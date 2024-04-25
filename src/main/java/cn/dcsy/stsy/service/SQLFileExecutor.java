package cn.dcsy.stsy.service;

import java.io.IOException;

/**
 * @author DC_DC
 * Date: 2024/4/25/22:40
 */
public interface SQLFileExecutor {
    void executeSqlFile(String path) throws IOException;
}
