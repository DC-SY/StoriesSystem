package cn.dcsy.stsy.service.impl;

import cn.dcsy.stsy.mappers.CodeMapper;
import cn.dcsy.stsy.service.CleanCode;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author DC_DC
 * Date: 2024/4/28/20:35
 */
@Service
@RequiredArgsConstructor
public class CleanCodeImpl implements CleanCode {
    private final CodeMapper codeMapper;


    @Override
//    @Scheduled(cron = "0 */1 * * * ?")
    @Scheduled(cron = "0 0/10 * * * ?")
    public void scheduleCleanCode() {
        codeMapper.cleanCode();
    }
}
