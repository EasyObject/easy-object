package den.vor.easy.object.value.ref;

import den.vor.easy.object.value.IntValue;
import den.vor.easy.object.value.ScalarValue;
import den.vor.easy.object.value.StringValue;
import den.vor.easy.object.value.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FieldRef {

    private static final Pattern INDEX_ACCESS = Pattern.compile("^(\\w+)\\[(\\d+)]$");

    private int parentLinks;
    private List<ScalarValue<?>> keys;

    public FieldRef(String path) {
        if(path.startsWith("$")) {
            path = path.substring(1);
        }
        String[] fieldPath = path.split("\\.");
        int skipped = 0;
        int parents = 0;
        if (fieldPath[0].equals("this")) {
            skipped = 1;
        }
        else {
            while (parents < fieldPath.length &&fieldPath[parents].equals("parent")) {
                parents++;
            }
        }
        this.keys = resolveFieldReferences(fieldPath, skipped + parents);
        this.parentLinks = parents;
    }

    private FieldRef(List<ScalarValue<?>> keys, int parentLinks) {
        this.parentLinks = parentLinks;
        this.keys = keys;
    }

    public int getParentLinks() {
        return parentLinks;
    }

    private static List<ScalarValue<?>> resolveFieldReferences(String[] fieldPathArray, int parents) {
        List<ScalarValue<?>> fieldPath = new ArrayList<>();
        for (int i = parents; i < fieldPathArray.length; i++) {
            Matcher matcher = INDEX_ACCESS.matcher(fieldPathArray[i]);
            if (matcher.matches()) {
                fieldPath.add(StringValue.of(matcher.group(1)));
                fieldPath.add(IntValue.of(Integer.parseInt(matcher.group(2))));
            } else {
                fieldPath.add(StringValue.of(fieldPathArray[i]));
            }
        }
        return fieldPath;
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

    public Value<?> get(Value<?> value) {
        for (ScalarValue<?> key: keys) {
            value = value.get(key);
        }
        return value;
    }
}
