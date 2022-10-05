package co.aikar.idb.types;

import co.aikar.idb.schema.AbstractColumn;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class BooleanColumn extends AbstractColumn<Boolean> {

    @Contract("_ -> new")
    public static @NotNull BooleanColumn of(String name) {
        return new BooleanColumn(name);
    }

    @Contract("_, _ -> new")
    public static @NotNull BooleanColumn ofDefault(String name, boolean defaultValue) {
        return new BooleanColumn(name, defaultValue);
    }

    private BooleanColumn(String name) {
        super(name, "BIT(1)",false,null,null);
    }

    private BooleanColumn(String name, boolean defaultValue) {
        super(name, "BIT(1)", false, String.valueOf(defaultValue), null);
    }

    @Override
    public Boolean get(Object object) {
        return (Boolean) object;
    }

}
