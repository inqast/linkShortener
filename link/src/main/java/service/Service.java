package service;

import java.util.Date;
import java.util.UUID;

import config.IConfig;
import config.Keys;
import domain.exception.AccessException;
import domain.link.Link;
import repo.ILinkRepo;

public class Service implements IService {
    private ILinkRepo repo;
    private IHash hasher;
    private IConfig cfg;

    private final long MilliseconsInDay = 86400000;

    public Service(ILinkRepo repo, IHash hasher, IConfig cfg) {
        this.repo = repo;
        this.hasher = hasher;
        this.cfg = cfg;
    }

    @Override
    public int create(String link, UUID user, int limit, Date userDeadline, Date todayDate) throws Exception {
        Date deadline =  new Date(todayDate.getTime() + cfg.getIntValue(Keys.DAYS_BEFORE_DEADLINE) * MilliseconsInDay);

        Link obj = new Link(link, user, deadline);

        obj.updateLimit(limit);
        obj.updateDeadline(todayDate, userDeadline);
        obj.setHash(hasher.getHash(obj));

        int hash = repo.create(obj);

        return hash;
    }

    @Override
    public Link read(int hash) throws Exception {
        return repo.read(hash);
    }

    @Override
    public void update(int hash, String link, UUID user, int limit, Date userDeadline, Date todayDate) throws Exception {
        Link newLink = new Link(hash, link, user, limit, userDeadline);
        Link linkForUpdate = repo.read(hash);
       
        linkForUpdate.merge(newLink, todayDate);

        repo.update(linkForUpdate);
    }

    @Override
    public void delete(int hash, UUID user) throws Exception {
        Link link = repo.read(hash);

        if (link.getOwner() != user) {
            throw new AccessException("wronf user");
        }

        repo.delete(hash);
    }

    @Override
    public Link[] index(UUID user) throws Exception {
        return repo.index(user);
    }
}
