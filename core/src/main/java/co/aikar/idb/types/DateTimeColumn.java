package co.aikar.idb.types;

import co.aikar.idb.schema.AbstractColumn;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;

public class DateTimeColumn extends AbstractColumn<Instant> {

    @Contract("_ -> new")
    public static @NotNull DateTimeColumn of(String name) {
        return new DateTimeColumn(name);
    }

    private DateTimeColumn(String name) {
        super(name, "DATETIME",false,null,null);
    }

    @Override
    public Instant get(Object object) {
        if (object instanceof Timestamp) {
            return ((Timestamp) object).toInstant();
        }
        return null;
    }

}
