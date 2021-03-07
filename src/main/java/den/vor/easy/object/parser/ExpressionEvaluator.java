/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.parser;

import den.vor.easy.object.parser.ast.Expression;
import den.vor.easy.object.parser.ast.Variables;
import den.vor.easy.object.parser.visitors.CompositeVisitor;
import den.vor.easy.object.parser.visitors.impl.*;
import den.vor.easy.object.ref.FieldRef;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.MapValue;
import den.vor.easy.object.value.impl.NullValue;

import java.util.List;

public class ExpressionEvaluator {

    public static final NotExistingVarVisitor NOT_EXISTING_VAR_VISITOR = new NotExistingVarVisitor();
    private final Expression expression;
    private final MapValue constParams;
    private final List<FieldRef> dependencies;

    public ExpressionEvaluator(String expression, MapValue constParams) {
        List<Token> tokens = new Lexer(expression).tokenize();
        Expression parsedExpression = new Parser(tokens).parse();
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

    public Value<?> evaluate(Value<?> context) {
        Variables variables = new Variables(constParams, context);
        return expression.eval(variables);
    }

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
