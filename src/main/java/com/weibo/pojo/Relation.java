package com.weibo.pojo;

/**
 * @program: HBase_weibo
 * @description: ToDo
 * @author: Re:k
 * @create: 2020-06-28 11:28
 **/
public class Relation {
    private String rowKey;
    private String attends;
    private String fans;

    public String getRowKey() {
        return rowKey;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    public String getAttends() {
        return attends;
    }

    public void setAttends(String attends) {
        this.attends = attends;
    }

    public String getFans() {
        return fans;
    }

    public void setFans(String fans) {
        this.fans = fans;
    }
}
