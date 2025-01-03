package repo;

import java.util.List;
import java.util.UUID;

import domain.link.Link;

public interface ILinkRepo {
    public List<Link> index() throws Exception;
    public List<Link> indexForUser(UUID user) throws Exception;
    public int create(Link link) throws Exception;
    public Link read(int hash, boolean forUpdate) throws Exception;
    public void update(Link link) throws Exception;
    public void delete(int hash) throws Exception;
    public void cleanUp(List<Integer> hashes) throws Exception;
}
