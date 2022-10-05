package co.aikar.idb.schema;

import java.util.StringJoiner;

public interface Table {
    String getName();
    Attribute[] getAttributes();

    default String getTableAttributes() {
        StringJoiner joiner = new StringJoiner(",");
        for (Attribute attribute : getAttributes()) {
            joiner.add(attribute.getDefinition());
        }
        return joiner.toString();
    }
}
