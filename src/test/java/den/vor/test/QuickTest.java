package den.vor.test;

import den.vor.easy.object.factory.RootFactory;
import den.vor.easy.object.factory.impl.IntFactory;
import den.vor.easy.object.factory.impl.ObjectFactory;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuickTest {

    @Test
    public void test() {
        assertEquals("Hello World", "Hello" + " World");
    }

    @Test
    public void test2() {
        RootFactory<Map<String, Object>> rootFactory = new RootFactory<>(
                new ObjectFactory()
                        .and("a", new IntFactory())
                        .and("b", new IntFactory())
        );

        List<Map<String, Object>> list = rootFactory.getList(5);
        System.out.println(list);
    }
}
