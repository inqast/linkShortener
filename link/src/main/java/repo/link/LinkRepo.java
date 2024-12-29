package repo.link;

import java.sql.Connection;
import java.util.UUID;

import domain.link.Link;
import repo.ILinkRepo;

public class LinkRepo implements ILinkRepo {
    private Connection con;

    public LinkRepo(Connection con) {
        this.con = con;
    }

    @Override
    public Link[] index(UUID user) throws Exception {
        throw new Exception("not implemented");
    }

    @Override
    public int create(Link link) throws Exception {
        throw new Exception("not implemented");
    }

    @Override
    public Link read(int hash) throws Exception {
        throw new Exception("not implemented");
    }

    @Override
    public void update(Link link) throws Exception {
        throw new Exception("not implemented");
    }

    @Override
    public void delete(int hash) throws Exception {
        throw new Exception("not implemented");
    }
}
