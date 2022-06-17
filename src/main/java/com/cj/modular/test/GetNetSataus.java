package com.cj.modular.test;

import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;

/**
 * <p>
 *  测试泰州光猫的连接状态，每5秒写1次库。并且一直运行，采用crud连接池
 * </p>
 *
 * @author Caoj
 * @date 2022-06-08 13:49
 */
public class GetNetSataus extends TimerTask {
    private static String sysauth = null;

    /**
     * The action to be performed by this timer task.
     */
    @SneakyThrows
    @Override
    public void run() {
        String output = getStatus(sysauth);
        if (output.startsWith("{\"sysTime\"")) {
            JSONObject jsonObject = JSONObject.parseObject(output);
            Integer sysTime = Integer.parseInt(jsonObject.get("sysTime").toString());
            Integer upTime = Integer.parseInt(jsonObject.get("upTime").toString());
            System.out.println(sysTime);
            System.out.println(upTime);
        } else {
            login();
        }
    }

    String getStatus(String sysauth) throws IOException {
        URL url = new URL("http://192.168.1.1/cgi-bin/luci/admin/settings/gwstatus");
        URLConnection urlConnection = url.openConnection();
        urlConnection.setConnectTimeout(2000);
        urlConnection.setReadTimeout(2500);
        urlConnection.setRequestProperty("Cookie", "sysauth=" + sysauth);
        urlConnection.connect();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String output = bufferedReader.readLine();
        bufferedReader.close();
        return output;
    }

    private void login() {
        // 全局请求设置
        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
        // 创建cookie store的本地实例
        CookieStore cookieStore = new BasicCookieStore();
        // 创建HttpClient上下文
        HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(cookieStore);

        // 创建一个HttpClient
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig)
                .setDefaultCookieStore(cookieStore).build();
        CloseableHttpResponse res = null;

        // 创建本地的HTTP内容
        try {
            try {
                // 构造post数据
                List<NameValuePair> valuePairs = new LinkedList<NameValuePair>();
                valuePairs.add(new BasicNameValuePair("username", "useradmin"));
                valuePairs.add(new BasicNameValuePair("psd", "vvttk"));
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(valuePairs, Consts.UTF_8);
                entity.setContentType("application/x-www-form-urlencoded");

                // 创建一个post请求
                HttpPost post = new HttpPost("http://192.168.1.1/cgi-bin/luci");
                // 注入post数据
                post.setEntity(entity);
                res = httpClient.execute(post, context);
                res.close();

                this.sysauth = context.getCookieStore().getCookies().get(0).getValue();
//                for (Cookie c : context.getCookieStore().getCookies()) {
//                    System.out.println(c.getName() + ": " + c.getValue());
//                }
            } finally {
                httpClient.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
