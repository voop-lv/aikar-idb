package co.aikar.idb;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.Map;

@SuppressWarnings("UnusedAssignment")
@Builder(toBuilder = true) @Data
public class PooledDatabaseOptions {
    @Builder.Default int minIdleConnections = 3;
    @Builder.Default int maxConnections = 5;
    @Builder.Default int connectionTimeout = 30 * 1000;
    @Builder.Default int leakDetectionThreshold = 30 * 1000;
    Map<String, Object> dataSourceProperties;
    @NonNull DatabaseOptions options;

    public static class PooledDatabaseOptionsBuilder  {
        public HikariPooledDatabase createHikariDatabase() {
            return new HikariPooledDatabase(this.build());
        }
    }
}
