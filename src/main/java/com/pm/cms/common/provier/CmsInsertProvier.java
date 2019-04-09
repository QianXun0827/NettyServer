package com.pm.cms.common.provier;

import com.pm.cms.pojo.CmsShelterFodder;

import java.util.List;
import java.util.Map;

/**
 * cms模块动态sql
 *
 * @author huhaiqiang
 * @date 2018/08/08 15:36
 */
public class CmsInsertProvier {

    public String insertShelterFodderMiddle(Map map) {
        CmsShelterFodder cmsShelterFodder = (CmsShelterFodder) map.get("cmsShelterFodder");
        List<String> iccidList = cmsShelterFodder.getIccids();
        Integer fodderId = cmsShelterFodder.getFodderId();
        Integer publishType = cmsShelterFodder.getPublishType();
        Integer fodderBeginTime = cmsShelterFodder.getFodderBeginTime();
        Integer fodderEndTime = cmsShelterFodder.getFodderEndTime();
        Integer shelterMonitor = cmsShelterFodder.getShelterMonitor();
        Integer fodderArea = cmsShelterFodder.getFodderArea();
        Integer isVoice = cmsShelterFodder.getIsVoice();

        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO cms_shelter_fodder ");
        sb.append("(fodder_id,iccid,publish_type,fodder_area,is_voice,shelter_monitor,fodder_begin_time,fodder_end_time,update_state)");
        sb.append("VALUES ");

        for (int i = 0; i < iccidList.size(); ++i) {
            sb.append("(").append(fodderId).append(",")
                    .append("'").append(iccidList.get(i)).append("',")
                    .append(publishType).append(",")
                    .append(fodderArea).append(",")
                    .append(isVoice).append(",")
                    .append(shelterMonitor).append(",")
                    .append(fodderBeginTime).append(",")
                    .append(fodderEndTime).append(",")
                    .append(0 + ")");
            if (i < iccidList.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }


    public String insertHistoryFodder(Map map) {
        List<Integer> adsIdList = (List) map.get("list");
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO cms_history_fodder ");
        sb.append("(fodder_id)");
        sb.append("VALUES ");

        for (int i = 0; i < adsIdList.size(); ++i) {
            sb.append("(").append(adsIdList.get(i)).append(")");
            if (i < adsIdList.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }


}
