package com.pm.cms.common.provier;

import java.util.List;
import java.util.Map;

/**
 * @Auther: derekhehe@yahoo.com
 * @Date: Created in 2018/8/22 09:28
 * @Description:
 * @Modified By:
 */
public class MiddleInsertProvider {

    public String insertMiddle1(Map map) {
        List<String> iccidList = (List<String>) map.get("list");
        Integer apkId = (Integer) map.get("apkId");
        Integer state = (Integer)map.get("state");
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO apk_box ");
        sb.append("(apk_id,iccid,state)");
        sb.append("VALUES ");
        for (int i = 0; i < iccidList.size(); i++)
        {
            sb.append("("+apkId+",");
            sb.append("'"+iccidList.get(i)+"'"+",");
            sb.append(state+")");
            if(i <iccidList.size()-1)
            {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public String insertRtfImageMid(Map map) {
        List<Integer> imageList = (List<Integer>) map.get("list");
        Integer rtfId = (Integer) map.get("rtfId");
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO cms_rtf_image ");
        sb.append("(rtf_id,image_id)");
        sb.append("VALUES ");
        for (int i = 0; i < imageList.size(); i++)
        {
            sb.append("("+rtfId+",");
            sb.append(imageList.get(i)+")");
            if(i <imageList.size()-1)
            {
                sb.append(",");
            }
        }
        return sb.toString();
    }
}
