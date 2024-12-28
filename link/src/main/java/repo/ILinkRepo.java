package repo;

import java.util.UUID;

import domain.link.Link;

public interface ILinkRepo {
    public Link[] index(UUID user) throws Exception;
    public int create(Link link) throws Exception;
    public Link read(int hash) throws Exception;
    public void update(Link link) throws Exception;
    public void delete(int hash) throws Exception;
}
