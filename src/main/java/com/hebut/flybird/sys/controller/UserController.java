package com.hebut.flybird.sys.controller;

import com.hebut.flybird.sys.entity.User;
import com.hebut.flybird.sys.service.UserService;
import com.hebut.flybird.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by WangJL on 2017/5/5.
 */
@RestController
@RequestMapping(value = SysController.REQUEST_MAPPING_PREFIX+"user")
public class UserController {
    @Autowired
    UserService userService;
    //用户注册
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public R doRegister(@RequestBody User user){
        // TODO: 2017/5/6 后端校验
        if(!userService.save(user)){
            return R.error("用户名已存在");
        }
        return R.ok("注册成功");
    }
    @RequestMapping(value = "/findByAccount",method = RequestMethod.GET)
    public R findByAccount(String strangeAcc){
        User user = userService.queryByAccount(strangeAcc);
        if(user!=null){
            user.setPassword(null);
            user.setSalt(null);
            return R.ok().put("user",user);
        }
        return R.error();
    }
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public R personalInfo(HttpServletRequest request){
        String account = (String) request.getSession().getAttribute("account");
        User user = userService.queryByAccount(account);
            user.setPassword(null);
            user.setSalt(null);
            return R.ok().put("user",user);
    }
}
