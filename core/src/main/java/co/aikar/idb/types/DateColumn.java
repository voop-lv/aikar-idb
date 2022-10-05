package co.aikar.idb.types;

import co.aikar.idb.schema.AbstractColumn;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.time.LocalDate;

public class DateColumn extends AbstractColumn<LocalDate> {

    @Contract("_ -> new")
    public static @NotNull DateColumn of(String name) {
        return new DateColumn(name);
    }

    private DateColumn(String name) {
        super(name, "DATE",false,null,null);
    }

    @Override
    public LocalDate get(Object object) {
        if (object instanceof Date) {
            return ((Date) object).toLocalDate();
        }
        return null;
    }
}
