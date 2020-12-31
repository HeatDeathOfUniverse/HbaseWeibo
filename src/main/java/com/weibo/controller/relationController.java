package com.weibo.controller;

import com.weibo.dao.HBaseDao;
import com.weibo.pojo.Attends;
import com.weibo.pojo.Fans;
import com.weibo.pojo.Relation;
import com.weibo.utils.JsonUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @program: HBase_weibo
 * @description: �û���ϵ������
 * @author: Re:k
 * @create: 2020-06-26 21:39
 **/
@Controller
public class relationController {
    @RequestMapping("/login")
    @ResponseBody
    public JsonUtils login(String uid, HttpSession session) {
        session.setAttribute("uid", uid);
        System.out.println(session.getAttribute("uid"));
        return JsonUtils.success();
    }

    @RequestMapping("/myAttends")
    @ResponseBody
    public String myAttends(HttpSession session) throws IOException {
        HBaseDao hBaseDao = new HBaseDao();
        //����jackson����ӳ����,��������
        ObjectMapper objectMapper = new ObjectMapper();
        //��ȡ��ǰ�û�id
        String uid = (String) session.getAttribute("uid");
        System.out.println("myAttends��ȡ�����û�idΪ:" + uid);
        List<Attends> attendsList = hBaseDao.scanAttendsListByRk(uid);
        String str = objectMapper.writeValueAsString(attendsList);
        return str;
    }

    @RequestMapping("/myFans")
    @ResponseBody
    public String myFans(HttpSession session) throws IOException {
        HBaseDao hBaseDao = new HBaseDao();
        //����jackson����ӳ����,��������
        ObjectMapper objectMapper = new ObjectMapper();
        //��ȡ��ǰ�û�id
        String uid = (String) session.getAttribute("uid");
        System.out.println("myFans��ȡ�����û�idΪ:" + uid);
        List<Fans> fansList = hBaseDao.scanFansListByRk(uid);
        String str = objectMapper.writeValueAsString(fansList);
        return str;
    }

    @RequestMapping("/attendPerson")
    @ResponseBody
    public String attendPerson(HttpSession session, String attendId) throws IOException {
        HBaseDao hBaseDao = new HBaseDao();
        //����jackson����ӳ����,��������
        ObjectMapper objectMapper = new ObjectMapper();
        //��ȡ��ǰ�û�id
        String uid = (String) session.getAttribute("uid");
        System.out.println("attendPerson��ȡ�����û�idΪ:" + uid);
        hBaseDao.addAttends(uid, attendId);
        return "success";
    }

    @RequestMapping("/unAttendPerson")
    @ResponseBody
    public String unAttendPerson(HttpSession session, String unAttendId) throws IOException {
        HBaseDao hBaseDao = new HBaseDao();
        //����jackson����ӳ����,��������
        ObjectMapper objectMapper = new ObjectMapper();
        //��ȡ��ǰ�û�id
        String uid = (String) session.getAttribute("uid");
        System.out.println("attendPerson��ȡ�����û�idΪ:" + uid);
        hBaseDao.deleteAttends(uid, unAttendId);
        return "success";
    }

    @RequestMapping("/isApAttendBp")
    @ResponseBody
    public String isApAttendBp(String aid, String bid) throws IOException {
        System.out.println("isApAttendBp��ȡ�����û�idΪ:" + aid + bid);
        HBaseDao hBaseDao = new HBaseDao();
        ObjectMapper objectMapper = new ObjectMapper();
        String msg = "";
        boolean e = hBaseDao.isApAttendBp(aid, bid);
        if (e) {
            msg = "yes!";
        }
        if (!e) {
            msg = "no!";
        }
        System.out.println("����ֵΪ:" + msg);
        msg = objectMapper.writeValueAsString(msg);
        return msg;
    }

    @RequestMapping("/index")
    public String toIndex() {
        return "index";
    }
}
