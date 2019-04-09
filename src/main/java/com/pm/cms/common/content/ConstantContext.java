package com.pm.cms.common.content;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 功能描述：
 * 【百度审核常量上下文】
 *
 * @author huhaiqiang
 * @version V1.0
 * @date 2019/02/22 09:15
 */
public class ConstantContext {

    /**
     * 审核通过
     */
    public static final String NORMAL_RESULT = "NORMAL";
    /**
     * 审核不通过
     */
    public static final String REJECT_RESULT = "REJECT";
    /**
     * 待复核
     */
    public static final String REVIEW_RESULT = "REVIEW";

    /**
     * 百度AI审核类型
     */
    public static final Map<String, String> TYPE_MAP = new ConcurrentHashMap<>();
    static {
        TYPE_MAP.put("sexual_porn", "涉黄");
        TYPE_MAP.put("sexual_sexy", "涉黄");
        TYPE_MAP.put("sexual_intimacy", "涉黄");
        TYPE_MAP.put("sexual_vulgar", "涉黄");
        TYPE_MAP.put("terrorist_group", "暴恐");
        TYPE_MAP.put("terrorist", "暴恐");
        TYPE_MAP.put("terror_event", "暴恐");
        TYPE_MAP.put("politician", "涉政");
        TYPE_MAP.put("political_event", "涉政");
        TYPE_MAP.put("political_group", "涉政");
        TYPE_MAP.put("ad_brand", "带广告");
        TYPE_MAP.put("ad_marketing", "带广告");
        TYPE_MAP.put("illegal_gamble", "违禁");
        TYPE_MAP.put("illegal_forgery", "违禁");
        TYPE_MAP.put("illegal_trade", "违禁");
        TYPE_MAP.put("illegal_privacy", "违禁");
    }

    /**
     * 百度审核模版编号
     */
    public static final Map<Integer, String> PRESET_NUM = new ConcurrentHashMap<>();
    static {
        PRESET_NUM.put(0,"pm_default");
        PRESET_NUM.put(1,"pm_default_politics");
        PRESET_NUM.put(2,"pm_default_advertising");
        PRESET_NUM.put(3,"default");
    }


    /**
     * 站牌素材状态异常Map
     */
    public static final Map<Integer, String> UPDATE_STATE_MAP = new ConcurrentHashMap<>();
    static {
        UPDATE_STATE_MAP.put(0,"发布中素材不允许删除");
        UPDATE_STATE_MAP.put(1,"发布成功素材只允许下架");
        UPDATE_STATE_MAP.put(3,"下架中素材不允许删除");
    }


}
