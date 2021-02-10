package com.design.elecworkshopsys.controller;

import com.design.elecworkshopsys.bean.User;
import com.design.elecworkshopsys.service.UserService;
import com.design.elecworkshopsys.utils.PageInfo;
import com.design.elecworkshopsys.utils.ReturnDatas;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/user")
    public ReturnDatas listPojo(PageInfo<?> pageInfo, User user) {
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        List<User> userList = userService.listUser(user);
        return ReturnDatas.success().data(userList).pageInfo(new PageInfo<User>(userList));
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ReturnDatas getPojo(@PathVariable Integer id) {
        return ReturnDatas.success().data(userService.getUserById(id));
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ReturnDatas login(User user, HttpSession session) {
        if (StringUtils.isBlank(user.getAccount()) || StringUtils.isBlank(user.getPassword())) {
            return ReturnDatas.error().message("请输入账号密码");
        }
        User userInfo = userService.login(user);
        if (null == userInfo) {
            return ReturnDatas.error().message("账号或密码错误");
        }
        session.setAttribute("user", userInfo);
        return ReturnDatas.success().data(userInfo).message("账号或密码错误");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ReturnDatas logout(HttpSession session) {
        session.invalidate();
        return ReturnDatas.success().message("退出成功");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ReturnDatas insertUser(User user) {
        if (userService.countUser(user) != 0) {
            return ReturnDatas.error().message("账号已被注册");
        }
        userService.insertUser(user);
        return ReturnDatas.success().message("注册成功");
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public ReturnDatas updateUser(User user, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        userService.updateUser(user);
        if (currentUser != null && currentUser.getType() != 0) {
            session.setAttribute("user", userService.getUserById(user.getId()));
        }
        return ReturnDatas.success().message("更新成功");
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ReturnDatas deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ReturnDatas.success().message("删除成功");
    }

    @GetMapping("/user/count")
    public ReturnDatas getPojoCount(){
        return ReturnDatas.success().data(userService.countUser());
    }

}
