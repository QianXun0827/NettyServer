package com.pm.cms.chat.ws.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: derekhehe@yahoo.com
 * @Date: Created in 2018/8/21 19:37
 * @Description:
 * @Modified By:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginMsg {
    private Integer userId;
    private String projectId;
    private int state;
}
