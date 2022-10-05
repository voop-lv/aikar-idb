package co.aikar.idb.schema;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor
public abstract class AbstractColumn<T> implements Column<T> {
    @Getter @NonNull private final String name,sqlType;
    @Getter private final boolean nullable;
    @Getter @Nullable private final String defaultValue,generatedAs;

    @Override
    public String getDefinition() {
        StringBuilder definitionBuilder = new StringBuilder();
        definitionBuilder.append(getName())
                .append(" ")
                .append(getSqlType());
        if (!isNullable()) definitionBuilder.append(" NOT NULL");
        if (defaultValue != null) definitionBuilder.append(" DEFAULT ").append(defaultValue);
        if (generatedAs != null) definitionBuilder.append(" GENERATED ALWAYS AS (").append(generatedAs).append(") PERSISTENT");
        return definitionBuilder.toString();
    }
}
