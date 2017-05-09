package com.hebut.flybird.sys.controller;

import com.hebut.flybird.sys.Constants;
import com.hebut.flybird.sys.Global.GlobalFactory;
import com.hebut.flybird.sys.entity.User;
import com.hebut.flybird.sys.service.UserService;
import com.hebut.flybird.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by WangJL on 2017/5/6.
 */
@RestController
@RequestMapping(value = SysController.REQUEST_MAPPING_PREFIX)
public class LoginController {
    @Autowired
    UserService userService;
    //用户登录
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public R doLogin(@RequestBody User user, HttpServletRequest request){

        // TODO: 2017/5/6 后端校验
        if(userService.login(user)){
            request.getSession().setAttribute("account",user.getAccount());
            request.getSession().setAttribute("uplineStatus",Constants.FREE);
            GlobalFactory.getUsersUplineStatusMap().put(user.getAccount(),user.getUplineStatus());
            return R.ok();
        }
        return R.error("用户名或密码错误");
    }
    //退出登录
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public R doLogout(HttpServletRequest request){
        GlobalFactory.getUsersUplineStatusMap().remove(request.getSession().getAttribute("account"));
        request.getSession().invalidate();
        return R.ok("退出登录成功");
    }
}
