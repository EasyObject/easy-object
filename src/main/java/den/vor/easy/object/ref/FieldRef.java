package den.vor.easy.object.ref;

import den.vor.easy.object.value.ScalarValue;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.IntValue;
import den.vor.easy.object.value.impl.StringValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that stores reference path in the object tree.
 */
public class FieldRef {

    private final int parentLinks;
    private final List<ScalarValue<?>> keys;

    /**
     * Creates a new class instance
     * @param keys collection of references down the tree. Applied after parentLinks
     * @param parentLinks number of leaps up in the tree. Applied before keys
     */
    public FieldRef(List<ScalarValue<?>> keys, int parentLinks) {
        this.parentLinks = parentLinks;
        this.keys = keys;
    }

    public int getParentLinks() {
        return parentLinks;
    }

    public ScalarValue<?> getFirstPath() {
        if (parentLinks == 0 && !keys.isEmpty()) {
            return keys.get(0);
        }
        throw new IllegalArgumentException();
    }

    public FieldRef getReferenceForParentFactory() {
        if (parentLinks == 0) {
            throw new IllegalArgumentException();
        }
        return new FieldRef(keys, parentLinks - 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldRef fieldRef = (FieldRef) o;
        return parentLinks == fieldRef.parentLinks &&
                Objects.equals(keys, fieldRef.keys);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parentLinks, keys);
    }

    @Override
    public String toString() {
        return "FieldRef{" +
                "parentLinks=" + parentLinks +
                ", keys=" + keys +
                '}';
    }
}
