package co.aikar.idb.types;

import co.aikar.idb.schema.AbstractColumn;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class UUIDColumn extends AbstractColumn<UUID> {

    @Contract("_ -> new")
    public static @NotNull UUIDColumn of(String name) {
        return new UUIDColumn(name);
    }

    @Contract("_ -> new")
    public static @NotNull UUIDColumn ofNullable(String name) {
        return new UUIDColumn(name, true, null);
    }

    @Contract("_, _ -> new")
    public static @NotNull UUIDColumn ofDefault(String name, String defaultValue) {
        return new UUIDColumn(name, false, defaultValue);
    }

    public UUIDColumn(String name) {
        super(name, "VARCHAR(" + 36 + ")",false,null,null);
    }

    public UUIDColumn(String name, boolean nullable, String defaultValue) {
        super(name, "VARCHAR(" + 36 + ")", nullable, defaultValue, null);
    }

    @Override
    public UUID get(Object object) {
        return object instanceof String ? UUID.fromString((String) object) : null;
    }
}
