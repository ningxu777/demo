package com.neil.demo.httpclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Neil on 2018/4/19.
 */
public class HttpClientUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();


    /**
     * 参数拼在"？"后面的get请求
     * @param url
     * @return
     */
    public static String get(String url, Map<String, String> para){
//        HttpGet httpGet = new HttpGet(url);
        URIBuilder builder = null;
        try {
            builder = new URIBuilder(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Set<String> set = para.keySet();
        for(String key: set){
            builder.setParameter(key, para.get(key));
        }
        HttpGet request = null;
        try {
            request = new HttpGet(builder.build());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(6000)
                .setConnectTimeout(6000)
                .setConnectionRequestTimeout(6000).build();
        request.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {

            response = httpClient.execute(request);
            if(response.getStatusLine().getStatusCode() == 200){
                String rel = EntityUtils.toString(response.getEntity(),"utf-8");
                return rel;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * POST请求
     */

    public static String post(String url, Map<String,Object> params){
        HttpPost httpPost = new HttpPost(url);

        //设置请求参数
        List<NameValuePair> pairList = new ArrayList<NameValuePair>();
//        if(params != null){
//            for(Map.Entry<String,Object> item:params.entrySet()){
//                pairList.add(new BasicNameValuePair(item.getKey(),String.valueOf(item.getValue())));
//            }
//            // 构造一个form表单式的实体
//            try {
//                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(pairList, Consts.UTF_8);
//                httpPost.setEntity(formEntity);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

        //伪装浏览器请求 // TODO: 2018/4/19 不知道为什么要这一行
//        httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.118 Safari/537.36");
//        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");

        httpPost.addHeader("Content-Type", "application/json_xml;charset=UTF-8");
        String json = null;
        try {
            json = objectMapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        StringEntity stringEntity = new StringEntity(json, "UTF-8");
        stringEntity.setContentEncoding("UTF-8");
        httpPost.setEntity(stringEntity);
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            response = httpClient.execute(httpPost);
            if(response.getStatusLine().getStatusCode() == 200){
                String rel = EntityUtils.toString(response.getEntity());
                return rel;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return null;
    }

}
