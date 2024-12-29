package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import config.IConfig;
import config.Keys;
import domain.exception.AccessException;
import domain.exception.NotFoundException;
import domain.link.Link;
import repo.ILinkRepo;

public class Service implements IService {
    private ILinkRepo repo;
    private IHash hasher;
    private IConfig cfg;

    private static final long MILLISECONDS_IN_DAY = 86400000;

    public Service(ILinkRepo repo, IHash hasher, IConfig cfg) {
        this.repo = repo;
        this.hasher = hasher;
        this.cfg = cfg;
    }

    @Override
    public int create(String link, UUID user, int limit, Date userDeadline, Date todayDate) throws Exception {
        Date deadline =  new Date(todayDate.getTime() + cfg.getIntValue(Keys.DAYS_BEFORE_DEADLINE) * MILLISECONDS_IN_DAY);

        Link obj = new Link(link, user, deadline);

        obj.updateLimit(limit);
        obj.updateDeadline(todayDate, userDeadline);
        obj.setHash(hasher.getHash(obj));

        return repo.create(obj);
    }

    @Override
    public Link read(int hash, Date todayDate) throws Exception {
        Link link = repo.read(hash, false);

        if (!link.isActual(todayDate)) {
            throw new NotFoundException("link expired");
        }

        return link;
    }

    @Override
    public void update(int hash, String link, UUID user, int limit, Date userDeadline, Date todayDate) throws Exception {
        Link newLink = new Link(hash, link, user, limit, userDeadline);
        Link linkForUpdate = repo.read(hash, true);
       
        linkForUpdate.merge(newLink, todayDate);

        repo.update(linkForUpdate);
    }

    @Override
    public void delete(int hash, UUID user) throws Exception {
        Link link = repo.read(hash, true);

        if (!link.getOwner().equals(user)) {
            throw new AccessException("wrong user");
        }

        repo.delete(hash);
    }

    @Override
    public List<Link> index(UUID user) throws Exception {
        return repo.indexForUser(user);
    }

    @Override
    public void cleanUp(Date todayDate) throws Exception {
        List<Link> links = repo.index();

        ArrayList<Integer> hashesToDelete = new ArrayList<>();
        for (Link link : links) {
            if (!link.isActual(todayDate)) {
                hashesToDelete.add(link.getHash());
            }
        }

        repo.cleanUp(hashesToDelete);
    }
}
