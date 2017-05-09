package com.hebut.flybird.sys.controller;

import com.hebut.flybird.websocket.SysWebSocketHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by WangJL on 2017/5/9.
 */
@RestController
@RequestMapping(value = SysController.REQUEST_MAPPING_PREFIX+"chat")
public class ChatController {
    @Resource
    SysWebSocketHandler handler;

}
