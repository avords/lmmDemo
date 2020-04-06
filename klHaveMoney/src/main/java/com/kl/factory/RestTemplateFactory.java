package com.kl.factory;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

/**
 * Created by arno.yan on 2018/10/19.
 */
public class RestTemplateFactory {

    private static RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(RestTemplateFactory.class);
    private static HttpClientConnectionMonitorThread thread;  //HTTP链接管理器线程

    public static RestTemplate getRestTemplate() {
        if (restTemplate == null) {
            synchronized (RestTemplateFactory.class) {
                if (restTemplate == null) {
                    restTemplate = new RestTemplate();
                    restTemplate.setRequestFactory(clientHttpRequestFactory());
                    restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
                }
            }
        }
        return restTemplate;
    }

    public static HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
        try {
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    return true;
                }
            }).build();
            httpClientBuilder.setSSLContext(sslContext);
            
            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext,
                    hostnameVerifier);
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", sslConnectionSocketFactory).build();// 注册http和https请求
            // 开始设置连接池 每个连接的存活时间120秒
            //PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry, null, null, null, 120000, TimeUnit.MILLISECONDS);

            PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(
                    socketFactoryRegistry);
            poolingHttpClientConnectionManager.setMaxTotal(200); // 最大连接数2700
            poolingHttpClientConnectionManager.setDefaultMaxPerRoute(20); // 同路由并发数100，就是到每个站点保持的连接数

            RestTemplateFactory.thread=new HttpClientConnectionMonitorThread(poolingHttpClientConnectionManager); //管理 http连接池
            
            httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager);
            httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(3, true)); // 重试次数

            //connectionRequestTimeout：连接不够用的等待时间，connectTimeout：连接超时时间，socketTimeout：读取数据超时时间
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(5000).setConnectionRequestTimeout(2000)
                    .setSocketTimeout(10000).build();
            
            httpClientBuilder.setDefaultRequestConfig(requestConfig);
            HttpClient httpClient = httpClientBuilder.build();

            HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
                    httpClient); // httpClient连接配置
            
            return clientHttpRequestFactory;
        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
            logger.error("初始化HTTP连接池出错", e);
        }
        return null;
    }


    private static class HttpClientConnectionMonitorThread extends Thread{

        private final HttpClientConnectionManager connManager;
        private volatile boolean shutdown;

        public HttpClientConnectionMonitorThread(HttpClientConnectionManager connManager) {
            super();
            this.setName("http-connection-monitor");
            this.setDaemon(true);
            this.connManager = connManager;
            this.start();
        }

        @Override
        public void run() {
            try {
                while (!shutdown) {
                    synchronized (this) {
                        wait(5000); // 等待5秒
                        // 关闭过期的链接
                        connManager.closeExpiredConnections();
                        // 选择关闭 空闲30秒的链接
                        connManager.closeIdleConnections(30, TimeUnit.SECONDS);
                    }
                }
            } catch (InterruptedException ex) {
            }
        }

        /**
         * 方法描述: 停止 管理器 清理无效链接  (该方法当前暂时关闭) 
         * @author andy 2017年8月28日 下午1:45:18
         */
        @Deprecated
        public void shutDownMonitor() {
            synchronized (this) {
                shutdown = true;
                notifyAll();
            }
        }
    }
}
