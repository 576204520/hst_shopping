package com.cj.reocrd.api;

/**
 * Created by Lyndon.Li on 2018/3/17.
 */

public class UrlConstants {
    //BaseURl
//    public final static String BASE_URL = "http://gank.io/api/";
    public final static String BASE_URL = "http://mall.xdiandian.cn/mall/";
    public final static String URL = "app/outinterface";
    public final static String PID = "123222212121";  //  TODO  pid
    public final static String SUCCESE_CODE = "1"; // 返回结果成功

    public static class key {
        public static final String USERID = "userid";
        public static final String PID = "pid";
    }

    public static class codeType {
        //1注册2登录 3修改密码 4修改手机号（如果是注册，该接口判断手机号是否存在）
        public static final String REGISTER = "1";
        public static final String LOGIN = "2";
        public static final String UPDATE_PWD = "3";
        public static final String UPDATE_PHONE = "4";
    }

    public static class UrLType {
        // 发送验证码
        public static final String GET_CODE = "101";
        // 登陆-密码
        public static final String LOGIN_PWD = "102";
        // 登陆-验证码
        public static final String LOGIN_CODE = "103";
        // 注册
        public static final String REGISTER = "104";
        //修改密码
        public static final String UPDATE_PWD = "106";
        //验证推荐号码是否存在
        public static final String CHECK_RECOMMEND = "107";
        //首页
        public static final String GET_HOME_DATA = "201";

        //我的首页
        public static final String MY_HOME = "301";
        //userinfo
        public static final String USERINFO = "302";
        //修改头像
        public static final String UPDATE_PHOTO = "clientupload/updateUserPhoto";
        //修改名字
        public static final String UPDATE_NAME = "304";
        //修改性别
        public static final String UPDATE_SEX = "305";
        //修改手机
        public static final String UPDATE_PHONE = "306";
        //验证原手机号验证码
        public static final String CHECK_PHONE = "307";
        //修改身份证号
        public static final String UPDATE_IC = "308";

        //关注好友
        public static final String FRIEDNS_KEEP = "501";
        //取消关注
        public static final String FRIEDNS_UNKEEP = "502";
        //获取我的好友
        public static final String FRIEDNS_GET = "503";
        //发布圈子
        public static final String FRIEDNS_SEND = "clientupload/addCommunity";
        //删除圈子
        public static final String FRIEDNS_DELETE = "505";
        //圈子列表
        public static final String FRIEDNS_LIST = "506";
        //圈子详情
        public static final String FRIEDNS_DETAIL = "507";
        //圈子评论列表
        public static final String FRIEDNS_MESSAGE = "508";
        //点赞
        public static final String FRIEDNS_LIKE = "509";
        //评论
        public static final String FRIEDNS_COMMENT = "510";
    }

}
