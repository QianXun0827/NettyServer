package com.pm.cms.mapper;

import com.pm.cms.pojo.PmUser;
import com.pm.common.service.PmMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Auther: derekhehe@yahoo.com
 * @Date: Created in 2018/8/22 09:58
 * @Description:
 * @Modified By:
 */
public interface PmUserMapper extends PmMapper<PmUser> {

    @Select("select count(1) from pm_user where user_id = #{userId}")
    int auth(@Param("userId") int userId);

    @Select("select count(1) from pm_user where user_name = #{userName}")
    int getUserCount(@Param("userName") String userName);

    @Select("select user_name from pm_user where user_id = #{userId}")
    String getUserName(@Param("userId") int userId);
}
