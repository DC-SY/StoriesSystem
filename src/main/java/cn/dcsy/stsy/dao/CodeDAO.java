package cn.dcsy.stsy.dao;

import cn.dcsy.stsy.mappers.CodeMapper;
import cn.dcsy.stsy.models.doData.MailCodeDO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * @author DC_DC
 * Date: 2024/4/28/16:08
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class CodeDAO {
    private  final CodeMapper codeMapper;

    public Boolean insertCode(MailCodeDO mailCodeDO){
        return codeMapper.insertCode(mailCodeDO);
    }
}
