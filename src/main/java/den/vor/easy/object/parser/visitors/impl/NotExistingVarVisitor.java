package den.vor.easy.object.parser.visitors.impl;

import den.vor.easy.object.parser.ast.ContextVariableAccessExpression;
import den.vor.easy.object.parser.visitors.AbstractResultVisitor;
import den.vor.easy.object.ref.FieldRef;
import den.vor.easy.object.value.ScalarValue;

import java.util.List;

/**
 * AST nodes visitor that returns the list of variables that should be present in context on the evaluation moment.
 * It's required to build a dependencies graph and calculate field creation order.
 */
public class NotExistingVarVisitor extends AbstractResultVisitor<FieldRef> {

    @Override
    public List<FieldRef> visit(ContextVariableAccessExpression expression) {
        int parentHops = expression.getParentHops();
        List<ScalarValue<?>> keys = expression.getKeyHops();

        return List.of(new FieldRef(keys, parentHops));
    }
}
