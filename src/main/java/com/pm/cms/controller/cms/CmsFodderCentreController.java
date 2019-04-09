package com.pm.cms.controller.cms;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.pm.common.dto.SysResult;
import com.pm.cms.common.content.PropertiesContext;
import com.pm.cms.pojo.CmsFodder;
import com.pm.cms.service.CmsFodderCentreService;
import com.pm.common.annotation.ControllerSysLogger;
import com.pm.common.utils.FileResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 功能描述：
 * 【素材中心controller】
 *
 * @author huhaiqiang
 * @version V1.0
 * @date 2019/02/18 15:35
 */
@Controller
@RequestMapping("/cms")
@ResponseBody
@Slf4j
public class CmsFodderCentreController {

    @Autowired
    private CmsFodderCentreService cmsFodderCentreService;
    @Autowired
    private PropertiesContext propertiesContext;

    @ControllerSysLogger(type = "/cms/insertFodder", description = "新增素材")
    @RequestMapping("/insertFodder")
    public SysResult insertFodder(CmsFodder cmsFodder) {
        return cmsFodderCentreService.insertFodder(cmsFodder);
    }

    @ControllerSysLogger(type = "/cms/selectFodder/{type}/{fodderName}", description = "查询素材")
    @RequestMapping("/selectFodder/{type}/{fodderName}")
    public SysResult selectFodder(@PathVariable("type") Integer type, @PathVariable("fodderName") String fodderName) {
        return cmsFodderCentreService.selectFodder(type, fodderName);
    }

    @ControllerSysLogger(type = "/cms/selectFodderByAuditStatus/{auditStatus}", description = "查询素材")
    @RequestMapping("/selectFodderByAuditStatus/{auditStatus}")
    public SysResult selectFodderByAuditStatus(@PathVariable("auditStatus") Integer auditStatus) {
        return cmsFodderCentreService.selectFodderByAuditStatus(auditStatus);
    }

    @ControllerSysLogger(type = "/cms/updateFodder", description = "修改素材")
    @RequestMapping("/updateFodder")
    public SysResult updateFodder(Integer fodderId, String fodderName, Integer auditStatus) {
        return cmsFodderCentreService.updateFodder(fodderId, fodderName, auditStatus);
    }

    @ControllerSysLogger(type = "/cms/deleteFodder", description = "删除素材(逻辑删除)")
    @RequestMapping("/deleteFodder")
    public SysResult deleteFodder(@RequestParam(value = "fodderIds[]") Integer[] fodderIds) {
        return cmsFodderCentreService.deleteFodder(fodderIds);
    }

    @ControllerSysLogger(type = "/cms/previewFodder", description = "预览素材")
    @RequestMapping("/previewFodder")
    public SysResult previewFodder(Integer fodderId) {
        return cmsFodderCentreService.selectFodderPathById(fodderId);
    }

    @ControllerSysLogger(type = "/cms/fodder/fileUpload", description = "上传素材文件")
    @RequestMapping(value = "/fodder/fileUpload", method = {RequestMethod.GET, RequestMethod.POST})
    public SysResult adsFileUpload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) {
        if (null == file) {
            return SysResult.build(201, "文件上传失败");
        } else {
            String suffix = request.getParameter("fileName");
            FileResult fileResult = uploadFile(file, suffix);
            return SysResult.oK(fileResult);
        }
    }

    private FileResult uploadFile(MultipartFile uploadFile, String suffix) {
        FileResult fileResult = new FileResult();
        String fileName = uploadFile.getOriginalFilename();
        fileName = StrUtil.removeAll(fileName, "，");
        fileName = StrUtil.removeAll(fileName, ",");
        String datePathDir = (new SimpleDateFormat("yyyy/MM/dd/HH")).format(new Date());
        String millis = String.valueOf(DateUtil.current(false));
        int randomNum = new Random().nextInt(999);
        String randomPath = millis + randomNum;
        String dirPath = propertiesContext.getRepositoryPath();
        String url = propertiesContext.getImageBaseUrl();
        String LocalPath = dirPath + datePathDir;

        try {
            File file = new File(LocalPath);
            if (!file.exists()) {
                file.mkdirs();
            }

            String localPathFile = LocalPath + "/" + randomPath + fileName;
            if (StrUtil.isNotBlank(suffix)) {
                localPathFile = LocalPath + "/" + randomPath + fileName + suffix;
            }

            uploadFile.transferTo(new File(localPathFile));
            String urlPath = url + datePathDir + "/" + randomPath + fileName;
            fileResult.setCurrentUrl(localPathFile);
            fileResult.setDownloadUrl(urlPath);
            return fileResult;
        } catch (Exception e) {
            log.error("文件上传异常【{}】", e);
            return null;
        }
    }

}
