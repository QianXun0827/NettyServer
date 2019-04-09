package com.pm.cms.common.provier;

import cn.hutool.core.util.StrUtil;
import com.pm.cms.dto.SelectFodderManageDto;

import java.util.List;
import java.util.Map;

import static com.pm.cms.common.constant.InterceptorConstant.*;

/**
 * cms模块动态sql
 *
 * @author huhaiqiang
 * @date 2018/08/08 15:36
 */
public class CmsSelectProvier {

    public String selectFodderByDto(Map map) {
        Integer type = (Integer) map.get("type");
        String fodderName = (String) map.get("fodderName");

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT fodder_id,fodder_name,fodder_type,fodder_context,file_size,file_time,file_name,audit_status,remark,is_delete,create_time,update_time FROM cms_fodder WHERE is_delete = 1 ");
        if (type != null && type != 0) {
            sb.append(" AND fodder_type = ").append(type);
        }
        if (StrUtil.isNotBlank(fodderName) && !(INIT_FODDER_NAME.equals(fodderName))) {
            sb.append(" AND fodder_name LIKE '%").append(fodderName).append("%'");
        }
        return sb.toString();
    }

    public String selectPublishTypeByIccid(Map map) {
        List<Integer> iccidList = (List) map.get("list");

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT cf.fodder_id,cf.fodder_type,csf.iccid,csf.fodder_area,csf.shelter_monitor FROM cms_fodder AS cf , cms_shelter_fodder AS csf ");
        sb.append("WHERE cf.fodder_id = csf.fodder_id AND csf.iccid IN ( ");

        for (int i = 0; i < iccidList.size(); ++i) {
            sb.append("'").append(iccidList.get(i)).append("'");
            if (i < iccidList.size() - 1) {
                sb.append(",");
            }
        }
        sb.append(" )");
        return sb.toString();
    }

    public String selectShelterInfo(Map map) {
        Integer subareaNum = (Integer) map.get("subareaNum");

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT sh.iccid,sh.shelter_name,sh.shelter_direction FROM pm_shelter sh " +
                "LEFT JOIN pm_project_shelter pps ON sh.iccid = pps.iccid " +
                "LEFT JOIN cms_ads_setting cas ON cas.iccid = sh.iccid ");
        if (subareaNum != null && !(INIT_SUBAREA_NUM.equals(subareaNum)) ) {
            sb.append(" WHERE cas.subarea_num = ").append(subareaNum);
        }
        return sb.toString();
    }

    public String selectFodderByIccid(Map map) {
        SelectFodderManageDto selectFodderManageDto = (SelectFodderManageDto) map.get("selectFodderManageDto");

        String iccid = selectFodderManageDto.getIccid();
        Integer updateState = selectFodderManageDto.getUpdateState();
        String fodderName = selectFodderManageDto.getFodderName();
        Integer fodderType = selectFodderManageDto.getType();

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ps.iccid,ps.shelter_name,ps.shelter_direction,cf.fodder_name,cf.fodder_context,cf.fodder_id,cf.fodder_type,csf.shelter_fodder_id,csf.fodder_area,csf.is_voice,csf.update_state,csf.shelter_monitor,csf.fodder_begin_time,csf.fodder_end_time,csf.remark,csf.create_time,csf.update_time FROM pm_shelter ps " +
                "INNER JOIN cms_shelter_fodder csf ON  csf.iccid = ps.iccid " +
                "INNER JOIN cms_fodder cf ON csf.fodder_id = cf.fodder_id WHERE 1 = 1 ");

        if (StrUtil.isNotBlank(iccid) && !(INIT_ICCID.equals(iccid))) {
            sb.append(" AND csf.iccid = '").append(iccid).append("'");
        }
        if (StrUtil.isNotBlank(fodderName) && !(INIT_FODDER_NAME.equals(fodderName))) {
            sb.append(" AND cf.fodder_name LIKE '%").append(fodderName).append("%'");
        }
        if (fodderType != null && !(INIT_FODDER_TYPE.equals(fodderType))) {
            sb.append(" AND cf.fodder_type = ").append(fodderType);
        }
        if (updateState != null && !(INIT_SHELTER_FODDER_STASUS.equals(updateState))) {
            sb.append(" AND csf.update_state =").append(updateState);
        }

        return sb.toString();
    }

}
