package com.lmm.jdbuy;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by Administrator on 2016/12/9.
 */
public class Test {
    public static void main(String[] args) throws Exception {
        /*UserService userService = new UserService();
        User user = new User();
        user.setUserName("17717370595");
        user.setPassword("cc123456");
        System.out.println(userService.checkAuthcode()?"需要验证码":"不需要验证码");
        boolean flag = userService.login(user);
        System.out.println(flag?"登录成功":"登录失败");
        WebClient webClient = WebClientFactory.getCurrentWebClient();
        CloseableHttpResponse response = webClient.get("https://order.jd.com/center/list.action?search=0&d=2015&s=4096");
        userService.logout();
        webClient.printResponse(response);*/
        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse("2017-03-12");
        System.out.println(date);*/
        
        Integer[] a = {56,12,89,76,11};
        Arrays.sort(a,new Comparator<Integer>(){

            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        });
        System.out.println(Arrays.toString(a));
    }
}
