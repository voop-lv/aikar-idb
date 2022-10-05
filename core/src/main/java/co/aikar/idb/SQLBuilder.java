package co.aikar.idb;

import co.aikar.idb.schema.Column;
import co.aikar.idb.schema.Table;

import java.math.BigDecimal;
import java.sql.*;
import java.util.StringJoiner;
public class SQLBuilder implements AutoCloseable {
    private Connection connection;
    private PreparedStatement ps;
    private ResultSet set;
    private StringBuilder statement = new StringBuilder();

    public SQLBuilder() throws SQLException {
        connection = DB.getGlobalDatabase().getConnection();
    }

    public SQLBuilder(Connection connection) {
        this.connection = connection;
    }

    public String getStatement() {
        return statement.toString();
    }

    public SQLBuilder createTable(Table table, boolean ifNotExists) {
        statement.append("CREATE TABLE").append(ifNotExists ? " IF NOT EXISTS " : " ").append(table.getName())
                .append("(").append(table.getTableAttributes()).append(")");
        return this;
    }

    public String getColumnsAsString(Column<?>... columns) {
        String columnQuery;
        if (columns.length == 0) {
            columnQuery = "*";
        } else {
            StringJoiner joiner = new StringJoiner(",");
            for (Column<?> column : columns) {
                joiner.add(column.getName());
            }
            columnQuery = joiner.toString();
        }
        return columnQuery;
    }

    public SQLBuilder select(Table table, Column<?>... columns) {
        statement.append("SELECT ").append(getColumnsAsString(columns)).append(" FROM ").append(table.getName());
        return this;
    }

    public SQLBuilder insert(Table table, Column<?>... columns) {
        statement.append("INSERT INTO ").append(table.getName());
        if (columns.length > 0) {
            statement.append(" (").append(getColumnsAsString(columns)).append(")");
        }
        return this;
    }

    public SQLBuilder insertIgnore(Table table, Column<?>... columns) {
        statement.append("INSERT IGNORE INTO ").append(table.getName());
        if (columns.length > 0) {
            statement.append(" (").append(getColumnsAsString(columns)).append(")");
        }
        return this;
    }

    public SQLBuilder values(String values) {
        statement.append(" VALUES (").append(values).append(")");
        return this;
    }

    public SQLBuilder onDuplicateKeyUpdate(String assignment) {
        statement.append(" ON DUPLICATE KEY UPDATE ").append(assignment);
        return this;
    }

    public SQLBuilder update(Table table, Column<?>... columns) {
        statement.append("UPDATE ").append(table.getName()).append(" SET ");
        for (int i = 0; i < columns.length; i++) {
            String column = columns[i].getName();
            statement.append(column).append(" = ?");

            if (i + 1 < columns.length) {
                statement.append(", ");
            }
        }
        return this;
    }

    public SQLBuilder updateRaw(Table table) {
        statement.append("UPDATE ").append(table.getName());
        return this;
    }

    public SQLBuilder set() {
        statement.append(" SET ");
        return this;
    }

    public SQLBuilder delete(Table table) {
        statement.append("DELETE FROM ").append(table.getName());
        return this;
    }

    public SQLBuilder where(String condition) {
        if (!condition.isEmpty()) {
            statement.append(" WHERE ").append(condition);
        }
        return this;
    }

    public SQLBuilder whereColumnIs(Column<?> column) {
        return where(column.getName() + " = ?");
    }

    public SQLBuilder and(String condition) {
        statement.append(" AND ").append(condition);
        return this;
    }

    public SQLBuilder andColumnIs(Column<?> column) {
        return and(column.getName() + " = ?");
    }

    public SQLBuilder or(String condition) {
        statement.append(" OR ").append(condition);
        return this;
    }

    public SQLBuilder orColumnIs(Column<?> column) {
        return or(column.getName() + " = ?");
    }

    public SQLBuilder in(String values) {
        if (!values.isEmpty()) {
            statement.append(" IN (").append(values).append(")");
        }
        return this;
    }

    public SQLBuilder orderBy(Column<?> column, boolean ascending) {
        statement.append(" ORDER BY ").append(column.getName()).append(ascending ? " ASC" : " DESC");
        return this;
    }

