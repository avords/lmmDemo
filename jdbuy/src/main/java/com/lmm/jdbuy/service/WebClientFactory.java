package com.lmm.jdbuy.service;

import com.lmm.jdbuy.utils.WebClient;

/**
 * Created by Administrator on 2016/12/9.
 */
public class WebClientFactory {
    private static final ThreadLocal<WebClient> webClients = new ThreadLocal<WebClient>();
    public static WebClient getCurrentWebClient(){
        WebClient webClient = webClients.get();
        if(webClient==null){
            WebClient webC = new WebClient();
            webClients.set(webC);
            return webC;
        }
        return webClient;
    }
}
