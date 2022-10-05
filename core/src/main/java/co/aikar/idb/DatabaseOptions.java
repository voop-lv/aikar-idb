package co.aikar.idb;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.logging.Logger;

@SuppressWarnings("UnusedAssignment")
@Builder(toBuilder = true) @Data
public class DatabaseOptions {
    private static final DatabaseTiming NULL_TIMING = new NullDatabaseTiming();
    /**
     * JDBC DSN to connect to
     */
    @NonNull String dsn;
    /**
     * JDBC Classname of the Driver name to use
     */
    @NonNull String driverClassName;
    /**
     * Class name of DataSource to use
     */
    String dataSourceClassName;
    String defaultIsolationLevel;

    private static int minAsyncThreadsCount = 2;
    @Builder.Default boolean displayConnectionInfo = true;

    @Builder.Default boolean favorDataSourceOverDriver = true;

    @Builder.Default String poolName = "DB";
    @Builder.Default boolean useOptimizations = true;

    /**
     * For Async queries, minimum threads in the pool to use.
     */
    @Builder.Default int minAsyncThreads = Math.min(Runtime.getRuntime().availableProcessors(), minAsyncThreadsCount);
    /**
     * For Async queries, maximum threads in the pool to use.
     */
    @Builder.Default int maxAsyncThreads = Runtime.getRuntime().availableProcessors();
    @Builder.Default int asyncThreadTimeout = 60;
    @Builder.Default TimingsProvider timingsProvider = (name, parent) -> NULL_TIMING;
    @Builder.Default Consumer<Exception> onFatalError = DB::logException;
    @Builder.Default Consumer<Exception> onDatabaseConnectionFailure = DB::logException;


    String user;
    String pass;
    Logger logger;
    ExecutorService executor;

    public static class DatabaseOptionsBuilder {

        public DatabaseOptionsBuilder postgresSQL(@NonNull String user, @NonNull String pass, @NonNull String db, @Nullable String hostAndPort) {
            if (hostAndPort == null) {
                hostAndPort = "localhost:5432";
            }

            this.user = user;
            this.pass = pass;

            //if (dataSourceClassName == null) tryDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");
            if (dataSourceClassName == null) tryDataSourceClassName("org.postgresql.jdbc3.Jdbc3PoolingDataSource");
            if (driverClassName == null) tryDriverClassName("org.postgresql.Driver");

            if (defaultIsolationLevel == null) defaultIsolationLevel = "TRANSACTION_READ_COMMITTED";
            this.dsn = "postgresql://" + hostAndPort + "/" + db;
            return this;
        }

        public DatabaseOptionsBuilder mysql(@NonNull String user, @NonNull String pass, @NonNull String db, @Nullable String hostAndPort) {
            if (hostAndPort == null) {
                hostAndPort = "localhost:3306";
            }
            this.user = user;
            this.pass = pass;

            if (defaultIsolationLevel == null) defaultIsolationLevel = "TRANSACTION_READ_COMMITTED";

            if (dataSourceClassName == null) tryDataSourceClassName("org.mariadb.jdbc.MariaDbDataSource");
            if (dataSourceClassName == null) tryDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
            if (dataSourceClassName == null) tryDataSourceClassName("com.mysql.cj.jdbc.MysqlDataSource");

            if (driverClassName == null) tryDriverClassName("org.mariadb.jdbc.Driver");
            if (driverClassName == null) tryDriverClassName("com.mysql.cj.jdbc.Driver");
            if (driverClassName == null) tryDriverClassName("com.mysql.jdbc.Driver");

            this.dsn = "mysql://" + hostAndPort + "/" + db;
            return this;
        }

        public DatabaseOptionsBuilder sqlite(@NonNull String fileName) {
            if (defaultIsolationLevel == null) defaultIsolationLevel = "TRANSACTION_SERIALIZABLE";

            if (dataSourceClassName == null) tryDataSourceClassName("org.sqlite.SQLiteDataSource");

            if (driverClassName == null) tryDriverClassName("org.sqlite.JDBC");

            this.dsn = "sqlite:" + fileName;

            return this;
        }

        public DatabaseOptionsBuilder setMinAsyncThreadsCount(int value) {
            minAsyncThreadsCount = value;
            return this;
        }

        /**
         * Tries the specified JDBC driverClassName, and uses it if it is valid. Does nothing if a Driver is already set
         */
        public DatabaseOptionsBuilder tryDriverClassName(@NonNull String className) {
            try {
                driverClassName(className);
            } catch (Exception ignored) {}
            return this;
        }


        /**
         * Tries the specified JDBC DataSource, and uses it if it is valid. Does nothing if a DataSource is already set
         */
        public DatabaseOptionsBuilder tryDataSourceClassName(@NonNull String className) {
            try {
                dataSourceClassName(className);
            } catch (Exception ignored) {}
            return this;
        }

        public DatabaseOptionsBuilder driverClassName(@NonNull String className) {
            try {
                Class.forName(className).newInstance();
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            this.driverClassName = className;
            return this;
        }

        public DatabaseOptionsBuilder dataSourceClassName(@NonNull String className) {
            try {
                Class.forName(className);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            this.dataSourceClassName = className;
            return this;
        }
    }

}
