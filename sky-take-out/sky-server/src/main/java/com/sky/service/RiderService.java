package com.sky.service;

import com.sky.dto.RiderPasswordLoginDTO;
import com.sky.dto.RiderWeixinLoginDTO;
import com.sky.vo.RiderLoginVO;

/**
 * @author 帅的被人砍
 * @create 2024-09-20 14:09
 */
public interface RiderService {

    /**
     * 骑手密码登录
     * @param riderPasswordLoginDTO
     */
    RiderLoginVO riderPasswordLogin(RiderPasswordLoginDTO riderPasswordLoginDTO);

    /**
     * 骑手微信登录
     * @param riderWeixinLoginDTO
     * @return
     */
    RiderLoginVO riderWeixinLogin(RiderWeixinLoginDTO riderWeixinLoginDTO);
}
