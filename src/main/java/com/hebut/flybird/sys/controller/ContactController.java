package com.hebut.flybird.sys.controller;

import com.hebut.flybird.sys.Constants;
import com.hebut.flybird.sys.Global.GlobalFactory;
import com.hebut.flybird.sys.entity.AddFriendItem;
import com.hebut.flybird.sys.entity.Contact;
import com.hebut.flybird.sys.entity.User;
import com.hebut.flybird.sys.service.AddFriendItemService;
import com.hebut.flybird.sys.service.ContactService;
import com.hebut.flybird.sys.service.UserService;
import com.hebut.flybird.util.PageUtils;
import com.hebut.flybird.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by WangJL on 2017/5/7.
 */
@RestController
@RequestMapping(value = SysController.REQUEST_MAPPING_PREFIX+"linkman")
public class ContactController {
    @Autowired
    private ContactService contactService;
    @Autowired
    private UserService userService;
    @Autowired
    private AddFriendItemService addFriendItemService;
    @RequestMapping("/info")
    public R findLinkmans(HttpServletRequest request,String remark){
        String account = (String) request.getSession().getAttribute("account");
        List<User> linkmans = null;
        if(remark == null || remark.equals("")){
            linkmans = userService.queryLinkmansByAccount(account);
        }else {
            linkmans = userService.queryLinkmansByAccountAndRemarkLike(account,remark);
        }
        for (User linkman : linkmans) {
            Integer uplineStatus = GlobalFactory.getUsersUplineStatusMap().get(linkman.getAccount());
            if (uplineStatus == null) {
                linkman.setUplineStatus(Constants.LEAVE); //用户没有登录
            }else {
                linkman.setUplineStatus(uplineStatus);
            }
        }
        return R.ok().put("linkmans",linkmans);
    }
    @RequestMapping("/addLinkman")
    @Transactional
    public R addLinkman(HttpServletRequest request,String applyAccount){
        String account = (String) request.getSession().getAttribute("account");
        Contact contact = new Contact();
        contact.setAccount(account);
        contact.setLinkmanAccount(applyAccount);
        contactService.save(contact);
        return R.ok();
    }
}



