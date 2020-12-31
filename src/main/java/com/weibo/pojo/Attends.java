package com.weibo.pojo;

/**
 * @program: HBase_weibo
 * @description: ToDo
 * @author: Re:k
 * @create: 2020-06-28 23:04
 **/
public class Attends {
    private String rowKey;
    private String attends;

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
}
