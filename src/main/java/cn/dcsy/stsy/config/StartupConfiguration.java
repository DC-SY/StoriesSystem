package cn.dcsy.stsy.config;

import cn.dcsy.stsy.service.SQLFileExecutor;
import cn.dcsy.stsy.service.impl.SQLFileExecutorImpl;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author DC_DC
 * Date: 2024/4/23/20:37
 */
@Configuration
public class StartupConfiguration {

    private final SQLFileExecutor sqlFileExecutor;

    private static final Logger log = LoggerFactory.getLogger(StartupConfiguration.class);

    @Autowired
    public StartupConfiguration(SQLFileExecutor sqlFileExecutor) {
        this.sqlFileExecutor = sqlFileExecutor;
    }


    @Bean
    @Order(1)
    public CommandLineRunner sqlPreparation(JdbcTemplate jdbcTemplate) {
        log.info("============================================================");
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
