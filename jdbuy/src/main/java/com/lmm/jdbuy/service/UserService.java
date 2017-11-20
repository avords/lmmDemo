package com.lmm.jdbuy.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lmm.jdbuy.model.Constant;
import com.lmm.jdbuy.model.User;
import com.lmm.jdbuy.utils.UnicodeUtils;
import com.lmm.jdbuy.utils.WebClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Administrator on 2016/12/9.
 */
public class UserService {
    
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public List<User> getUses(String fileName) throws Exception {
        List<User> users = new ArrayList<User>();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(inputStream)
        );
        String nameAndPwd = "";
        while ((nameAndPwd=in.readLine())!=null){
            if(StringUtils.isNotBlank(nameAndPwd)){
                String[] temp = nameAndPwd.split("\\s+");
                if(temp.length>1){
                    String userName = temp[0];
                    String pwd = temp[1];
                    User user = new User(userName,pwd);
                    users.add(user);
                }
            }
        }
        return users;
    }
    
    public boolean login(User user){
        Map map = new HashMap();
        map.put(Constant.LOGIN_USER_NAME,user.getUserName());
        map.put(Constant.LOGIN_PASSWORD,user.getPassword());
        try {
            WebClient webClient = WebClientFactory.getCurrentWebClient();
            //初始化登录前cookie
            CloseableHttpResponse response = webClient.get(Constant.LOGIN_PRE_URL);
            String getRes = EntityUtils.toString(response.getEntity());
            Document document = Jsoup.parse(getRes);
            Elements elements = document.select("#formlogin input[type='hidden']");//得到表单隐藏元素
            Iterator<Element> it = elements.iterator();
            while(it.hasNext()){
                Element element = it.next();
                String name = element.attr("name");
                String value = element.val();
                if(StringUtils.isNotBlank(name)){
                    map.put(name,value);
                }
            }
            //解析页面参数
            response = webClient.post(Constant.LOGIN_URL,map);
            //根据返回值判断登录是否成功
            String res = EntityUtils.toString(response.getEntity());
            String decRes = UnicodeUtils.convertUnicode(res);
            decRes = decRes.replaceAll("\\(|\\)","");
            if(StringUtils.isNotBlank(decRes)){
                JSONObject jsonObject = JSON.parseObject(decRes);
                if(StringUtils.isNotBlank(jsonObject.getString("success"))){
                    return true;
                }
                logger.info("登录失败:"+decRes);
                return false;
            }
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return false;
    }

    /**
     * 判断是否需要验证码
     * @return
     * @throws Exception
     */
    public boolean checkAuthcode() throws Exception {
        //判断是否需要验证 返回Json({"verifycode":false})  
        //https://passport.jd.com/uc/showAuthCode?r=0.7007493122946471&version=2015
        String r = new Random().nextDouble()+"";
        WebClient webClient = WebClientFactory.getCurrentWebClient();
        CloseableHttpResponse closeableHttpResponse = webClient.get(String.format(Constant.LOGIN_IS_SHOW_AUTH_CODE,r));
        String html = EntityUtils.toString(closeableHttpResponse.getEntity());
        if (html.toLowerCase().contains("false")) {
            return false;
        }
        else {
            return true;
        }
    }
    public boolean logout(){
        WebClient webClient = WebClientFactory.getCurrentWebClient();
        try {
            CloseableHttpResponse closeableHttpResponse = webClient.get(Constant.LOGOUT_URL);
            int code = closeableHttpResponse.getStatusLine().getStatusCode();
            if(code==200){
                return true;
            }
        } catch (Exception e) {
            logger.error("退出京东失败");
        }
        return false;
    }
}
