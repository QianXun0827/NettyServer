package com.pm.cms.controller.cms;

import com.google.gson.Gson;
import com.pm.cms.service.AdditionService;
import com.pm.cms.vo.ShelterVo;
import com.pm.common.annotation.ControllerSysLogger;
import com.pm.common.dto.SysResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 站牌选择控制层
 *
 * @author huhaiqiang
 * @date 2018/08/07 17:37
 */
@Controller
@RequestMapping("/cms")
@ResponseBody
@Slf4j
public class AdditionController {

    @Autowired
    private AdditionService additionService;

    @ControllerSysLogger(type = "/cms/chooseShelter/{subareaNum}", description = "站牌信息获取")
    @RequestMapping("/chooseShelter/{subareaNum}")
    public SysResult chooseShelter(@PathVariable("subareaNum") Integer subareaNum) {
        return additionService.chooseShelter(subareaNum);
    }

    @ControllerSysLogger(type = "/cms/getShelter", description = "站牌信息获取")
    @RequestMapping("/getShelter")
    public String getShelter() {
        SysResult sysResult = additionService.chooseShelter(null);
        List<ShelterVo> list = (List<ShelterVo>) sysResult.getData();
        String str = new Gson().toJson(list);
        return "{\"value\":" + str + "}";
    }

}
