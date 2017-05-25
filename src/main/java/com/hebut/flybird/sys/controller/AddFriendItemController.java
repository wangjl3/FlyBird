package com.hebut.flybird.sys.controller;

import com.hebut.flybird.sys.entity.AddFriendItem;
import com.hebut.flybird.sys.entity.User;
import com.hebut.flybird.sys.service.AddFriendItemService;
import com.hebut.flybird.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by WangJL on 2017/5/7.
 */
@RestController
@RequestMapping(value = SysController.REQUEST_MAPPING_PREFIX+"addfrienditem")
public class AddFriendItemController {
    @Autowired
    AddFriendItemService addFriendItemService;
    @RequestMapping(value = "/new",method = RequestMethod.POST)
    private R addNewFriend(String strangeAccount, HttpServletRequest request){
        // TODO: 2017/5/7 后端校验

        String account = (String) request.getSession().getAttribute("account");
        if(account!=null){
            AddFriendItem addFriendItem = new AddFriendItem();
            addFriendItem.setFromAcc(account);
            addFriendItem.setToAcc(strangeAccount);
            if(!addFriendItemService.exists(addFriendItem)){
                addFriendItemService.save(addFriendItem);
                return R.ok();
            }
            return R.error("好友申请已经发送");
        }
        return  R.error();
    }
    @RequestMapping(value = "/requestFromOthers",method = RequestMethod.GET)
    private R friendRequest(String strangeAccount, HttpServletRequest request){
        // TODO: 2017/5/7 后端校验
        String account = (String) request.getSession().getAttribute("account");
        if(account!=null){
            List<User> users = addFriendItemService.findByToAcc(account);
            return R.ok().put("strangeUsers",users);
        }
        return R.error();
    }

}
