package co.aikar.idb.schema;

import java.util.StringJoiner;
public interface Key extends Attribute {
    static Key primary(Column<?>... columns) {
        return new PrimaryKey(columns);
    }

    static Key index(Column<?>... columns) {
        return new IndexKey(false, columns);
    }

    static Key index(boolean unique, Column<?>... columns) {
        return new IndexKey(unique, columns);
    }

    static Key unique(Column<?>... columns) {
        return new Unique(columns);
    }

    Column<?>[] getColumns();

    class PrimaryKey implements Key {

        Column<?>[] columns;

        public PrimaryKey(Column<?>... columns) {
            this.columns = columns;
        }

        @Override
        public Column<?>[] getColumns() {
            return columns;
        }

        @Override
        public String getDefinition() {
            StringJoiner joiner = new StringJoiner(",");
            for (Column<?> column : columns) {
                joiner.add(column.getName());
            }
            return "PRIMARY KEY (" + joiner.toString() + ")";
        }
    }

    class IndexKey implements Key {
        Column<?>[] columns;
        boolean unique;

        public IndexKey(boolean unique, Column<?>... columns) {
            this.unique = unique;
            this.columns = columns;
        }

        @Override
        public Column<?>[] getColumns() {
            return columns;
        }

        @Override
        public String getDefinition() {
            StringJoiner joiner = new StringJoiner(",");
            for (Column<?> column : columns) {
                joiner.add(column.getName());
            }
            return (unique ? "UNIQUE " : "") + "KEY (" + joiner.toString() + ")";
        }
    }

    class Unique implements Key {
        Column<?>[] columns;

        public Unique(Column<?>... columns) {
            this.columns = columns;
        }

        @Override
        public Column<?>[] getColumns() {
            return columns;
        }

        @Override
        public String getDefinition() {
            StringJoiner joiner = new StringJoiner(",");
            for (Column<?> column : columns) {
                joiner.add(column.getName());
            }
            return "UNIQUE (" + joiner.toString() + ")";
        }
    }
}
