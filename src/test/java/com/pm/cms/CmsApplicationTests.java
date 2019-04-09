package com.pm.cms;

import com.alibaba.fastjson.JSON;
import com.baidubce.BceClientConfiguration;
import com.baidubce.services.vcr.VcrClient;
import com.baidubce.services.vcr.model.GetImageAsyncResponse;
import com.baidubce.services.vcr.model.PutImageAsyncResponse;
import com.google.gson.Gson;
import com.pm.cms.auth.AuthService;
import com.pm.cms.common.content.PropertiesContext;
import com.pm.cms.common.util.RedisHelps;
import com.pm.cms.dto.SelectFodderManageDto;
import com.pm.cms.pojo.CmsFodder;
import com.pm.cms.service.CmsFodderCentreService;
import com.pm.cms.service.CmsFodderManageService;
import com.pm.cms.service.CmsUserService;
import com.pm.common.dto.SysResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CmsApplication.class)
@Slf4j
public class CmsApplicationTests {

    @Autowired
    private CmsFodderCentreService cmsFodderCentreService;
    @Autowired
    private CmsFodderManageService cmsFodderManageService;
    @Autowired
    private CmsUserService cmsUserService;
    @Autowired
    private RedisHelps redisHelps;
    @Autowired
    private AuthService authService;
    @Autowired
    private PropertiesContext propertiesContext;

    private Gson gson = new Gson();

    @Test
    public void selectFodder() {

        Integer type = 2;
        String fodderName = "我";

        SysResult sysResult = cmsFodderCentreService.selectFodder(type,fodderName);
        log.info(gson.toJson(sysResult));
    }

    @Test
    public void insertFodder() {
        CmsFodder cmsFodder = new CmsFodder();
        cmsFodder.setFodderName("冰雪奇缘2");
        cmsFodder.setFodderType(2);
        cmsFodder.setFodderContext("E:\\mnt\\file\\cms\\2019\\02\\22\\Idina Menzel - Let It Go.mp4");
        cmsFodder.setFileName("Idina Menzel - Let It Go.mp4");
        cmsFodder.setFileSize(0);
        cmsFodder.setFileTime("00:03:39");
        cmsFodder.setFileFormat("mp4");
        cmsFodder.setRemark("");

        SysResult sysResult = cmsFodderCentreService.insertFodder(cmsFodder);
        log.info(gson.toJson(sysResult));
    }

    @Test
    public void updateFodder() {
        Integer fodderId = 1;
        String fodderName = "我修改了";
        Integer fodderType = 1;

        SysResult sysResult = cmsFodderCentreService.updateFodder(fodderId, fodderName, fodderType);
        log.info(gson.toJson(sysResult));
    }

    @Test
    public void deleteFodder() {
        Integer[] fodderIds = {};

        SysResult sysResult = cmsFodderCentreService.deleteFodder(fodderIds);
        log.info(gson.toJson(sysResult));
    }

    @Test
    public void selectFodderManage() {
        SelectFodderManageDto selectFodderManageDto = new SelectFodderManageDto();
        selectFodderManageDto.setIccid("&1");
        selectFodderManageDto.setFodderName("&0");
        selectFodderManageDto.setType(0);
        selectFodderManageDto.setUpdateState(88);

        SysResult sysResult = cmsFodderManageService.selectFodderManage(selectFodderManageDto);
        log.info(gson.toJson(sysResult));
    }

    @Test
    public void redisHelps() {
        boolean delete = redisHelps.delete("111");
        log.info(delete + "");
    }

    @Test
    public void userLogin() {

        String userName = "";
        String passWord = "pmyun";

//        SysResult sysResult = cmsUserService.userLogin(userName, passWord, request, response);

//        log.info(gson.toJson(sysResult));
    }

    @Test
    public void checkImage() {
        String url = "http://47.98.225.240:12015/downLoad/fodder/9";
        String preset = "pm_default";

//        PutImageRequest request = new PutImageRequest();
//        request.setPreset(preset);
//        request.setSource(url);

        BceClientConfiguration config = authService.cipherkeySetting();
        VcrClient vcrClient = new VcrClient(config);

        PutImageAsyncResponse response = vcrClient.putImageAsync(url, preset, propertiesContext.getNotification());
        log.info(JSON.toJSONString(response));

    }

    @Test
    public void checkFodderImage() {
        String preset = "pm_default";
        String fodderPath = "http://47.98.225.240:12015/downLoad/fodder/17";

        BceClientConfiguration config = authService.cipherkeySetting();
        VcrClient vcrClient = new VcrClient(config);
        GetImageAsyncResponse response = vcrClient.getImageAsync(fodderPath, preset);
        log.info(JSON.toJSONString(response));
    }



}

