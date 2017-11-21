package io.candice.config.model;

import io.candice.route.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-09-18
 */
public class RealTableCache {

    //k up value realTab
    public static final Map<String, String> map = new HashMap<String, String>();

    /**
     *
     *
     * @param rTableName
     */
    public static void put(String rTableName) {
        if (StringUtil.isNotEmpty(rTableName)) {
            map.put(rTableName.toUpperCase(), rTableName);
        }

    }

    /**
     *
     * @param tbUp
     * @return
     */
    public static String getRealByUp(String tbUp) {
        String s = map.get(tbUp);
        if (StringUtil.isEmpty(s)) {
            return tbUp;
        } else {
            return s;
        }
    }

}
