package service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import domain.link.Link;

public interface IService {
    public int create(String link, UUID user, int limit, Date userDeadline, Date todayDate) throws Exception;
    public Link read(int hash, Date todayDate) throws Exception;
    public void update(int hash, String link, UUID user, int limit, Date userDeadline, Date todayDate) throws Exception;
    public void delete(int hash, UUID user) throws Exception;
    public List<Link> index(UUID user) throws Exception;
    public void cleanUp(Date todayDate) throws Exception;
}