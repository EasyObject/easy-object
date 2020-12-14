package den.vor.easy.object.parser.ast.operator;

public abstract class Operator {

    protected boolean isInt(Object value) {
        return value instanceof Integer;
    }

    protected boolean isString(Object value) {
        return value instanceof String;
    }

    protected boolean isNumber(Object value) {
        return value instanceof Number;
    }

    protected boolean isBool(Object value) {
        return value instanceof Boolean;
    }

    protected boolean isDouble(Object value) {
        return value instanceof Double;
    }
}
