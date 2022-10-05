package co.aikar.idb.types;

import co.aikar.idb.schema.AbstractColumn;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class IntegerColumn extends AbstractColumn<Integer> {

    private final boolean autoIncrement;

    public static IntegerColumn of(String name, boolean autoIncrement) {
        return new IntegerColumn(name, autoIncrement);
    }

    @Contract("_ -> new")
    public static @NotNull IntegerColumn of(String name) {
        return of(name, false);
    }

    @Contract("_ -> new")
    public static @NotNull IntegerColumn ofNullable(String name) {
        return new IntegerColumn(name, true, null, null);
    }

    @Contract("_, _ -> new")
    public static @NotNull IntegerColumn ofDefault(String name, int defaultValue) {
        return new IntegerColumn(name, false, String.valueOf(defaultValue), null);
    }

    @Contract("_, _ -> new")
    public static @NotNull IntegerColumn ofGenerated(String name, String generatedAs) {
        return new IntegerColumn(name, true, null, generatedAs);
    }

    private IntegerColumn(String name, boolean autoIncrement) {
        super(name, "INTEGER",false,null,null);
        this.autoIncrement = autoIncrement;
    }

    private IntegerColumn(String name, boolean nullable, String defaultValue, String generatedAs) {
        super(name, "INTEGER", nullable, defaultValue, generatedAs);
        this.autoIncrement = false;
    }

    @Override
    public Integer get(Object object) {
        if (object instanceof BigDecimal) {
            return ((BigDecimal) object).intValue();
        }
        return (Integer) object;
    }

    @Override
    public String getDefinition() {
        String def = super.getDefinition();
        if (autoIncrement) {
            def = def + " AUTO_INCREMENT";
        }
        return def;
    }

}
