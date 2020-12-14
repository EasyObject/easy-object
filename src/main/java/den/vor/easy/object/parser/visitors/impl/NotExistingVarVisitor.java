package den.vor.easy.object.parser.visitors.impl;

import den.vor.easy.object.parser.ast.ContextVariableAccessExpression;
import den.vor.easy.object.parser.visitors.AbstractResultVisitor;
import den.vor.easy.object.ref.FieldRef;
import den.vor.easy.object.value.ScalarValue;

import java.util.List;

public class NotExistingVarVisitor extends AbstractResultVisitor<FieldRef> {

    @Override
    public List<FieldRef> visit(ContextVariableAccessExpression expression) {
        int parentHops = expression.getParentHops();
        List<ScalarValue<?>> keys = expression.getKeyHops();

        return List.of(new FieldRef(keys, parentHops));
    }
}
