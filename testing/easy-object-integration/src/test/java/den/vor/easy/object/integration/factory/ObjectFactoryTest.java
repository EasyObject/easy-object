package den.vor.easy.object.integration.factory;

import den.vor.easy.object.factory.impl.ObjectFactory;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.MapValue;
import org.junit.jupiter.api.Test;

import static den.vor.easy.object.facade.EasyObject.*;
import static den.vor.easy.object.facade.ValueFacade.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ObjectFactoryTest {

    @Test
    public void shouldCopySiblingNode_whenExpressionReferencesIt() {
        ObjectFactory factory = isObject("a", isInt(10, 20)).and("b", isExpression("a"));
        FactoryTestHelper.of(factory)
                .withAssertionRunner(map -> {
                    assertEquals(map.size(), 2, "Expected result map to have 2 fields");
                    Value<?> a = map.get(of("a"));
                    Value<?> b = map.get(of("b"));
                    assertEquals(a, b, () -> "Expected sibling " + a + " and " + b + " to be equal");
                }).test();
    }

    @Test
    public void shouldCopySiblingNodeWithNameThis_whenExpressionReferencesIt() {
        ObjectFactory factory = isObject("this", isInt(10, 20)).and("b", isExpression("this.this"));
        FactoryTestHelper.of(factory)
                .withAssertionRunner(map -> {
                    assertEquals(map.size(), 2, "Expected result map to have 2 fields");
                    Value<?> thisNode = map.get(of("this"));
                    Value<?> b = map.get(of("b"));
                    assertEquals(thisNode, b, () -> "Expected sibling " + thisNode + " and " + b + " to be equal");
                }).test();
    }
    @Test
    public void shouldCopySiblingNodeWithNameParent_whenExpressionReferencesIt() {
        ObjectFactory factory = isObject("parent", isInt(10, 20)).and("b", isExpression("this.parent"));
        FactoryTestHelper.of(factory)
                .withAssertionRunner(map -> {
                    assertEquals(map.size(), 2, "Expected result map to have 2 fields");
                    Value<?> parent = map.get(of("parent"));
                    Value<?> b = map.get(of("b"));
                    assertEquals(parent, b, () -> "Expected sibling " + parent + " and " + b + " to be equal");
                }).test();
    }

    @Test
    public void shouldCopySiblingNode_whenExpressionReferencesItWithExplicitThisReference() {
        ObjectFactory factory = isObject("a", isInt(10, 20)).and("b", isExpression("this.a"));
        FactoryTestHelper.of(factory)
                .withAssertionRunner(map -> {
                    assertEquals(map.size(), 2, "Expected result map to have 2 fields");
                    Value<?> a = map.get(of("a"));
                    Value<?> b = map.get(of("b"));
                    assertEquals(a, b, () -> "Expected sibling " + a + " and " + b + " to be equal");
                }).test();
    }


    @Test
    public void shouldCopyParentsSiblingNode_whenExpressionReferencesIt() {
        ObjectFactory factory = isObject("a", isInt(10, 20))
                .and("b", isObject("c", isExpression("parent.a")));
        FactoryTestHelper.of(factory)
                .withAssertionRunner(map -> {
                    assertEquals(map.size(), 2, "Expected result map to have 2 fields");
                    Value<?> b = map.get(of("b"));
                    assertTrue(b instanceof MapValue, "Expected field 'b' to be a map");
                    MapValue bMap = (MapValue) b;
                    Value<?> a = map.get(of("a"));
                    Value<?> c = bMap.get(of("c"));
                    assertEquals(a, c, () -> "Expected node " + c + " to copy parent's sibling (" + a + ") value");
                }).test();
    }

}
