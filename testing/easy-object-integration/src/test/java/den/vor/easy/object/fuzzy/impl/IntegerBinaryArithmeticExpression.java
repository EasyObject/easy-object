/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.fuzzy.impl;

import den.vor.easy.object.fuzzy.FuzzyTestContext;
import den.vor.easy.object.fuzzy.GenerationContext;
import den.vor.easy.object.fuzzy.factory.IntExpressionFactory;
import den.vor.easy.object.random.CustomRandom;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class IntegerBinaryArithmeticExpression extends IntExpression {

    private IntExpression left;
    private List<Pair<Operator, IntExpression>> right;

    public IntegerBinaryArithmeticExpression(GenerationContext context, CustomRandom customRandom) {
        GenerationContext childContext = new GenerationContext().setDepth(context.getDepth() + 1);
        left = IntExpressionFactory.getExpression(childContext, customRandom);
        right = new ArrayList<>();
        do {
            if (right.size() >= 10) {
                break;
            }
            Operator operator = customRandom.next(Operator.values());
            IntExpression expression = IntExpressionFactory.getExpression(childContext, customRandom);
            right.add(new Pair<>(operator, expression));
        } while (customRandom.nextBoolean());
    }

    @Override
    public String build() {
        return "(" + left.build() + " "
                + right.stream().map(this::buildPair).collect(Collectors.joining())
                + ")";
    }

    private String buildPair(Pair<Operator, IntExpression> p) {
        return p.getKey().operator + " " + p.getValue().build();
    }

    @Override
    public Integer getExpected(FuzzyTestContext context) {
        int current = left.getExpected(context);
        for (Pair<Operator, IntExpression> pair : right) {
            current = pair.getKey().function.apply(current, pair.getValue().getExpected(context));
        }
        return current;
    }

    @Override
    public String toString() {
        return "IntBinary{" + build() + '}';
    }

    private enum Operator {
        PLUS("+", Integer::sum),
        MINUS("-", (a, b) -> a - b),
//        MULTIPLY("*", (a, b) -> a * b),
//        DIVIDE("/", (a, b) -> a / b),
//        POWER("**", (a, b) -> (int)Math.pow(a, b))
        ;

        private final String operator;
        private final BiFunction<Integer, Integer, Integer> function;

        Operator(String operator, BiFunction<Integer, Integer, Integer> function) {
            this.operator = operator;
            this.function = function;
        }

        public String getOperator() {
            return operator;
        }

        public int calculate(int left, int right) {
            return function.apply(left, right);
        }
    }

    private static class Pair<K, V> {
        K key;
        V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }
}
