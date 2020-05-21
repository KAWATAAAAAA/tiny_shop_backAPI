package cn.wuyuwei.tiny_shop.controller;

import cn.wuyuwei.tiny_shop.common.ApiResultEnum;
import cn.wuyuwei.tiny_shop.common.Result;
import cn.wuyuwei.tiny_shop.entity.UserInfo;
import cn.wuyuwei.tiny_shop.service.serviceImple.UserServiceImple;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wuyuwei
 */
@RestController
@Api(tags = "User to User Actions Controller")
@RequestMapping(value="/link")
public class FriendController {

    @Autowired
    UserServiceImple userServiceImple;

    @GetMapping("/user-list")
    @ApiOperation(value = "查询用户列表",notes = "LoginRequired")
    public Result getAllUserLink(){

        try {
            List<UserInfo> list = userServiceImple.doSelectUserList();
            return Result.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ApiResultEnum.ERROR);
        }

    }
}
