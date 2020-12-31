import com.weibo.dao.HBaseDao;
import com.weibo.utils.HBaseUtil;
import org.junit.Test;

import java.io.IOException;

/**
 * @program: HBase_weibo
 * @description: ToDo
 * @author: Re:k
 * @create: 2020-06-27 12:39
 **/
public class MyTest {
    @Test
    public void testTableExist() throws IOException {
        HBaseUtil hBaseUtil = new HBaseUtil();

        System.out.println(hBaseUtil.isTableExist("weibo:relation"));
    }

    @Test
    public void insertAttends() throws IOException {
        HBaseDao hBaseDao = new HBaseDao();
        String attend = "";
        hBaseDao.addAttends("1_002", "1_001");
        //hBaseDao.deleteAttends("1_001","1000","1_00","1_001");

        for (int i = 2; i <= 999; i++) {
            //System.out.println(i);
            if (i <= 9) {
                attend = "2_00" + i;
                //System.out.println(attend);
                hBaseDao.addAttends("2_001", attend);
            }
            if (i <= 99 && i >= 11) {
                attend = "2_0" + i;
                //System.out.println(attend);
                hBaseDao.addAttends("2_010", attend);
            }
            if (i >= 101 && i <= 999) {
                attend = "2_" + i;
                //System.out.println(attend);
                hBaseDao.addAttends("2_100", attend);
            }

        }
        //��ӡ������
        hBaseDao.scanAttendsListByRk("1_001");
    }

    @Test
    public void isAB() throws IOException {
        HBaseDao hBaseDao = new HBaseDao();
        boolean e = false;
        e = hBaseDao.isApAttendBp("1_001", "1_002");
        if (e) {
            System.out.println("��ע��");
        }
        if (!e) {
            System.out.println("û��ע");
        }
    }


}
