package com.pm.cms.service.impl;

import com.pm.cms.mapper.CoreMapper;
import com.pm.cms.service.CoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 功能描述：
 * 【核心impl】
 *
 * @author huhaiqiang
 * @version V1.0
 * @date 2019/02/22 11:52
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class CoreServiceImpl implements CoreService {

    @Autowired
    private CoreMapper coreMapper;

    @Override
    public int getAuth(String iccid) {
        return coreMapper.getAuth(iccid);
    }
}
