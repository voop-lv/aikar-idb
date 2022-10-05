package co.aikar.idb.types;

import co.aikar.idb.schema.AbstractColumn;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class StringColumn extends AbstractColumn<String> {

    @Contract("_, _ -> new")
    public static @NotNull StringColumn of(String name, int length) {
        return new StringColumn(name, length);
    }

    @Contract("_, _ -> new")
    public static @NotNull StringColumn ofNullable(String name, int length) {
        return new StringColumn(name, length, true, null);
    }

    @Contract("_, _, _ -> new")
    public static @NotNull StringColumn ofDefault(String name, int length, String defaultValue) {
        return new StringColumn(name, length, false, defaultValue);
    }

    public StringColumn(String name, int length) {
        super(name, "VARCHAR(" + length + ")",false,null,null);
    }

    public StringColumn(String name, int length, boolean nullable, String defaultValue) {
        super(name, "VARCHAR(" + length + ")", nullable, defaultValue, null);
    }

    @Override
    public String get(Object object) {
        return (String) object;
    }
}
