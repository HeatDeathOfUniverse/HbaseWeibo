package com.weibo.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

/**
 * @program: HBase_weibo
 * @description: ToDo
 * @author: Re:k
 * @create: 2020-06-26 23:58
 **/
public class Constants {
    public static final Configuration CONFIGURATION = HBaseConfiguration.create();
    //�û���ϵ��
    public static final String RELATION_TABLE = "weibo:relation";
    public static final String RELATION_TABLE_CF1 = "attends";
    public static final String RELATION_TABLE_CF2 = "fans";

}
