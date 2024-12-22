package domain.link;

import java.util.Date;
import java.util.UUID;

public class Link {
    private String hash;
    private String link;
    private UUID owner;
    private int usages;
    private int limit;
    private Date deadline;

    public Link(String hash, String link, UUID owner, int usages, int limit, Date deadline) {
        this.hash = hash;
        this.link = link;
        this.owner = owner;
        this.usages = usages;
        this.limit = limit;
        this.deadline = deadline;
    }

    public String getHash() {
        return hash;
    }

    public String getLink() {
        return link;
    }

    public UUID getOwner() {
        return owner;
    }

    public int getUsages() {
        return usages;
    }

    public int getLimit() {
        return limit;
    }

    public Date getDeadline() {
        return deadline;
    }
}
