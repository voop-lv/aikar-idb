package co.aikar.idb.types;

import co.aikar.idb.schema.AbstractColumn;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class DoubleColumn extends AbstractColumn<Double> {

    @Contract("_ -> new")
    public static @NotNull DoubleColumn of(String name) {
        return new DoubleColumn(name);
    }

    @Contract("_ -> new")
    public static @NotNull DoubleColumn ofNullable(String name) {
        return new DoubleColumn(name, true, null, null);
    }

    @Contract("_, _ -> new")
    public static @NotNull DoubleColumn ofDefault(String name, int defaultValue) {
        return new DoubleColumn(name, false, String.valueOf(defaultValue), null);
    }

    @Contract("_, _ -> new")
    public static @NotNull DoubleColumn ofGenerated(String name, String generatedAs) {
        return new DoubleColumn(name, true, null, generatedAs);
    }

    private DoubleColumn(String name) {
        super(name, "DOUBLE",false,null,null);
    }

    public DoubleColumn(String name,boolean nullable, String defaultValue, String generatedAs) {
        super(name, "DOUBLE", nullable, defaultValue, generatedAs);
    }

    @Override
    public Double get(Object object) {
        if (object instanceof BigDecimal) {
            return ((BigDecimal) object).doubleValue();
        }
        return (Double) object;
    }

}
