package com.hebut.flybird.sys.controller;

import com.hebut.flybird.sys.entity.AddFriendItem;
import com.hebut.flybird.sys.service.AddFriendItemService;
import com.hebut.flybird.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
            addFriendItemService.save(addFriendItem);
            return R.ok();
        }
        return  R.error();
    }

}
