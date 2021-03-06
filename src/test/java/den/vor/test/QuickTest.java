package den.vor.test;

import den.vor.easy.object.consumer.NoOpConsumer;
import den.vor.easy.object.consumer.formatter.CsvFormatter;
import den.vor.easy.object.consumer.formatter.SqlFormatter;
import den.vor.easy.object.factory.impl.BigIntFactory;
import den.vor.easy.object.factory.impl.ElFactory;
import den.vor.easy.object.factory.impl.TimeFactory;
import den.vor.easy.object.parser.ExpressionEvaluator;
import den.vor.easy.object.parser.Lexer;
import den.vor.easy.object.parser.Parser;
import den.vor.easy.object.parser.Token;
import den.vor.easy.object.parser.ast.Expression;
import den.vor.easy.object.parser.ast.Variables;
import den.vor.easy.object.ref.FieldRef;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.IntValue;
import den.vor.easy.object.value.impl.MapValue;
import den.vor.easy.object.value.impl.NullValue;
import den.vor.easy.object.value.impl.StringValue;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static den.vor.easy.object.facade.ConsumerFacade.*;
import static den.vor.easy.object.facade.EasyObject.*;
import static den.vor.easy.object.facade.ValueFacade.of;
import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuickTest {

    @Test
    @Disabled
    public void test() {
        assertEquals("Hello World", "Hello" + " World");
    }

    @Test
    @Disabled
    public void test2() {
//        RootFactory<MapValue> rootFactory =
        isObject()
                .and("c", isObject("parent", isInt(0, 10000))
                        .and("ent", isConst(StringValue.of("ent")))
                )
                .and("piSq", isExpression("this.c['par' + this.c.ent] ** 2"))
                .and("p", isObject("pp", isExpression("parent.c")))
                .prepare(3)
                .consume(
                        toFile("./output/file.json", toJson(), toJson("c")),
                        toFile("./output/file2.json", toJson("c"))
                ).generate();

//        List<MapValue> list = rootFactory.getList(5);
//        list.forEach(System.out::println);
    }

    @Test
    public void test12451() {
//        RootFactory<MapValue> rootFactory =
        isObject()
                .and("piSq", isInt("count"))
                .prepare(10)
                .consume(toStd(toJson()))
                .generate();

//        List<MapValue> list = rootFactory.getList(5);
//        list.forEach(System.out::println);
    }


    @Test
    @Disabled
    public void test3() {
        IntValue value = IntValue.of(1);
        Supplier<IntValue> supplier = () -> value;

        isObject("v", fromSupplier(supplier))
                .prepare()
                .stream()
                .limit(10)
                .forEach(System.out::println);
    }

    @Test
//    @Disabled
    public void test4() {
        String expression = "2 + 5";
        new ExpressionEvaluator(expression);
    }

    @Test
//    @Disabled
    public void elTest() {
        String string = "2 > 3 || 3 < 2 || (false && true)";

        List<Token> tokenize = new Lexer(string).tokenize();
        Expression parse = new Parser(tokenize).parse();
        System.out.println(parse);
        Value<?> eval = parse.eval(new Variables(MapValue.emptyMap(), NullValue.NULL));
        System.out.println(eval);
    }

    @Test
//    @Disabled
    public void elTest2() {
        String string = "a.b.size()";

        Value<?> evaluate = new ExpressionEvaluator(string).evaluate(
                new MapValue(Map.of(StringValue.of("a"),
                        new MapValue(Map.of(StringValue.of("b"), StringValue.of("lalala")))))
        );
        System.out.println(evaluate);
    }

    @Test
//    @Disabled
    public void elTest1() {
        String string = "1 * (10 ** 3) + 2";

        ExpressionEvaluator expressionEvaluator = new ExpressionEvaluator(string);
        System.out.println(expressionEvaluator.getExpression());
        Value<?> evaluate = expressionEvaluator.evaluate(
                new MapValue(Map.of(StringValue.of("a"),
                        IntValue.of(10)))
        );
        System.out.println(evaluate);
    }

    @Test
//    @Disabled
    public void elTest5123() {
        String string = "parent.parent.this.parent.a.b.size()";

        List<FieldRef> dependencies = new ExpressionEvaluator(string).getDependencies();
        System.out.println(dependencies);
    }

    @Test
//    @Disabled
    public void elTest5126() {
        isObject("a", new TimeFactory(LocalDateTime.now().toLocalTime(), LocalDateTime.now().toLocalTime().plusHours(2)))
                .prepare(10)
                .stream()
                .forEach(System.out::println);
    }


    @Test
//    @Disabled
    public void elTest5124() {
        isObject("d", isInt(10, 20))
                .and("b", isInt(5, 15).lt("this.d"))
                .prepare(5)
                .stream()
                .forEach(System.out::println);
    }

    @Test
    public void elTest3() {
        String string = "32 >> 4 >> 1";

        Value<?> evaluate = new ExpressionEvaluator(string).evaluate();
        System.out.println(evaluate);

        System.out.println(32 >> 4 >> 1);
//        System.out.println(1L << 1L);
    }

    @Test
    public void elTest1234() {
        String string = "a > 2 ? 1 : 4";

        Expression expression = new ExpressionEvaluator(string, new MapValue(Map.of(StringValue.of("a"), IntValue.of(1)))).getExpression();
        System.out.println(expression);
    }

    @Test
    public void elTest1235() {
        String string = "2 ** 3";

        Expression expression = new ExpressionEvaluator(string, new MapValue(Map.of(StringValue.of("a"), IntValue.of(1)))).getExpression();
        System.out.println(expression);
        System.out.println();
    }

    @Test
    public void elTest1236() {
        String string = "now() + 1d1h";

        Value<?> expression = new ExpressionEvaluator(string).evaluate();
        System.out.println(expression);
    }

    @Test
//    @Disabled
    public void asdasd() {
        System.out.println(ElFactory.factory("2 + 3").prepare().getList(5));
    }

    @Test
    public void t2() throws InterruptedException {
        isObject()
                .and("id", isInt(10, 20))
                .and("a", isInt(10, 20))
                .and("b", isInt(15, 25).lt("(a ** 2) / 3"))
                .and("inner", isObject("d", isInt(15, 25))
                            .and("id", isInt(100, 200))
                            .and("in2", isObject("a", isConst(of(13)))))
//                .and("t", isExpression("inner.d + 1h"))
                .prepare(10_000_000)
                .consume(new NoOpConsumer<>(toJson()))
//                .generateParallel();
                .generate();
//                .stream().forEach(System.out::println);

    }


    @Test
    public void t123() {
        isObject()
                .and("id", new BigIntFactory())
                .prepare(10)
                .consume(toStd(toJson()))
                .generate();

    }
}
