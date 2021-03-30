/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.value.impl;

import den.vor.easy.object.value.Value;

import java.util.UUID;

/**
 * Value class that encapsulates UUIDv4. Doesn't support any arithmetical operations.
 */
public class UUIDValue extends Value<UUID> {

    private final UUID value;

    public UUIDValue(UUID value) {
        this.value = value;
    }

    /**
     * Wraps the given value with {@linkplain UUIDValue}.
     * @param uuid value to wrap
     */
    public static UUIDValue of(UUID uuid) {
        return new UUIDValue(uuid);
    }

    @Override
    public UUID getValue() {
        return value;
    }
}
