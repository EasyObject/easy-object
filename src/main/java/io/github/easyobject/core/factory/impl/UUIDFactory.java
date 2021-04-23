/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.factory.impl;

import io.github.easyobject.core.factory.Factory;
import io.github.easyobject.core.factory.Generator;
import io.github.easyobject.core.value.impl.UUIDValue;
import io.github.easyobject.core.random.CustomRandom;

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
        bytes[6] &= 0x0f;  /* clear version        */
        bytes[6] |= 0x40;  /* set to version 4     */
        bytes[8] &= 0x3f;  /* clear variant        */
        bytes[8] |= 0x80;  /* set to IETF variant  */

        long msb = ByteBuffer.wrap(bytes).getLong(0);
        long lsb = ByteBuffer.wrap(bytes).getLong(1);
        return new UUID(msb, lsb);
    }
}
