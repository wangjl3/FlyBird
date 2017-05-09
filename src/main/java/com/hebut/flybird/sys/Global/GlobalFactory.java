package com.hebut.flybird.sys.Global;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by WangJL on 2017/5/8.
 */
public abstract class GlobalFactory {
    private  static  Map<String,Integer> usersUplineStatusMap = new HashMap<String, Integer>();

    public static Map<String, Integer> getUsersUplineStatusMap() {
        return usersUplineStatusMap;
    }
}
