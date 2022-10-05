package co.aikar.idb.schema;

public interface Column<T> extends Attribute {
    T get(Object object);
    String getName();
    String getSqlType();
    boolean isNullable();
}
