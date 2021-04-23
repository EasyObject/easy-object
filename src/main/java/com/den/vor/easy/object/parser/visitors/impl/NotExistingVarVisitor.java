/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.parser.visitors.impl;

import com.den.vor.easy.object.parser.ast.ContextVariableAccessExpression;
import com.den.vor.easy.object.parser.visitors.AbstractResultVisitor;
import com.den.vor.easy.object.ref.FieldRef;
import com.den.vor.easy.object.value.ScalarValue;

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
