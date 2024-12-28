package service;

import java.util.UUID;

import domain.link.Link;

public interface IService {
    public String create(String link, UUID user, int limit) throws Exception;
    public Link read(String hash) throws Exception;
    public void update(String hash, String link, UUID user, int limit) throws Exception;
    public void delete(String hash, UUID user) throws Exception;
    public Link[] index(UUID user) throws Exception;
}