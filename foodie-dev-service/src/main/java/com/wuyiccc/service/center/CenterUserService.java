package com.wuyiccc.service.center;

import com.wuyiccc.pojo.Users;
import com.wuyiccc.pojo.bo.center.CenterUserBO;

/**
 * @author wuyiccc
 * @date 2020/1/17 9:24
 * 岂曰无衣，与子同袍~
 */
public interface CenterUserService {

    /**
     * 根据用户名查询用户信息---用户中心
     * @param userId
     * @return
     */
    public Users queryUserInfo(String userId);

    /**
     * 更新用户信息
     * @param userId
     * @param centerUserBO
     * @return
     */
    public Users updateUserInfo(String userId, CenterUserBO centerUserBO);


}
