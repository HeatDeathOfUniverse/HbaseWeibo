package com.weibo.dao;

import com.weibo.pojo.Attends;
import com.weibo.pojo.Fans;
import com.weibo.utils.Constants;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: HBase_weibo
 * @description: ToDo
 * @author: Re:k
 * @create: 2020-06-27 10:34
 **/
public class HBaseDao {
    /**
     * @param uid
     * @param attends
     * @return void
     * @author: Re:k
     * @description: ��ӹ�ע
     * @date: 2020/6/27 12:49
     */
    public static void addAttends(String uid, String... attends) throws IOException {
        //У���Ƿ�����˹�ע��
        if (attends.length <= 0) {
            System.out.println("��ѡ��Ҫ��ע����!!!");
            return;
        }
        //��ȡ����
        Connection connection = ConnectionFactory.createConnection(Constants.CONFIGURATION);
        //�����û���ϵ��
        //1.��ȡ�û���ϵ��
        Table relaTable = connection.getTable(TableName.valueOf(Constants.RELATION_TABLE));
        //2.����һ���������ڴ���û���ϵ��put����
        ArrayList<Put> relaPuts = new ArrayList<Put>();
        //3.���������ߵ�put����
        Put uidPut = new Put(Bytes.toBytes(uid));
        //4.ѭ����������ע�ߵ�put����
        for (String attend : attends) {
            //5.�������ߵ�Put����ֵ
            uidPut.addColumn(Bytes.toBytes(Constants.RELATION_TABLE_CF1), Bytes.toBytes(attend), Bytes.toBytes(attend));
            //6.��������ע�ߵ�put ����
            Put attendPut = new Put(Bytes.toBytes(attend));
            //7.������ע�ŵ�put ����ֵ
            attendPut.addColumn(Bytes.toBytes(Constants.RELATION_TABLE_CF2), Bytes.toBytes(uid), Bytes.toBytes(uid));
            //8.����ע�ߵ�put������뼯��
            relaPuts.add(attendPut);
        }
        //9.�������ߵ�put�������������
        relaPuts.add(uidPut);
        //10.ִ���û���ϵ��Ĳ������ݲ���
        relaTable.put(relaPuts);
        //�ر���Դ
        relaTable.close();
        connection.close();
    }

    /**
     * @param uid
     * @param dels
     * @return void
     * @author: Re:k
     * @description: ȡ���û�
     * @date: 2020/6/27 14:51
     */
    public static void deleteAttends(String uid, String... dels) throws IOException {
        if (dels.length <= 0) {
            System.out.println("����Ӵ�ȡ���û�!!!");
            return;
        }

        //��ȡConnection����
        Connection connection = ConnectionFactory.createConnection(Constants.CONFIGURATION);

        //1.��ȡ�û���ϵ�����
        Table relaTable = connection.getTable(TableName.valueOf(Constants.RELATION_TABLE));
        //2.����һ������,���ڴ��delete����
        ArrayList<Delete> relaDeletes = new ArrayList<Delete>();
        //3.���������ߵ�delete����
        Delete uidDelete = new Delete(Bytes.toBytes(uid));
        //4.ѭ����ȡ���ߵ�delete����
        for (String del : dels) {
            //5.�������ߵ�Delete����ֵ
            uidDelete.addColumns(Bytes.toBytes(Constants.RELATION_TABLE_CF1), Bytes.toBytes(del));
            //6.������ȡ���ߵ�Delete����
            Delete delDelete = new Delete(Bytes.toBytes(del));
            //7.����ȡ���ߵ�Delete����ֵ
            delDelete.addColumns(Bytes.toBytes(Constants.RELATION_TABLE_CF2), Bytes.toBytes(uid));
            //8.����ȡ���ߵ�Delete�������������
            relaDeletes.add(delDelete);
        }
        //9.��������Delete������ӵ�����
        relaDeletes.add(uidDelete);
        //10.ִ���û���ϵ���ɾ������
        relaTable.delete(relaDeletes);
        //�ر���Դ
        relaTable.close();
        connection.close();
    }

