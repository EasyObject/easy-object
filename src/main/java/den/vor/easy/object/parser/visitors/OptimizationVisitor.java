package den.vor.easy.object.parser.visitors;

import den.vor.easy.object.parser.ast.*;

public interface OptimizationVisitor extends ResultVisitor<Expression> {

    Expression visit(BinaryExpression expression);

    Expression visit(ConditionalExpression expression);

    Expression visit(ContextVariableAccessExpression expression);

    Expression visit(TernaryExpression expression);

    Expression visit(UnaryExpression expression);

    Expression visit(ValueExpression expression);

    Expression visit(MethodInvocationExpression expression);

    Expression visit(FunctionInvocationExpression expression);

    Expression visit(VariableMapAccessExpression expression);
}
