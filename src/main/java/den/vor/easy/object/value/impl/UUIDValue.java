package den.vor.easy.object.value.impl;

import den.vor.easy.object.value.Value;

import java.util.UUID;

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