    /**
     * @param rowKey
     * @return java.util.List<com.weibo.pojo.Attends>
     * @author: Re:k
     * @description: ��ȡ��ע���б�
     * @date: 2020/6/29 22:25
     */
    public List<Attends> scanAttendsListByRk(String rowKey) throws IOException {

        //1.��ȡConnection����
        Connection connection = ConnectionFactory.createConnection(Constants.CONFIGURATION);
        //2.��ȡ�û���ϵ�����
        Table relaTable = connection.getTable(TableName.valueOf(Constants.RELATION_TABLE));
        //3.��������һ������,���ڴ�Ż�ȡ��relation����
        List<Attends> attendsList = new ArrayList<Attends>();
        //4.��ʼ��Get����
        Get get = new Get(Bytes.toBytes(rowKey));
        get.addFamily(Bytes.toBytes(Constants.RELATION_TABLE_CF1));
        //5.��ȡ����
        Result result = relaTable.get(get);
        //6.����result
        for (Cell cell : result.rawCells()) {
            Attends attends = new Attends();
            attends.setRowKey(Bytes.toString(CellUtil.cloneRow(cell)));
            if (Bytes.toString(CellUtil.cloneFamily(cell)).equals("attends") && Bytes.toString(CellUtil.cloneValue(cell)) != null) {
                attends.setAttends(Bytes.toString(CellUtil.cloneValue(cell)));
            }

            attendsList.add(attends);
        }
        //�ر���Դ
        relaTable.close();
        connection.close();
        return attendsList;
    }

    /**
     * @param rowKey
     * @return java.util.List<com.weibo.pojo.Fans>
     * @author: Re:k
     * @description: ��ȡ��˿�б�
     * @date: 2020/6/29 22:26
     */
    public List<Fans> scanFansListByRk(String rowKey) throws IOException {

        //1.��ȡConnection����
        Connection connection = ConnectionFactory.createConnection(Constants.CONFIGURATION);
        //2.��ȡ�û���ϵ�����
        Table relaTable = connection.getTable(TableName.valueOf(Constants.RELATION_TABLE));
        //3.��������һ������,���ڴ�Ż�ȡ��relation����
        List<Fans> fansList = new ArrayList<Fans>();
        //4.��ʼ��Get����
        Get get = new Get(Bytes.toBytes(rowKey));
        get.addFamily(Bytes.toBytes(Constants.RELATION_TABLE_CF2));
        //5.��ȡ����
        Result result = relaTable.get(get);
        //6.����result
        for (Cell cell : result.rawCells()) {
            Fans fans = new Fans();
            fans.setRowKey(Bytes.toString(CellUtil.cloneRow(cell)));
            if (Bytes.toString(CellUtil.cloneFamily(cell)).equals("fans") && !Bytes.toString(CellUtil.cloneValue(cell)).equals(null)) {
                fans.setFans(Bytes.toString(CellUtil.cloneValue(cell)));
            }
            fansList.add(fans);
        }
        //��ӡ��ȡ������
        //�ر���Դ
        relaTable.close();
        connection.close();
        return fansList;
    }

    /**
     * @param aid
     * @param bid
     * @return boolean
     * @author: Re:k
     * @description: A��ע��B?
     * @date: 2020/6/30 13:47
     */
    public boolean isApAttendBp(String aid, String bid) throws IOException {
        System.out.println("��ȡ��idΪ:" + aid + "," + bid);
        //1.��ȡConnection����
        Connection connection = ConnectionFactory.createConnection(Constants.CONFIGURATION);
        //2.��ȡ�û���ϵ�����
        Table relaTable = connection.getTable(TableName.valueOf(Constants.RELATION_TABLE));
        //3.��������һ������,���ڴ�Ž��
        boolean e = false;
        //4.��ʼ��Get����
        Get get = new Get(Bytes.toBytes(aid));
        get.addFamily(Bytes.toBytes(Constants.RELATION_TABLE_CF1));
        //5.��ȡ����
        Result result = relaTable.get(get);
        //6.����result
        for (Cell cell : result.rawCells()) {
            //System.out.println(cell);
            System.out.println("��ǰֵΪ:" + Bytes.toString(CellUtil.cloneValue(cell)));
            Attends attends = new Attends();
            System.out.println();
            if (Bytes.toString(CellUtil.cloneValue(cell)).equals(bid)) {
                e = true;
                break;
            }
            System.out.println("�жϵĽ��Ϊ" + e);

        }
        System.out.println("���յĽ��Ϊ" + e);
        return e;
    }

}
