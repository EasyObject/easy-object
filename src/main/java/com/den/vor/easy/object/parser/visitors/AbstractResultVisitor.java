/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.parser.visitors;

import com.den.vor.easy.object.parser.ast.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

/**
 * Skeletal implementation of a {@link ResultVisitor}.
 * Visits all child expressions and concatenates results.
 * @param <T> type of objects in list returned by a visitor.
 */
public abstract class AbstractResultVisitor<T> implements ResultVisitor<List<T>> {

    @Override
    public List<T> visit(BinaryExpression s) {
        return concat(asList(
                s.getLeft().accept(this),
                s.getRight().accept(this)));
    }

    @Override
    public List<T> visit(ConditionalExpression s) {
        return concat(asList(
                s.getLeft().accept(this),
                s.getRight().accept(this)));
    }

    @Override
    public List<T> visit(UnaryExpression s) {
        return s.getExpression().accept(this);
    }

    @Override
    public List<T> visit(ValueExpression s) {
        return Collections.emptyList();
    }


    @Override
    public List<T> visit(ContextVariableAccessExpression s) {
        return Collections.emptyList();
    }

    @Override
    public List<T> visit(TernaryExpression s) {
        return concat(asList(
                s.getCondition().accept(this),
                s.getThenExpression().accept(this),
                s.getElseExpression().accept(this)
        ));
    }

    @Override
    public List<T> visit(MethodInvocationExpression s) {
        return concat(List.of(
                s.getExpression().accept(this),
                s.getMethod().accept(this),
                s.getArgs().stream().map(arg -> arg.accept(this)).flatMap(Collection::stream).collect(toList())
        ));
    }

    @Override
    public List<T> visit(FunctionInvocationExpression s) {
        return concat(List.of(
                s.getExpression().accept(this),
                s.getArgs().stream().map(arg -> arg.accept(this)).flatMap(Collection::stream).collect(toList())
        ));

    }

    @Override
    public List<T> visit(VariableMapAccessExpression s) {
        return s.getKeys().stream().map(arg -> arg.accept(this)).flatMap(Collection::stream).collect(toList());
    }

    private List<T> concat(Collection<List<T>> lists) {
        return lists.stream().flatMap(Collection::stream).collect(toList());
    }
}
