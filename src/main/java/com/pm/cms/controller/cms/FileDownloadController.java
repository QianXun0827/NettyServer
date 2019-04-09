package com.pm.cms.controller.cms;

import com.pm.cms.service.CmsFodderCentreService;
import com.pm.common.annotation.ControllerSysLogger;
import com.pm.common.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;

/**
 * @author huhaiqiang
 * @date 2019/02/20
 */
@Controller
@RequestMapping("/downLoad")
@Slf4j
public class FileDownloadController {

    @Autowired
    private CmsFodderCentreService cmsFodderCentreService;

    @ControllerSysLogger(type = "/downLoad/fodder/{fodderId}",description = "下载素材文件")
    @RequestMapping(value = "/fodder/{fodderId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> fodderFileDownLoad(@PathVariable("fodderId") Integer fodderId) {
        try {
            String fileUrl = (String) cmsFodderCentreService.selectFodderPathById(fodderId).getData();
            File file = new File(fileUrl);
            return FileUtils.downLoad(file, file.getName());
        } catch (Exception e) {
            log.error("文件下载异常：", e);
        }
        return null;
    }
}
