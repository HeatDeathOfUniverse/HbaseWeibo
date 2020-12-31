package com.weibo.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: HBase_weibo
 * @description: Json������
 * @author: Re:k
 * @create: 2020-06-27 15:45
 **/
public class JsonUtils {
    private int code;
    private String msg;
    private Map<String, Object> extendInfo = new HashMap<String, Object>();

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getExtendInfo() {
        return extendInfo;
    }

    public void setExtendInfo(Map<String, Object> extendInfo) {
        this.extendInfo = extendInfo;
    }

    public static JsonUtils success() {
        JsonUtils res = new JsonUtils();
        res.setCode(100);
        res.setMsg("�����ɹ���");
        return res;
    }

    public static JsonUtils fail() {
        JsonUtils res = new JsonUtils();
        res.setCode(200);
        res.setMsg("����ʧ�ܣ�");
        return res;
    }

    public JsonUtils addInfo(String key, Object obj) {
        this.extendInfo.put(key, obj);
        return this;
    }


}
