package com.pm.cms.controller.cms;

import com.pm.common.dto.SysResult;
import com.pm.cms.dto.SelectFodderManageDto;
import com.pm.cms.service.CmsFodderManageService;
import com.pm.common.annotation.ControllerSysLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 功能描述：
 * 【素材展示Controller】
 *
 * @author huhaiqiang
 * @version V1.0
 * @date 2019/02/20 17:49
 */
@Controller
@RequestMapping("/cms")
@ResponseBody
@Slf4j
public class CmsFodderManageController {

    @Autowired
    private CmsFodderManageService cmsFodderManageService;

    @ControllerSysLogger(type = "/cms/selectFodderManage/{iccid}/{fodderName}/{type}/{updateState}",description = "查询节目管理列表")
    @RequestMapping("/selectFodderManage/{iccid}/{fodderName}/{type}/{updateState}")
    public SysResult selectFodderManage(@PathVariable("iccid") String iccid,
                                        @PathVariable("fodderName") String fodderName,
                                        @PathVariable("type") Integer type,
                                        @PathVariable("updateState") Integer updateState
                                        ) {
        SelectFodderManageDto selectFodderManageDto = new SelectFodderManageDto(iccid,fodderName,type,updateState);
        return cmsFodderManageService.selectFodderManage(selectFodderManageDto);
    }

    @ControllerSysLogger(type = "/cms/deleteShelterFodder",description = "删除选中节目管理")
    @RequestMapping("/deleteShelterFodder")
    public SysResult deleteShelterFodder(@RequestParam("shelterFodderIds[]") Integer[] shelterFodderIds) {
        return cmsFodderManageService.deleteShelterFodder(shelterFodderIds);
    }
}
