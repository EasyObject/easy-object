package den.vor.easy.object.ref;

import den.vor.easy.object.value.ScalarValue;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.IntValue;
import den.vor.easy.object.value.impl.StringValue;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FieldRef {

    private final int parentLinks;
    private final List<ScalarValue<?>> keys;

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
        return new FieldRef(keys, parentLinks - 1);
    }

    @Override
    public String toString() {
        return "FieldRef{" +
                "parentLinks=" + parentLinks +
                ", keys=" + keys +
                '}';
    }
}
