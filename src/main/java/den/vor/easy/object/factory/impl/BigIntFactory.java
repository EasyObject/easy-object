package den.vor.easy.object.factory.impl;

import den.vor.easy.object.factory.ComparableFactory;
import den.vor.easy.object.factory.GenerationContext;
import den.vor.easy.object.factory.constraints.Bound;
import den.vor.easy.object.random.CustomRandom;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.BigIntValue;
import den.vor.easy.object.value.impl.IntValue;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

public class BigIntFactory extends ComparableFactory<BigInteger, BigIntValue> {

    private static final BigInteger DEFAULT_MIN = ZERO;
    private static final BigInteger DEFAULT_MAX = new BigInteger("f".repeat(64), 16);

    public BigIntFactory() {
        this(DEFAULT_MIN, DEFAULT_MAX);
    }

    public BigIntFactory(BigInteger min, BigInteger max) {
        super(min, max);
    }

    @Override
    protected BigIntValue getBetween(GenerationContext context, Bound<BigInteger> min, Bound<BigInteger> max) {
        BigInteger minBound = min.isInclusive() ? min.getValue() : min.getValue().add(ONE);
        BigInteger maxBound = max.isInclusive() ? max.getValue() : max.getValue().subtract(ONE);

        BigInteger upperLimit = maxBound.subtract(minBound);

        BigInteger randomNumber;
        do {
            randomNumber = generateRandom(upperLimit.bitLength(), context.getRandom());
        } while (randomNumber.compareTo(upperLimit) >= 0);

        return BigIntValue.of(randomNumber);
    }

    private BigInteger generateRandom(int numBits, CustomRandom random) {
        int numBytes = (int)(((long)numBits+7)/8); // avoid overflow
        byte[] randomBits = new byte[numBytes];

        // Generate random bytes and mask out any excess bits
        if (numBytes > 0) {
            random.nextBytes(randomBits);
            int excessBits = 8*numBytes - numBits;
            randomBits[0] &= (1 << (8-excessBits)) - 1;
        }
        return new BigInteger(randomBits);
    }

    @Override
    protected BigInteger cast(Value<?> value) {
        return value.as(BigInteger.class);
    }
}
