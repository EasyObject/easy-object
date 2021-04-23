/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.parser.visitors.impl;

import io.github.easyobject.core.parser.ast.ContextVariableAccessExpression;
import io.github.easyobject.core.parser.visitors.AbstractResultVisitor;
import io.github.easyobject.core.ref.FieldRef;
import io.github.easyobject.core.value.ScalarValue;

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
