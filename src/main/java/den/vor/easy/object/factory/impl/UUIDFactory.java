package den.vor.easy.object.factory.impl;

import den.vor.easy.object.factory.Factory;
import den.vor.easy.object.factory.Generator;
import den.vor.easy.object.random.CustomRandom;
import den.vor.easy.object.value.impl.UUIDValue;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * Factory that generates UUIDv4 values.
 * Due to performance concerns default Java {@link UUID#randomUUID()} is not used
 */
public class UUIDFactory extends Factory<UUID, UUIDValue> {

    @Override
    public Generator<UUIDValue> getGenerator() {
        return context -> new UUIDValue(generateUUID(context.getRandom()));
    }

    private UUID generateUUID(CustomRandom random) {
        byte[] bytes = new byte[16];

        random.nextBytes(bytes);
        bytes[6]  &= 0x0f;  /* clear version        */
        bytes[6]  |= 0x40;  /* set to version 4     */
        bytes[8]  &= 0x3f;  /* clear variant        */
        bytes[8]  |= 0x80;  /* set to IETF variant  */

        long msb = ByteBuffer.wrap(bytes).getLong(0);
        long lsb = ByteBuffer.wrap(bytes).getLong(1);
        return new UUID(msb, lsb);
    }
}
