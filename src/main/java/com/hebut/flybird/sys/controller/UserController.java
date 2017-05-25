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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

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
    public R findByStrangeAccount(String strangeAcc,HttpServletRequest request){
        String  account = (String) request.getSession().getAttribute("account");
        User user = userService.queryByStrangeAccount(account,strangeAcc);
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
    @RequestMapping(value = "/updateProfile",method = RequestMethod.POST)
    public R updateProfile(@RequestBody User user,HttpServletRequest request){
        String account = (String) request.getSession().getAttribute("account");
        userService.updateProfile(account,user);
        return  R.ok();
    }
    @RequestMapping(value = "/uploadHeadImage",method = RequestMethod.POST)
    public R uploadHeadImage(HttpServletRequest request) {
        String pic_path = "E:\\IdeaProjects\\FlyBird\\src\\main\\webapp\\resources\\img\\";
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        MultipartFile multipartFile = null;
        for (Map.Entry<String, MultipartFile> set : fileMap.entrySet()) {
            multipartFile = set.getValue();// 文件名
        }
        if(multipartFile !=null){
            String newFileName = null;
            //原始名称
            String originalFilename = multipartFile.getOriginalFilename();
            if(originalFilename !=null && originalFilename.length()>0){
                //存储图片的物理路径
                newFileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
                File newFile = new File(pic_path+newFileName);
                try {
                    multipartFile.transferTo(newFile);
                    return  R.ok().put("headImage","/resources/img/"+newFileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return R.error();
    }

}
