package den.vor.easy.object.parser.ast;

import den.vor.easy.object.parser.visitors.ResultVisitor;
import den.vor.easy.object.value.Value;

public interface Expression {

    Value<?> eval(Variables params);

    <T> T accept(ResultVisitor<T> visitor);
}
