package den.vor.easy.object.value.impl;

import den.vor.easy.object.value.Value;

import java.util.UUID;

/**
 * Value class that encapsulates UUIDv4. Doesn't support any arithmetical operations
 */
public class UUIDValue extends Value<UUID> {

    private final UUID value;

    public UUIDValue(UUID value) {
        this.value = value;
    }

    @Override
    public UUID getValue() {
        return value;
    }
}
