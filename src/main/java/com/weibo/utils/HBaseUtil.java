package com.weibo.utils;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;

/**
 * @program: HBase_weibo
 * @description: ���ù�����
 * @author: Re:k
 * @create: 2020-06-26 21:44
 **/
public class HBaseUtil {
    /**
     * @param tableName
     * @return boolean
     * @author: Re:k
     * @description: �жϱ��Ƿ����
     * @date: 2020/6/27 0:07
     */
    public static boolean isTableExist(String tableName) throws IOException {
        //��ȡConnection����
        Connection connection = ConnectionFactory.createConnection(Constants.CONFIGURATION);

        //��ȡAdmin����
        Admin admin = connection.getAdmin();
        //�жϱ��Ƿ����
        boolean exists = admin.tableExists(TableName.valueOf(tableName));
        //�ر���Դ;

        admin.close();
        connection.close();

        //���ؽ��
        return exists;
    }

    /**
     * @param tableName
     * @param versions
     * @param cfs
     * @return void
     * @author: Re:k
     * @description: ������
     * @date: 2020/6/27 12:48
     */
    public static void createTable(String tableName, int versions, String... cfs) throws IOException {

        //�ж��Ƿ�����������Ϣ
        if (cfs.length <= 0) {
            System.out.println("����������!!!");
            return;
        }
        //�жϱ��Ƿ����
        if (isTableExist(tableName)) {
            System.out.println("���Ѵ���!!!");
            return;
        }
        //��ȡConnection����
        Connection connection = ConnectionFactory.createConnection(Constants.CONFIGURATION);
        //��ȡAdmin����
        Admin admin = connection.getAdmin();
        //������������
        HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
        //ѭ�����������Ϣ
        for (String cf : cfs) {
            HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(cf);
            //���ð汾
            hColumnDescriptor.setMaxVersions(versions);
            hTableDescriptor.addFamily(hColumnDescriptor);
        }

        //���������
        admin.createTable(hTableDescriptor);
        //�ر���Դ
        admin.close();
        connection.close();
    }

}
