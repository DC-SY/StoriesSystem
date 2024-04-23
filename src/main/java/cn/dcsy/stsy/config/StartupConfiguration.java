package cn.dcsy.stsy.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLSyntaxErrorException;

/**
 * @author DC_DC
 * Date: 2024/4/23/20:37
 */
@Configuration
public class StartupConfiguration {

    private static final Logger log = LoggerFactory.getLogger(StartupConfiguration.class);

    @Bean
    @Order(1)
    public CommandLineRunner sqlPreparation(JdbcTemplate jdbcTemplate) {
        log.info("[Runner]执行数据库初始化语句");
        return args -> {
            // 创建数据表
            // MySQL默认不支持在单个语句或批次中执行多个命令，需要分割SQL语句
            log.info("\t->创建 userdata 数据表");
            jdbcTemplate.execute(
                    """
                            create table if not exists user_data
                            (
                                uid         bigint unsigned not null comment '用户表序号'
                                    primary key,
                                uuid        char(36)        not null comment '用户唯一识别码',
                                name        varchar(100)    not null comment '用户名',
                                gender      varchar(10)     null comment '用户性别',
                                email       varchar(100)    not null comment '用户邮箱',
                                password    varchar(255)    not null comment '用户密码',
                                avatar      text            null comment '用户头像',
                                create_at   timestamp       not null comment '用户创建时间',
                                deleted_at  timestamp       null comment '用户删除时间',
                                update_at   timestamp       null comment '用户更新时间',
                                all_stories json            null comment '与用户相关的所有故事',
                                constraint user_data_email_uindex
                                    unique (email));
                            """
            );
            try {
                jdbcTemplate.execute("CREATE INDEX  uuid_index ON user_data (uuid)");
            } catch (DataAccessException ex) {
                SQLSyntaxErrorException sqlSyntaxErrorException = (SQLSyntaxErrorException) ex.getCause();
                if (sqlSyntaxErrorException.getMessage().contains("Duplicate key name")) {
                    log.info("\t->索引已经存在");
                } else {
                    log.error(String.valueOf(ex));
                }
            }
        };
    }
}
