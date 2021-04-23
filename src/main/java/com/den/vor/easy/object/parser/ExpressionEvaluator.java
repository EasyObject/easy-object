/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.parser;

import com.den.vor.easy.object.factory.impl.ElFactory;
import com.den.vor.easy.object.parser.visitors.OptimizationVisitor;
import com.den.vor.easy.object.parser.visitors.impl.*;
import com.den.vor.easy.object.ref.FieldRef;
import com.den.vor.easy.object.value.Value;
import com.den.vor.easy.object.value.impl.MapValue;
import com.den.vor.easy.object.parser.ast.Expression;
import com.den.vor.easy.object.parser.ast.Variables;
import com.den.vor.easy.object.parser.visitors.CompositeVisitor;
import com.den.vor.easy.object.value.impl.NullValue;

import java.util.List;

/**
 * Class for evaluating string expressions.
 * Is used by {@link ElFactory} and references in all factories.
 */
public class ExpressionEvaluator {

    public static final NotExistingVarVisitor NOT_EXISTING_VAR_VISITOR = new NotExistingVarVisitor();
    private final Expression expression;
    private final MapValue constParams;
    private final List<FieldRef> dependencies;

    /**
     * Creates an expression evaluator with the specified string and constant parameters.
     * String is split into tokens using {@link Lexer}.
     * Tokens are parsed into {@link Expression} tree using {@link Parser}.
     * Tree is optimized with {@link OptimizationVisitor}s.
     * @param expression expression to evaluate
     * @param constParams parameters that do not change between evaluations.
     */
    public ExpressionEvaluator(String expression, MapValue constParams) {
        TokenHolder tokenHolder = new Lexer(expression).tokenize();
        Expression parsedExpression = new Parser(tokenHolder).parse();
        Variables variables = new Variables(constParams, NullValue.NULL);
        CompositeVisitor compositeVisitor = new CompositeVisitor(List.of(
                new ConstArgsPropagationVisitor(variables),
                new ConstantFoldingVisitor(variables),
                new IdempotentFunctionCallsFoldingVisitor(variables),
                new ContextVariableAccessVisitor(variables),
                new ExpressionSimplifierVisitor(variables)
        ));
        this.expression = parsedExpression.accept(compositeVisitor);
        this.constParams = constParams;
        this.dependencies = this.expression.accept(NOT_EXISTING_VAR_VISITOR);
    }

    public ExpressionEvaluator(String expression) {
        this(expression, MapValue.emptyMap());
    }

    /**
     * Evaluates the expression tree using provided context.
     * @param context expression evaluation context
     * @return evaluation result
     */
    public Value<?> evaluate(Value<?> context) {
        Variables variables = new Variables(constParams, context);
        return expression.eval(variables);
    }

    /**
     * Evaluates the expression tree with empty context.
     * @return evaluation result
     */
    public Value<?> evaluate() {
        return evaluate(NullValue.NULL);
    }

    public Expression getExpression() {
        return expression;
    }

    public List<FieldRef> getDependencies() {
        return dependencies;
    }
}
