package com.lmm.httpclient;

import org.apache.http.client.methods.CloseableHttpResponse;

/**
 * Created by Administrator on 2016/12/8.
 */
public class DownLoadDemo {
    public static void main(String[] args) throws Exception {
        WebClient webClient = new WebClient();
        CloseableHttpResponse response = webClient.downLoad("http://g.hiphotos.baidu.com/exp/w=500/sign=9181472f7f1ed21b79c92ee59d6fddae/aec379310a55b319fdd154a841a98226cefc17c6.jpg",
                "/Users/xmly/mypic.jpg");
        if(response.getStatusLine().getStatusCode()==200){
            System.out.println("下载成功");
        }
    }
}
