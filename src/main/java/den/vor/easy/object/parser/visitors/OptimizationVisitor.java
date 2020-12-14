package den.vor.easy.object.parser.visitors;

import den.vor.easy.object.parser.ast.*;

public interface OptimizationVisitor extends ResultVisitor<Expression> {

    Expression visit(BinaryExpression s);

    Expression visit(ConditionalExpression s);

    Expression visit(ContextVariableAccessExpression s);

    Expression visit(TernaryExpression s);

    Expression visit(UnaryExpression s);

    Expression visit(ValueExpression s);

    Expression visit(MethodInvocationExpression s);

    Expression visit(FunctionInvocationExpression s);

    Expression visit(VariableMapAccessExpression s);
}
