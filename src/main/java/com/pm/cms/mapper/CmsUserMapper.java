package com.pm.cms.mapper;

import com.pm.cms.pojo.PmUser;
import org.apache.ibatis.annotations.Select;

/**
 * 功能描述：
 * 【用户mapper】
 *
 * @author huhaiqiang
 * @version V1.0
 * @date 2019/02/25 11:20
 */
public interface CmsUserMapper {

    /**
     * 根据用户名查找用户信息
     *
     * @param userName 用户名
     * @return PmUser实体
     */
    @Select("SELECT user_id,user_name,user_password,parent_id,user_role,user_role_type FROM pm_user WHERE user_name = #{userName}")
    PmUser userLogin(String userName);
}
