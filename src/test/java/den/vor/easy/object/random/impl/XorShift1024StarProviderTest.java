package den.vor.easy.object.random.impl;

import den.vor.easy.object.random.CustomRandom;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

class XorShift1024StarProviderTest {

    @Test
    public void test() {
        XorShift1024StarProvider provider = new XorShift1024StarProvider();

        CustomRandom customRandom = provider.getRandom();

        customRandom.nextInt();
        customRandom.nextInt();
        customRandom.nextInt();
        customRandom.nextInt();

        long[] state = customRandom.getSeed();

        CustomRandom newCustomRandom = provider.getRandom(state);

        System.out.println(Stream.generate(() -> newCustomRandom.nextInt() == customRandom.nextInt()).limit(10_000_000).allMatch(b -> b));
    }
}