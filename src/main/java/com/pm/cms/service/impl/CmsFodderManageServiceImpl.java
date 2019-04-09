package com.pm.cms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.pm.common.dto.SysResult;
import com.pm.cms.common.content.ConstantContext;
import com.pm.cms.dto.SelectFodderManageDto;
import com.pm.cms.mapper.CmsFodderManageMapper;
import com.pm.cms.service.CmsFodderManageService;
import com.pm.cms.vo.CmsFodderManageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 功能描述：
 * 【素材展示impl】
 *
 * @author huhaiqiang
 * @version V1.0
 * @date 2019/02/20 18:01
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class CmsFodderManageServiceImpl implements CmsFodderManageService {

    @Autowired
    private CmsFodderManageMapper cmsFodderManageMapper;

    @Override
    public SysResult selectFodderManage(SelectFodderManageDto selectFodderManageDto) {
        List<CmsFodderManageVo> cmsFodders = cmsFodderManageMapper.selectFodderByIccid(selectFodderManageDto);
        return SysResult.oK(cmsFodders);
    }

    @Override
    public SysResult deleteShelterFodder(Integer[] shelterFodderIds) {
        try {
            List<Integer> updateStateList = cmsFodderManageMapper.selectCountByShelterFodderIds(shelterFodderIds);
            for (Integer updateState : updateStateList) {
                String errorMsg = ConstantContext.UPDATE_STATE_MAP.get(updateState);
                if (StrUtil.isNotBlank(errorMsg)) {
                    return SysResult.build(300, errorMsg);
                }
            }
            cmsFodderManageMapper.deleteShelterFodder(shelterFodderIds);
        } catch (Exception e) {
            log.error("素材修改异常：", e);
            return SysResult.fail();
        }
        return SysResult.oK();
    }
}
