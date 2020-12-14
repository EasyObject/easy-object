package den.vor.easy.object.integration.util.random;

import den.vor.easy.object.random.CustomRandom;
import den.vor.easy.object.random.impl.XorShift1024StarProvider;
import org.junit.jupiter.api.extension.*;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.LongBuffer;

public class RandomProviderExtension implements ParameterResolver, AfterTestExecutionCallback {

    private static final String SEED_KEY = "SEED";
    private static final int RADIX = 16;

    private static final XorShift1024StarProvider PROVIDER = new XorShift1024StarProvider();
    private static final CustomRandom SEED_GENERATOR = PROVIDER.getRandom();


    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext context) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(CustomRandom.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext context) throws ParameterResolutionException {
        Method testMethod = context.getTestMethod().orElseThrow();
        Class<?> testClass = context.getTestClass().orElseThrow();
        long[] seed;
        if (testMethod.isAnnotationPresent(Seed.class)) {
            seed = toLongArray(testMethod.getAnnotation(Seed.class).value());
        } else if (testClass.isAnnotationPresent(Seed.class)) {
            seed = toLongArray(testClass.getAnnotation(Seed.class).value());
        } else {
            seed = generateSeed();
        }
        getStore(context).put(SEED_KEY, seed);
        return PROVIDER.getRandom(seed);
    }

    public long[] generateSeed() {
        return new long[]{ SEED_GENERATOR.nextLong(), SEED_GENERATOR.nextLong() };
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) {
        boolean testFailed = extensionContext.getExecutionException().isPresent();
        if (testFailed) {
            long[] seed = (long[]) getStore(extensionContext).get(SEED_KEY);
            if (seed != null) {
                System.out.println("Test failed with seed=" + toHexString(seed));
            }
        }
    }

    private ExtensionContext.Store getStore(ExtensionContext context) {
        return context.getStore(ExtensionContext.Namespace.create(getClass(), context.getRequiredTestMethod()));
    }

    private static String toHexString(long[] array) {
        ByteBuffer bb = ByteBuffer.allocate(array.length * Long.BYTES);
        bb.asLongBuffer().put(array);
        return new BigInteger(bb.array()).toString(RADIX);
    }

    private static long[] toLongArray(String seed) {
        byte[] array = new BigInteger(seed, RADIX).toByteArray();
        LongBuffer longBuffer = ByteBuffer.wrap(array).asLongBuffer();
        long[] result = new long[longBuffer.capacity()];
        for (int i = 0; i < result.length; i++) {
            result[i] = longBuffer.get(i);
        }
        return result;
    }
}
