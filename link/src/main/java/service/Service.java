package service;

import java.util.UUID;

import domain.link.Link;

public class Service implements IService {
    @Override
    public String create(String link, UUID user, int limit) throws Exception {
        throw new Exception("not implemented");
    }

    @Override
    public Link read(String hash) throws Exception {
        throw new Exception("not implemented");
    }

    @Override
    public void update(String hash, String link, UUID user, int limit) throws Exception {
        throw new Exception("not implemented");
    }

    @Override
    public void delete(String hash, UUID user) throws Exception {
        throw new Exception("not implemented");
    }

    @Override
    public Link[] index(UUID user) throws Exception {
        throw new Exception("not implemented");
    }
}
