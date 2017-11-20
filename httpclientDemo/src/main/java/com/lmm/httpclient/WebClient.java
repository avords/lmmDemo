package com.lmm.httpclient;

/**
 * Created by Administrator on 2016/12/8.
 */

import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 封装httpClient方法，其自带对cookie的管理，只要是同一个http实例
 * @author Rain
 *
 */
public class WebClient {

    private static Logger logger = LoggerFactory.getLogger(WebClient.class);
    private CloseableHttpClient httpclient = HttpClients.createDefault();

    /**
     * post方法访问，默认UTF-8表单编码方式
     * @param url 访问地址
     * @param params 表单参数
     * @return
     * @throws Exception
     */
    public CloseableHttpResponse post(String url,Map<String,String> params) throws Exception {
        return this.post(url,params,"UTF-8");
    }

    /**
     * get方法访问
     * @param url 访问地址
     * @return
     * @throws Exception
     */
    public CloseableHttpResponse get(String url) throws Exception {
        return this.get(url,null);
    }
    /**
     * get方法访问
     * @param url 访问地址
     * @return
     * @throws Exception
     */
    public CloseableHttpResponse get(String url,Map<String ,String> headerParams) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        //增加请求头
        if(headerParams!=null&&headerParams.size()>0) {
            for (String key : headerParams.keySet()) {
                String value = headerParams.get(key);
                httpGet.addHeader(key, value);
            }
        }
        CloseableHttpResponse httpResponse = httpclient.execute(httpGet);

        logger.info("request Line:"+httpGet.getRequestLine());
        return httpResponse;
    }

    /**
     *  post方法访问
     * @param url 访问地址
     * @param params 表单参数
     * @param code 编码
     * @return
     * @throws Exception
     */
    public CloseableHttpResponse post(String url,Map<String,String> params,Map<String,String> headerParams,String code) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        //增加请求头
        if(headerParams!=null&&headerParams.size()>0) {
            for (String key : headerParams.keySet()) {
                String value = headerParams.get(key);
                httpPost.addHeader(key, value);
            }
        }
        UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(
                getParam(params), code);
        httpPost.setEntity(postEntity);

        CloseableHttpResponse httpResponse = httpclient.execute(httpPost);

        logger.info("request Line:"+httpPost.getRequestLine());
        return httpResponse;
    }

    /**
     *  post方法访问
     * @param url 访问地址
     * @param params 表单参数
     * @param code 编码
     * @return
     * @throws Exception
     */
    public CloseableHttpResponse post(String url,Map<String,String> params,String code) throws Exception {
        return this.post(url,params,null,code);
    }

    /**
     * 下载文件
     * @param url 文件路径
     * @param destDic 本地磁盘路径加文件名
     * @throws Exception
     */
    public CloseableHttpResponse downLoad(String url,String destDic) throws Exception {
        HttpGet httpget = new HttpGet(url);
        CloseableHttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        InputStream in = entity.getContent();
        File file = new File(destDic);
        try {
            FileOutputStream fout = new FileOutputStream(file);
            int l = -1;
            byte[] tmp = new byte[1024];
            while ((l = in.read(tmp)) != -1) {
                fout.write(tmp,0,l);
                //注意这里如果用OutputStream.write(buff)的话，图片会失真，大家可以试试
            }
            fout.flush();
            fout.close();
        } finally {
            // 关闭低层流。
            in.close();
        }
        return response;
    }

    /**
     * 上传单个文件
     * @param url 上传路径
     * @param destDic 本地文件路径
     * @param params 传递参数
     * @return
     * @throws Exception
     */
    public CloseableHttpResponse upLoad(String url,String destDic,Map<String,String> params) throws Exception {
        EntityBuilder builder = EntityBuilder.create();
        if(params!=null&&params.size()>0){
            List<NameValuePair> list = getParam(params);
            builder.setParameters(list);
        }
        builder.setFile(new File(destDic));
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(builder.build());
        CloseableHttpResponse httpResponse = httpclient.execute(httpPost);
        logger.info("request Line:"+httpPost.getRequestLine());
        return httpResponse;
    }
    /**
     * 关闭http请求
     * @throws IOException
     */
    public void close() throws IOException {
        httpclient.close();
    }
    public void printResponse(CloseableHttpResponse httpResponse)
            throws ParseException, IOException {
        // 获取响应消息实体
        HttpEntity entity = httpResponse.getEntity();
        // 响应状态
        System.out.println("status:" + httpResponse.getStatusLine());
        System.out.println("response headers:");
        HeaderIterator iterator = httpResponse.headerIterator();
        while (iterator.hasNext()) {
            System.out.println("\t" + iterator.next());
        }
        // 判断响应实体是否为空
        if (entity != null) {
            String responseString = EntityUtils.toString(entity);
            System.out.println("response length:" + responseString.length());
            System.out.println("response content:"
                    + responseString.replace("\r\n", ""));
        }
    }
    
    private List<NameValuePair> getParam(Map parameterMap) {
        List<NameValuePair> param = new ArrayList<NameValuePair>();
        if (parameterMap==null||parameterMap.size()==0){
            return param;
        }
        Iterator it = parameterMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry parmEntry = (Map.Entry) it.next();
            param.add(new BasicNameValuePair((String) parmEntry.getKey(),
                    (String) parmEntry.getValue()));
        }
        return param;
    }
}
