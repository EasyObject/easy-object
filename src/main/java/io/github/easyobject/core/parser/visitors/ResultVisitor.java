/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.parser.visitors;


import io.github.easyobject.core.parser.ast.*;

/**
 * AST nodes visitor that produces some result.
 * @param <T> type of result produced by a visitor
 */
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