    public SQLBuilder limit(int limit) {
        statement.append(" LIMIT ").append(limit);
        return this;
    }

    public SQLBuilder join(Table table, String condition) {
        statement.append(" JOIN ").append(table.getName()).append(" ON ").append(condition);
        return this;
    }

    public SQLBuilder leftJoin(Table table, String condition) {
        statement.append(" LEFT JOIN ").append(table.getName()).append(" ON ").append(condition);
        return this;
    }

    public SQLBuilder incrInteger(Column<?> column, int amount) {
        statement.append(column.getName()).append(" = ").append(column.getName()).append(" + ").append(amount);
        return this;
    }

    public SQLBuilder setStatement(String statement) {
        this.statement = new StringBuilder(statement);
        return this;
    }

    public SQLBuilder addStatement(String sql) throws SQLException {
        if (ps == null) {
            createStatement();
        }
        ps.addBatch(sql);
        return this;
    }

    public SQLBuilder queue(Object... params) throws SQLException {
        if (ps == null) {
            createStatement();
        }
        fillParams(params);
        ps.addBatch();
        return this;
    }

    public SQLBuilder clearParameters() throws SQLException {
        ps.clearParameters();
        statement = new StringBuilder();
        return this;
    }

    public SQLBuilder createStatement() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            ps = connection.prepareStatement(statement.toString());
        } else {
            throw new SQLException("Connection already closed");
        }
        return this;
    }

    public SQLBuilder createStatement(int autoGeneratedKeys) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            ps = connection.prepareStatement(statement.toString(), autoGeneratedKeys);
        } else {
            throw new SQLException("Connection already closed");
        }
        return this;
    }

    public SQLBuilder setString(int number, String val) throws SQLException {
        this.getPreparedStatement().setString(number, val);
        return this;
    }

    public SQLBuilder setObject(int number, Object obj) throws SQLException {
        this.getPreparedStatement().setObject(number, obj);
        return this;
    }

    public int executeUpdate(Object... params) throws SQLException {
        if (ps == null) {
            createStatement();
        }
        fillParams(params);
        return ps.executeUpdate();
    }

    public int[] executeBatch() throws SQLException {
        return ps.executeBatch();
    }

    public SQLBuilder executeQuery(Object... params) throws SQLException {
        if (ps == null) {
            createStatement();
        }
        fillParams(params);
        set = ps.executeQuery();
        return this;
    }

    protected void fillParams(Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i + 1, params[i]);
        }
    }

    public boolean next() throws SQLException {
        return set.next();
    }

    public Object getObject(int column) throws SQLException {
        return set.getObject(column);
    }

    public Object getObject(String column) throws SQLException {
        return set.getObject(column);
    }

    public String getString(String column) throws SQLException {
        return (String) this.getObject(column);
    }

    public String getString(int column) throws SQLException {
        return (String) getObject(column);
    }

    public Integer getInteger(String column) throws SQLException {
        Object obj = getObject(column);
        if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj).intValue();
        }
        return (Integer) obj;
    }

    public Long getLong(String column) throws SQLException {
        Object obj = getObject(column);
        if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj).longValue();
        }
        return (Long) obj;
    }

    public Timestamp getTimestamp(String column) throws SQLException {
        return (Timestamp) getObject(column);
    }

    public Timestamp getTimestamp(int column) throws SQLException {
        return (Timestamp) getObject(column);
    }

    public Date getDate(String column) throws SQLException {
        return (Date) getObject(column);
    }

    public Date getDate(int column) throws SQLException {
        return (Date) getObject(column);
    }

    public <T> T get(Column<T> column) throws SQLException {
        return column.get(getObject(column.getName()));
    }

    public PreparedStatement getPreparedStatement() {
        return ps;
    }

    public ResultSet getResultSet() {
        return set;
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public void close() {
        if (ps != null) {
            closeResource(ps);
        }
        if (set != null) {
            closeResource(set);
        }
        if (connection != null) {
            closeResource(connection);
        }
    }

    protected void closeResource(AutoCloseable resource) {
        try {
            resource.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
