package com.lmm.jdbuy.model;

/**
 * Created by Administrator on 2016/12/9.
 */
public class Constant {
    public static final String LOGIN_PRE_URL="https://passport.jd.com/uc/login";//登录前访问地址，增加必须的cookie
    public static final String LOGIN_URL="https://passport.jd.com/uc/loginService";//登录网关
    public static final String LOGIN_USER_NAME="loginname";//账户名字段
    public static final String LOGIN_PASSWORD="nloginpwd";//密码字段
    public static final String LOGIN_IS_SHOW_AUTH_CODE = "https://passport.jd.com/uc/showAuthCode?r=%s&version=2015";
    public static final String LOGOUT_URL = "https://passport.jd.com/uc/login?ltype=logout";
    //一键购下单地址
    public static final String EASY_BUY_ORDER_URL = "https://cd.jd.com/easybuy?callback=jQuery9583998&_=1481270834985";
}
