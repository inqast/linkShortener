package domain.link;

import java.util.Date;
import java.util.UUID;

import domain.exception.AccessException;

public class Link {
    private int hash;
    private String link;
    private UUID owner;
    private int usages;
    private int limit;
    private Date deadline;

    public Link(String link, UUID owner, Date deadline) {
        this.link = link;
        this.owner = owner;
        this.deadline = deadline;
    }

    public Link(int hash, String link, UUID owner, int limit, Date deadline) {
        this.hash = hash;
        this.link = link;
        this.owner = owner;
        this.limit = limit;
        this.deadline = deadline;
    }

    public Link(int hash, String link, UUID owner, int usages, int limit, Date deadline) {
        this.hash = hash;
        this.link = link;
        this.owner = owner;
        this.usages = usages;
        this.limit = limit;
        this.deadline = deadline;
    }

    public int getHash() {
        return hash;
    }

    public void setHash(int hash) {
        this.hash = hash;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public UUID getOwner() {
        return owner;
    }

    public int getUsages() {
        return usages;
    }

    public void setUsages(int usages) {
        this.usages = usages;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void updateLimit(int limit) {
        if (limit > 0) {
            this.limit = limit;
        }
    }

    public Date getDeadline() {
        return deadline;
    }

    public void updateDeadline(Date todayDate, Date deadline) {
        if (validateDeadline(todayDate, deadline)) {
            this.deadline = deadline;
        }
    }

    public boolean validateDeadline(Date todayDate, Date deadline) {
        return deadline != null && deadline.getTime() != 0 
        && deadline.after(todayDate) 
        && (this.deadline == null || deadline.compareTo(this.deadline) < 0);
    }

    public boolean merge(Link link, Date todayDate) throws AccessException {
        if (!owner.equals(link.getOwner())) {
            throw new AccessException("wrong user");
        }

        boolean isUpdated = false;

        if (!link.getLink().isEmpty()) {
            this.link = link.getLink();
            isUpdated = true;
        }
        
        if (link.getLimit() > 0) {
            this.limit = link.getLimit();
            isUpdated = true;
        }
        
        if (validateDeadline(todayDate, link.getDeadline())) {
            this.deadline = link.getDeadline();
            isUpdated = true;
        }

        return isUpdated;
    }

    public boolean isActual(Date todayDate) {
        if (todayDate.after(deadline)) {
            return false;
        }

        if (limit > 0 && usages > limit) {
            return false;
        }

        return true;
    }
}
