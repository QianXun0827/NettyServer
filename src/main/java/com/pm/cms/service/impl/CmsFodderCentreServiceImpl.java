package com.pm.cms.service.impl;

import cn.hutool.core.util.ArrayUtil;
import com.pm.cms.auth.AuthService;
import com.pm.cms.mapper.CmsFodderMapper;
import com.pm.cms.pojo.CmsFodder;
import com.pm.cms.service.CmsFodderCentreService;
import com.pm.common.dto.SysResult;
import com.pm.common.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 功能描述：
 * 【素材中心impl】
 *
 * @author huhaiqiang
 * @version V1.0
 * @date 2019/02/18 15:53
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class CmsFodderCentreServiceImpl implements CmsFodderCentreService {

    @Autowired
    private CmsFodderMapper cmsFodderMapper;
    @Autowired
    private AuthService authService;

    @Override
    public SysResult insertFodder(CmsFodder cmsFodder) {
        try {
            cmsFodder.setAuditStatus(0);
            cmsFodder.setIsDelete(1);
            cmsFodderMapper.insertFodder(cmsFodder);

//            TODO 本地不调用，测试/正式服调用
//           authService.auditFodderContent(cmsFodder);
        } catch (Exception e) {
            log.error("素材新增异常：{}", e);
            return SysResult.fail("素材新增失败");
        }
        return SysResult.oK();
    }

    @Override
    public SysResult selectFodder(Integer type, String fodderName) {
        List<CmsFodder> cmsFodders = cmsFodderMapper.selectFodderByDto(type, fodderName);
        return SysResult.oK(cmsFodders);
    }

    @Override
    public SysResult selectFodderByAuditStatus(Integer updateStatus) {
        List<CmsFodder> cmsFodders = cmsFodderMapper.selectFodderByAuditStatus(updateStatus);
        return SysResult.oK(cmsFodders);
    }

    @Override
    public SysResult updateFodder(Integer fodderId, String fodderName, Integer auditStatus) {
        if (fodderId == null) {
            return SysResult.fail("素材编号丢失");
        }
        if (auditStatus == 0) {
            return SysResult.build(300, "审核中素材不能修改");
        }
        try {
            cmsFodderMapper.updateFodder(fodderId, fodderName, auditStatus);
        } catch (Exception e) {
            log.error("素材修改异常：{}", e);
            return SysResult.fail("素材修改失败");
        }
        return SysResult.oK();
    }

    @Override
    public SysResult deleteFodder(Integer[] fodderIds) {
        if (ArrayUtil.isEmpty(fodderIds)) {
            return SysResult.fail("素材编号丢失");
        }
        try {
            int count = cmsFodderMapper.selectCountByFodderIds(fodderIds);
            if (count == 0) {
                for (Integer fodderId : fodderIds) {
                    CmsFodder cmsFodder = cmsFodderMapper.selectFodderInfoById(fodderId);
                    deleteRealFile(fodderId, cmsFodder);
                }
                cmsFodderMapper.deleteFodder(fodderIds);
            } else {
                return SysResult.build(300, "选中素材正被站牌使用");
            }

        } catch (Exception e) {
            log.error("素材删除异常：{}", e);
            return SysResult.fail("素材删除失败");
        }
        return SysResult.oK();
    }

    /**
     * 删除服务器文件
     *
     * @param fodderId  素材主键
     * @param cmsFodder CmsFodder实体
     */
    private void deleteRealFile(Integer fodderId, CmsFodder cmsFodder) {
        if (cmsFodder.getFodderType() != 3) {
            String currentUrl = cmsFodder.getFodderContext();
            boolean flag = FileUtils.deleteFile(currentUrl);
            if (flag) {
                log.info("服务器文件已删除,素材id为：" + fodderId);
            } else {
                log.info("服务器文件删除失败,素材id为：" + fodderId);
            }
        }
    }

    @Override
    public SysResult selectFodderPathById(Integer fodderId) {
        if (null == fodderId) {
            return SysResult.fail("素材id丢失");
        }
        CmsFodder cmsFodder = cmsFodderMapper.selectFodderInfoById(fodderId);
        String fodderPath = cmsFodder.getFodderContext();
        Integer isDelete = cmsFodder.getIsDelete();
        if (isDelete == 0) {
            return SysResult.build(300, "素材已被删除");
        }
        return SysResult.oK(fodderPath);
    }

}
