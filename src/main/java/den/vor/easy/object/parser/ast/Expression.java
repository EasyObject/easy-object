package den.vor.easy.object.parser.ast;

import den.vor.easy.object.parser.visitors.ResultVisitor;
import den.vor.easy.object.value.Value;

/**
 * Basic interface for objects that can be evaluated.
 * Is used in expression language
 */
public interface Expression {

    /**
     * Evaluate expression
     * @param params variables to use during the evaluation
     * @return expression evaluation result
     */
    Value<?> eval(Variables params);

    /**
     * Accepts a visitor
     * @param visitor visitor to accept
     * @param <T> return type of a visitor
     * @return visit result
     */
    <T> T accept(ResultVisitor<T> visitor);
}
