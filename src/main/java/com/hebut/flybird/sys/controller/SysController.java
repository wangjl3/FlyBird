package com.hebut.flybird.sys.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * sys controller
 *
 * Created by yaofeng on 2017/2/26.
 */
public abstract class SysController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    // request mapping 前缀
    protected static final String REQUEST_MAPPING_PREFIX = "/sys/";
}
