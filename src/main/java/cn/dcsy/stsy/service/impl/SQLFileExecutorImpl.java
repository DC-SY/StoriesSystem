package cn.dcsy.stsy.service.impl;

import cn.dcsy.stsy.service.SQLFileExecutor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * @author 32841
 */
@Service
public class SQLFileExecutorImpl implements SQLFileExecutor {
    private final JdbcTemplate jdbcTemplate;
    private final ResourceLoader resourceLoader;

    public SQLFileExecutorImpl(JdbcTemplate jdbcTemplate, ResourceLoader resourceLoader) {
        this.jdbcTemplate = jdbcTemplate;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void executeSqlFile(String path) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + path);
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            String sqlScript = reader.lines().collect(Collectors.joining("\n"));
            for (String sqlStatement : sqlScript.split(";")) {
                sqlStatement = sqlStatement.trim();
                if (!sqlStatement.isEmpty()) {
                    jdbcTemplate.execute(sqlStatement);
                }
            }
        }
    }
}
