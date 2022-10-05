package co.aikar.idb.types;

import co.aikar.idb.schema.AbstractColumn;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class LongTextColumn extends AbstractColumn<String> {

    @Contract("_ -> new")
    public static @NotNull LongTextColumn of(String name) {
        return new LongTextColumn(name);
    }

    @Contract("_ -> new")
    public static @NotNull LongTextColumn ofNullable(String name) {
        return new LongTextColumn(name, true, null);
    }

    @Contract("_, _ -> new")
    public static @NotNull LongTextColumn ofDefault(String name, String defaultValue) {
        return new LongTextColumn(name, false, defaultValue);
    }

    public LongTextColumn(String name) {
        super(name, "LONGTEXT",false,null,null);
    }

    public LongTextColumn(String name, boolean nullable, String defaultValue) {
        super(name, "LONGTEXT", nullable, defaultValue, null);
    }

    @Override
    public String get(Object object) {
        return (String) object;
    }

}
