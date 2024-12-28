package hash;

import domain.link.Link;
import service.IHash;

public class Hash implements IHash {
    @Override
    public int getHash(Link link) {
        return link.hashCode();
    }
}
