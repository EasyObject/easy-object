/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.parser.visitors;

import den.vor.easy.object.parser.ast.*;

/**
 * Interface for an optimization AST nodes visitor.
 * Visitors are used by {@link den.vor.easy.object.parser.ExpressionEvaluator} to optimize expression.
 */
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
