package cn.dcsy.stsy.config.startup;

import cn.dcsy.stsy.service.SQLFileExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.TimeZone;

/**
 * @author DC_DC
 * Date: 2024/4/23/20:37
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class StartupConfiguration {

    private final SQLFileExecutor sqlFileExecutor;

    @Bean
    @Order(1)
    public CommandLineRunner sqlPreparation(JdbcTemplate jdbcTemplate) {
        log.info("============================================================");
        TimeZone tz = TimeZone.getDefault();
        log.info("[Preparation] 当前时区为: {}", tz.getDisplayName());
        log.info("[Preparation] 配置数据库时区: Asia/Shanghai");
        log.info("[Preparation] 系统进行数据库完整性检查");
        return args -> {
            if (!(checkTableExists(jdbcTemplate, "user_data") &&
                    checkIndexExists(jdbcTemplate, "user_data", "uuid_index") &&
                    checkTableExists(jdbcTemplate, "user_stories") &&
                    checkIndexExists(jdbcTemplate, "user_stories", "user_stories_ssid_uindex"))) {
                log.info("[Preparation] 数据库缺失,准备建表");
                sqlFileExecutor.executeSqlFile("sql/CreateTables.sql");
                log.info("[Preparation] 数据库建表成功");
            } else {
                log.info("[Preparation] 数据库完整性检查通过");
            }

        };
    }

    private boolean checkTableExists(JdbcTemplate jdbcTemplate, String tableName) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = ?",
                Integer.class,
                tableName
        );
        if (count != null && count > 0) {
            log.info("\t->数据表 {} 存在", tableName);
            return true;
        } else {
            log.warn("\t->数据表 {} 不存在", tableName);
            return false;
        }
    }

    private boolean checkIndexExists(JdbcTemplate jdbcTemplate, String tableName, String indexName) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM information_schema.statistics WHERE table_name = ? AND index_name = ?",
                Integer.class,
                tableName,
                indexName
        );
        if (count != null && count > 0) {
            log.info("\t->数据表 {} 的索引 {} 存在", tableName, indexName);
            return true;
        } else {
            log.warn("\t->数据表 {} 的索引 {} 不存在", tableName, indexName);
            return false;
        }
    }
}
