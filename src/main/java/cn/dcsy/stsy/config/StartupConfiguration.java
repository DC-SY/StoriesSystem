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
//
//    @Bean
//    @Order(1)
//    public CommandLineRunner sqlPreparation(JdbcTemplate jdbcTemplate) {
//        log.info("============================================================");
//        log.info("[Preparation] 系统进行准备检查");
//        return args -> {
//            log.info("[Preparation] SQL数据进行准备检查");
//            try {
//                log.info("\t检查用户信息数据表是否存在");
////                if (){
////
////                } else {
////
////                }
//
////                jdbcTemplate.execute("""
////                        create table if not exists stories_system.user_data
////                        (
////                            uid         bigint unsigned not null comment '用户表序号'
////                                primary key,
////                            uuid        char(36)        not null comment '用户唯一识别码',
////                            name        varchar(100)    not null comment '用户名',
////                            gender      varchar(10)     null comment '用户性别',
////                            email       varchar(100)    not null comment '用户邮箱',
////                            password    varchar(255)    not null comment '用户密码',
////                            avatar      text            null comment '用户头像',
////                            create_at   timestamp       not null comment '用户创建时间',
////                            deleted_at  timestamp       null comment '用户删除时间',
////                            update_at   timestamp       null comment '用户更新时间',
////                            all_stories json            null comment '与用户相关的所有故事');
////                        """);
////                log.info("\t\t检查用户信息数据表索引是否存在");
//
//                log.info("\t检查故事信息数据表是否存在");
//                jdbcTemplate.execute("CREATE INDEX uuid_index ON user_data (uuid)");
//                log.info("\t检查用户验证数据表是否存在");
//                jdbcTemplate.execute("""
//                        create table code
//                        (
//                            cid           bigint unsigned auto_increment comment '主键'
//                                primary key,
//                            email         varchar(100) not null comment '邮箱',
//                            code          char(6)      not null comment '邮箱验证码',
//                            created_time  timestamp    not null comment '验证码创建时间',
//                            update_time   timestamp    null comment '验证码修改时间',
//                            times         int          null comment '验证错误次数',
//                            modified_time timestamp    null comment '上次错误校验时间',
//                            constraint code_user_data_email_fk
//                                foreign key (email) references user_data (email)
//                                    on update cascade on delete cascade
//                        )
//                            comment '校验表';
//                        """);
//            } catch (DataAccessException ex) {
//                log.error("Database initialization failed", ex);
//            }
//
//        };
//    }
}
