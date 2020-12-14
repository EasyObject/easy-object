package den.vor.easy.object.parser.visitors;


import den.vor.easy.object.parser.ast.*;

public interface ResultVisitor<T> {

    T visit(BinaryExpression s);

    T visit(ConditionalExpression s);

    T visit(TernaryExpression s);

    T visit(UnaryExpression s);

    T visit(ValueExpression s);

    T visit(MethodInvocationExpression s);

    T visit(FunctionInvocationExpression s);

    T visit(VariableMapAccessExpression s);

    T visit(ContextVariableAccessExpression s);
}
