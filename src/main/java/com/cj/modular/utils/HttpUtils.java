package com.cj.modular.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * send web requests tool class
 * </p>
 *
 * @author Caoj
 * @since 2022-01-12 14:09
 */
@Slf4j
public class HttpUtils {
    private final static MultiThreadedHttpConnectionManager manager = new MultiThreadedHttpConnectionManager();
    // 支持重复连接
    private static final HttpClient client = new HttpClient(manager);

    /**
     * post请求
     * @param reqUrl 请求地址
     * @param parameters 请求参数
     * @return String
     */
    public static String doPost(String reqUrl, Map<String, String> parameters, String token) {
        HttpURLConnection urlConn = null;
        try {
            urlConn = _sendPost(reqUrl, parameters, token);
            String responseContent = _getContent(urlConn);
            return responseContent.trim();
        } finally {
            if (urlConn != null) {
                urlConn.disconnect();
            }
        }
    }

    /**
     * 从网络上下载文件
     *
     * @param reqUrl 请求url
     * @param parameters 请求参数
     * @param fileParamName 文件参数名
     * @param filename 文件名
     * @param contentType 内容类型
     * @param data 数据
     * @return String
     */
    public static String doUploadFile(String reqUrl, Map<String, String> parameters, String fileParamName,
                                      String filename, String contentType, byte[] data) {
        HttpURLConnection urlConn = null;
        try {
            urlConn = sendFormData(reqUrl, parameters, fileParamName, filename, contentType, data);
            String responseContent = new String(_getBytes(urlConn));
            return responseContent.trim();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if (urlConn != null) {
                urlConn.disconnect();
            }
        }
    }

