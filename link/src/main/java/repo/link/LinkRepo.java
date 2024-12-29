package repo.link;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import domain.exception.DuplicateException;
import domain.exception.NotFoundException;
import domain.link.Link;
import repo.ILinkRepo;
import repo.Keys;

public class LinkRepo implements ILinkRepo {
    private Connection con;

    public LinkRepo(Connection con) {
        this.con = con;
    }

    @Override
    public List<Link> index(UUID user) throws Exception {
        String query = "SELECT hash, link, owner, usages_count, usages_limit, deadline FROM links WHERE owner = UUID(?)";
        
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, user.toString());

            if (!statement.execute()) {
                throw new NotFoundException("links not found");
            }

            ResultSet resultSet = statement.getResultSet();
            ArrayList<Link> links = new ArrayList<>();
        
            while (resultSet.next()) {
                Link link = new Link(
                    resultSet.getInt(Keys.HASH),
                    resultSet.getString(Keys.LINK),
                    UUID.fromString(resultSet.getString(Keys.OWNER)),
                    resultSet.getInt(Keys.USAGES_COUNT),
                    resultSet.getInt(Keys.USAGES_LIMIT),
                    resultSet.getDate(Keys.DEADLINE)
                );

                links.add(link);
            }            

            return links;
        }
    }

    @Override
    public int create(Link link) throws Exception {
        String query = "INSERT INTO links (hash, link, owner, usages_count, usages_limit, deadline) " + 
        "VALUES (?, ?, UUID(?), ?, ?, ?)";
        
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, link.getHash());
            statement.setString(2, link.getLink());
            statement.setString(3, link.getOwner().toString());
            statement.setInt(4, link.getUsages());
            statement.setInt(5, link.getLimit());
            statement.setDate(6, new Date(link.getDeadline().getTime()));

            statement.execute();

            if (statement.getUpdateCount() == 0) {
                throw new DuplicateException("try again");
            }

            return link.getHash();
        }
    }

    @Override
    public Link read(int hash, boolean forUpdate) throws Exception {
        String query = "SELECT hash, link, owner, usages_count, usages_limit, deadline FROM links WHERE hash = ?";
        
        try (PreparedStatement statement = con.prepareStatement(
            query, 
            ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_UPDATABLE)
        ) {
            statement.setInt(1, hash);

            if (!statement.execute()) {
                throw new NotFoundException("link not found");
            }

            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
        
            Link link = new Link(
                resultSet.getInt(Keys.HASH),
                resultSet.getString(Keys.LINK),
                UUID.fromString(resultSet.getString(Keys.OWNER)),
                resultSet.getInt(Keys.USAGES_COUNT),
                resultSet.getInt(Keys.USAGES_LIMIT),
                resultSet.getDate(Keys.DEADLINE)
            );

            if (!forUpdate) {
                link.setUsages(link.getUsages()+1);
            
                resultSet.updateInt(Keys.USAGES_COUNT, link.getUsages());
                resultSet.updateRow();
            }

            return link;
        }
    }

    @Override
    public void update(Link link) throws Exception {
        String query = "UPDATE links SET link = ?, owner = UUID(?), usages_count = ?, usages_limit = ?, deadline = ? WHERE hash = ?";
        
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, link.getLink());
            statement.setString(2, link.getOwner().toString());
            statement.setInt(3, link.getUsages());
            statement.setInt(4, link.getLimit());
            statement.setDate(5, new Date(link.getDeadline().getTime()));

            statement.setInt(6, link.getHash());


            statement.execute();

            if (statement.getUpdateCount() == 0) {
                throw new NotFoundException("link not found");
            }
        }
    }

    @Override
    public void delete(int hash) throws Exception {
        String query = "DELETE from links WHERE hash = ?";
        
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, hash);

            statement.execute();

            if (statement.getUpdateCount() == 0) {
                throw new NotFoundException("link not found");
            }
        }
    }
}
