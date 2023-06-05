package com.lwc.sportcommunity.hwkit;

import com.alibaba.fastjson.JSONObject;
import com.lwc.sportcommunity.error.EmSportError;
import com.lwc.sportcommunity.error.SportException;
import com.lwc.sportcommunity.utils.JsonUtils;
import com.sun.tools.internal.ws.wsdl.document.soap.SOAPUse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Create by LWC on 2023/5/24 16:42
 */
public class HwUtil {
    //基础信息
    private static final int clientId = 0;
    private static final String clientSecret = "";
    //授权code 回调地址url
    private static final String redirectUri = "http://im7tif.natappfree.cc/detection/getCode";
    //userId  token
    private Map<Integer, TokenResponse> map = new HashMap<>();

    private String doPost(String url) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        String result = "";
        // 创建httpClient实例
        httpClient = HttpClients.createDefault();
        // 创建httpPost远程连接实例
        HttpPost httpPost = new HttpPost(url);
        // 配置请求参数实例
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 设置连接主机服务超时时间
                .setConnectionRequestTimeout(35000)// 设置连接请求超时时间
                .setSocketTimeout(60000)// 设置读取数据连接超时时间
                .build();
        // 为httpPost实例设置配置
        httpPost.setConfig(requestConfig);
        // 设置请求头
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.addHeader("Host", "oauth-login.cloud.huawei.com");

        try {
            // httpClient对象执行post请求,并返回响应参数对象
            httpResponse = httpClient.execute(httpPost);
            // 从响应对象中获取响应内容
            HttpEntity entity = httpResponse.getEntity();
            result = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != httpResponse) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    private String doPost(String url, String at, JSONObject param) {
        // 创建HttpClient对象
        HttpClient httpClient = HttpClientBuilder.create().build();
        // 创建HttpPost对象，并设置URL
        HttpPost httpPost = new HttpPost(url);

        String responseBody = "";
        try {
            // 设置请求头
            httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
            httpPost.setHeader("Authorization", "Bearer " + at);
            httpPost.setHeader("x-client-id", clientId+"");

            String json = param.toJSONString();
            // 创建StringEntity对象，设置JSON数据和编码方式
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            // 设置请求体
            httpPost.setEntity(entity);

            // 发送请求并获取响应
            HttpResponse response = httpClient.execute(httpPost);

            // 获取响应实体
            HttpEntity responseEntity = response.getEntity();

            // 解析响应内容
            responseBody = EntityUtils.toString(responseEntity);

            // 输出响应结果
//            System.out.println(responseBody);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseBody;
    }

    public  String getAccessToken(String code,Integer state){
        String url =    "https://oauth-login.cloud.huawei.com/oauth2/v3/token?" +
                "client_id=" + clientId +
                "&grant_type=authorization_code" +
                "&redirect_uri=" + redirectUri +
                "&client_secret=" + clientSecret +
                "&code=" + URLEncoder.encode(code);
        String result = doPost(url);
        TokenResponse tokenResponse = JsonUtils.jsonToPojo(result, TokenResponse.class);
        map.put(state, tokenResponse);
        return tokenResponse.getAccess_token();
    }

    public  String getSportInfo(Integer userId) throws SportException {
        if (!map.containsKey(userId)){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR,"请先开启运动检测功能，才有数据");
        }
        getAtByRt(userId);
        TokenResponse tokenResponse = map.get(userId);
//        System.out.println(tokenResponse.getRefresh_token());
        String url =
                "https://health-api.cloud.huawei.com/healthkit/v1/sampleSet:dailyPolymerize";

        //定义发送数据
        JSONObject param = new JSONObject();
        List<String> dataTypes = new ArrayList<>();
        dataTypes.add("com.huawei.continuous.steps.delta");
        dataTypes.add("com.huawei.continuous.distance.delta");
        dataTypes.add("com.huawei.continuous.calories.burnt");
        param.put("dataTypes", dataTypes);

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = currentDate.format(formatter);
        param.put("startDay", formattedDate);
        param.put("endDay", formattedDate);
        param.put("timeZone", "+0800");
        String result = doPost(url, tokenResponse.getAccess_token(), param);
//        System.out.println(result);
        return result;
    }

    private void getAtByRt(Integer userId){
        TokenResponse tokenResponse = map.get(userId);
        String url = "https://oauth-login.cloud.huawei.com/oauth2/v3/token";
        // 创建 HttpClient 实例
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建 HttpPost 请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Host","oauth-login.cloud.huawei.com");

        // 创建参数列表
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("grant_type", "refresh_token"));
        params.add(new BasicNameValuePair("refresh_token", tokenResponse.getRefresh_token()));
        params.add(new BasicNameValuePair("client_id",clientId+""));
        params.add(new BasicNameValuePair("client_secret",clientSecret));
        params.add(new BasicNameValuePair("access_type","offline"));
        // 设置请求参数
        httpPost.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));

        try {
            // 发送请求并获取响应
            CloseableHttpResponse response = httpClient.execute(httpPost);

            // 处理响应
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, StandardCharsets.UTF_8);

            // 输出响应结果
//            System.out.println("Response: " + responseString);

            TokenResponse afterTokenResponse = JsonUtils.jsonToPojo(responseString, TokenResponse.class);
            map.put(userId, afterTokenResponse);

            // 关闭响应和 HttpClient
            response.close();
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