    private static HttpURLConnection sendFormData(String reqUrl, Map<String, String> parameters, String fileParamName,
                                                   String filename, String contentType, byte[] data) {
        HttpURLConnection urlConn;
        try {
            URL url = new URL(reqUrl);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("POST");
            urlConn.setConnectTimeout(5000);// （单位：毫秒）jdk
            urlConn.setReadTimeout(5000);// （单位：毫秒）jdk 1.5换成这个,读操作超时
            urlConn.setDoOutput(true);

            urlConn.setRequestProperty("Connection", "keep-alive");

            String boundary = "-----------------------------114975832116442893661388290519"; // 分隔符
            urlConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            boundary = "--" + boundary;
            StringBuilder params = new StringBuilder();
            if (parameters != null) {
                for (String name : parameters.keySet()) {
                    String value = parameters.get(name);
                    params.append(boundary).append("\r\n");
                    params.append("Content-Disposition: form-data; name=\"").append(name).append("\"\r\n\r\n");
                    // params.append(URLEncoder.encode(value, "UTF-8"));
                    params.append(value);
                    params.append("\r\n");
                }
            }

            // sb.append("\r\n");
            String sb = boundary +
                    "\r\n" +
                    "Content-Disposition: form-data; name=\"" + fileParamName + "\"; filename=\"" + filename
                    + "\"\r\n" +
                    "Content-Type: " + contentType + "\r\n\r\n";
            byte[] fileDiv = sb.getBytes();
            byte[] endData = ("\r\n" + boundary + "--\r\n").getBytes();
            byte[] ps = params.toString().getBytes();

            OutputStream os = urlConn.getOutputStream();
            os.write(ps);
            os.write(fileDiv);
            os.write(data);
            os.write(endData);

            os.flush();
            os.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return urlConn;
    }

    private static String _getContent(HttpURLConnection urlConn) {
        try {
            String responseContent;
            InputStream in = urlConn.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String tempLine = rd.readLine();
            StringBuilder tempStr = new StringBuilder();
            String crlf = System.getProperty("line.separator");
            while (tempLine != null) {
                tempStr.append(tempLine);
                tempStr.append(crlf);
                tempLine = rd.readLine();
            }
            responseContent = tempStr.toString();
            rd.close();
            in.close();
            return responseContent;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static byte[] _getBytes(HttpURLConnection urlConn) {
        try {
            InputStream in = urlConn.getInputStream();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for (int i; (i = in.read(buf)) > 0;)
                os.write(buf, 0, i);
            in.close();
            return os.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static HttpURLConnection _sendPost(String reqUrl, Map<String, String> parameters, String token) {
        HttpURLConnection urlConn;
        try {
            StringBuilder params = new StringBuilder();
            if (parameters != null) {
                for (Iterator<String> iter = parameters.keySet().iterator(); iter.hasNext();) {
                    String name = iter.next();
                    String value = parameters.get(name);
                    params.append(name).append("=");
                    params.append(URLEncoder.encode(value, "UTF-8"));
                    if (iter.hasNext())
                        params.append("&");
                }
            }

            URL url = new URL(reqUrl);
            urlConn = (HttpURLConnection) url.openConnection();

            if(StringUtils.isNotBlank(token)) {
                urlConn.addRequestProperty("token", token);
            }

            urlConn.setRequestMethod("POST");
            urlConn.setConnectTimeout(5000);// （单位：毫秒）jdk
            urlConn.setReadTimeout(5000);// （单位：毫秒）jdk 1.5换成这个,读操作超时
            urlConn.setDoOutput(true);
            byte[] b = params.toString().getBytes();
            urlConn.getOutputStream().write(b, 0, b.length);
            urlConn.getOutputStream().flush();
            urlConn.getOutputStream().close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return urlConn;
    }

    /**
     * 发送GET请求
     *
     * @param link 链接
     * @param charset 字符集
     * @return java.lang.String
     */
    public static String doGet(String link, String charset, String token) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(link);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            if(StringUtils.isNotEmpty(token)) {
                conn.addRequestProperty("token", token);
            }

            BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for (int i; (i = in.read(buf)) > 0;) {
                out.write(buf, 0, i);
            }
            out.flush();
            out.close();
            return out.toString(charset);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /**
     * UTF-8编码
     *
     * @param link 链接
     * @return java.lang.String
     */
    public static String doGet(String link, String token) {
        return doGet(link, "UTF-8", token);
    }

    /**
     * 发送GET请求
     *
     * @param link 链接
     * @return int
     */
    public static int getIntResponse(String link, String token) {
        String str = doGet(link, token);
        return Integer.parseInt(str.trim());
    }

    public static long ip2Long(String strIP) {
        long[] ip = new long[4];
        // 先找到IP地址字符串中.的位置
        int position1 = strIP.indexOf(".");
        int position2 = strIP.indexOf(".", position1 + 1);
        int position3 = strIP.indexOf(".", position2 + 1);
        // 将每个.之间的字符串转换成整型
        ip[0] = Long.parseLong(strIP.substring(0, position1));
        ip[1] = Long.parseLong(strIP.substring(position1 + 1, position2));
        ip[2] = Long.parseLong(strIP.substring(position2 + 1, position3));
        ip[3] = Long.parseLong(strIP.substring(position3 + 1));
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }

    // 将10进制整数形式转换成127.0.0.1形式的IP地址
    public static String long2IP(long longIP) {
        // 直接右移24位
        return "" + (longIP >>> 24) + "." +
                // 将高8位置0，然后右移16位
                ((longIP & 0x00FFFFFF) >>> 16) + "." +
                ((longIP & 0x0000FFFF) >>> 8) + "." +
                (longIP & 0x000000FF);
    }

    /**
     *
     * urlParse:url解析. <br/>
     *
     * @author majun
     * @param arrList 字符串集合
     * @param url url
     * @return Map<String, String>
     * @since JDK 1.6
     */
    public static Map<String, String> urlParse(List<String> arrList, String url) {

        Map<String, String> valuesMap = new HashMap<>();
        for (String s : arrList) {
            Pattern pattern = Pattern.compile(s + "=([^&]*)(&|$)");
            Matcher matcher = pattern.matcher(url);
            if (matcher.find()) {
                String[] arr = matcher.group(1).split("'");
                valuesMap.put(s, arr[1]);
            }
        }
        return valuesMap;
    }

    /***
     *
     * http_doPost: httpClient发送post 请求. <br/>
     *
     * @author majun
     * @since 创建时间：2016年6月22日 下午6:15:59
     * @since JDK 1.6
     */
    public static String http_doPost(String reqUrl, Map<String, String> parameters, String userToken) {

        try {

            // MultiThreadedHttpConnectionManager manager = new MultiThreadedHttpConnectionManager();
            // 支持重复连接
            // HttpClient client = new HttpClient(manager);

            PostMethod post = new PostMethod(reqUrl);
            post.setRequestHeader("Connection", "keep-alive");
            post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

            NameValuePair[] params = new NameValuePair[parameters.size()];
            Set<String> keys = parameters.keySet();
            int index = 0;
            for (String key : keys) {

                params[index] = new NameValuePair(key, parameters.get(key));
                index++;
            }

            post.setQueryString(params);

            if (StringUtils.isNotBlank(userToken)) {
                post.setRequestHeader("userToken", userToken);
            }

            int status = client.executeMethod(post);
            log.info("loginStatus:" + status);

            return post.getResponseBodyAsString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     *
     * http_doGet: httpClient发送get请求. <br/>
     *
     * @author majun
     * @version 创建时间：2016年6月22日 下午6:25:10
     * @since JDK 1.6
     */
    public static String http_doGet(String reqUrl, Map<String, String> parameters, String userToken) {
        try {
            // MultiThreadedHttpConnectionManager manager = new MultiThreadedHttpConnectionManager();
            // 支持重复连接
            // HttpClient client = new HttpClient(manager);
            GetMethod get = new GetMethod(reqUrl);

            NameValuePair[] params = new NameValuePair[parameters.size()];
            Set<String> keys = parameters.keySet();
            int index = 0;
            for (String key : keys) {

                params[index] = new NameValuePair(key, parameters.get(key));
                index++;
            }

            get.setQueryString(params);

            if (StringUtils.isNotBlank(userToken)) {
                get.setRequestHeader("userToken", userToken);
            }

            int status = client.executeMethod(get);
            log.info("http_doGet==>Status:" + status);
            return get.getResponseBodyAsString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String doPost(String url, Map<String, Object> paramMap) {
        return doPost(url, paramMap, null);
    }

    public static String doPost(String url, Map<String, Object> paramMap, Map<String, String> headerMap) {

        log.info("doPost url=" + url);
        CloseableHttpClient httpClient;
        CloseableHttpResponse httpResponse = null;
        String result = "";
        // 创建httpClient实例
        httpClient = HttpClients.createDefault();

        // 创建httpPost远程连接实例
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig;
        // 配置请求参数实例
        requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 设置连接主机服务超时时间
                .setConnectionRequestTimeout(35000)// 设置连接请求超时时间
                .setSocketTimeout(60000)// 设置读取数据连接超时时间
                .build();

        // 为httpPost实例设置配置
        httpPost.setConfig(requestConfig);
        // 设置请求头
        if (headerMap == null){
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");// "application/json"
        } else {
            for (String key : headerMap.keySet()) {
                httpPost.addHeader(key, headerMap.get(key));
            }
        }

        // 封装post请求参数
        if (null != paramMap && paramMap.size() > 0) {
            List<BasicNameValuePair> nvps = new ArrayList<>();
            // 通过map集成entrySet方法获取entity
            Set<Map.Entry<String, Object>> entrySet = paramMap.entrySet();
            // 循环遍历，获取迭代器
            for (Map.Entry<String, Object> mapEntry : entrySet) {
                nvps.add(new BasicNameValuePair(mapEntry.getKey(), mapEntry.getValue().toString()));
            }

            // 为httpPost设置封装好的请求参数
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        try {
            // httpClient对象执行post请求,并返回响应参数对象
            httpResponse = httpClient.execute(httpPost);
            // 从响应对象中获取响应内容
            HttpEntity entity = httpResponse.getEntity();
            result = EntityUtils.toString(entity);
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

    public static String requestPayload(String httpUrl, String jsonStr) {
        return requestPayload(httpUrl, jsonStr, null);
    }

    public static String requestPayload(String httpUrl, String jsonStr, Map<String, String> headerMap) {
        log.info("httpUrl=" + httpUrl);
        log.info("jsonStr=" + jsonStr);
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse closeableHttpResponse = null;
        try {
            httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(httpUrl);

            // 设置请求头
            if (headerMap != null){
                for (String key : headerMap.keySet()) {
                    httpPost.addHeader(key, headerMap.get(key));
                }
            }

            StringEntity stringEntity = new StringEntity(jsonStr, "UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            closeableHttpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = closeableHttpResponse.getEntity();
            return EntityUtils.toString(httpEntity);// 响应内容
        } catch (UnsupportedCharsetException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (closeableHttpResponse != null) {
                try {
                    closeableHttpResponse.close();
                } catch (IOException ignored) {
                }
            }
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException ignored) {
                }
            }
        }
        return null;
    }
}
