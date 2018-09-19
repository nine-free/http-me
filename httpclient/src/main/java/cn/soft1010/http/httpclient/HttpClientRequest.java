package cn.soft1010.http.httpclient;


import com.google.gson.GsonBuilder;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bjzhangjifu on 2018/9/5.
 */
public class HttpClientRequest {

    public static void main(String[] args) {
        List<NameValuePair> list = new ArrayList<NameValuePair>();
//        key=%s&type=wxart&pr=web&t=%d
        NameValuePair nameValuePair = new BasicNameValuePair("key", "zhang");
        list.add(nameValuePair);
        list.add(new BasicNameValuePair("type", "wxart"));
        list.add(new BasicNameValuePair("pr", "web"));
        list.add(new BasicNameValuePair("t", "" + System.currentTimeMillis()));
        System.out.println(get("http://weixin.sogou.com/sugg/ajaj_json.jsp", list, null));
        System.out.println(post("http://localhost:8061/login", new Req("李菲", "123456", "1231231", "123")));
    }

    static class Req {

        private String username;
        private String password;
        private String uuId;
        private String authCode;

        public Req(String username, String password, String uuId, String authCode) {
            this.username = username;
            this.password = password;
            this.uuId = uuId;
            this.authCode = authCode;
        }
    }

    public static String get(String url, List<NameValuePair> params, Header[] headers) {
        HttpClient httpClient = HttpClients.createDefault();
        String body = null;
        try {
            // Get请求
            HttpGet httpget = new HttpGet(url);

            // 设置参数
            String str = EntityUtils.toString(new UrlEncodedFormEntity(params));
            httpget.setURI(new URI(httpget.getURI().toString() + "?" + str));
            if (headers != null && headers.length > 0) {
                httpget.setHeaders(headers);
            }
            // 发送请求
            HttpResponse httpresponse = httpClient.execute(httpget);
            // 获取返回数据
            HttpEntity entity = httpresponse.getEntity();
            body = EntityUtils.toString(entity);
            if (entity != null) {
                entity.consumeContent();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return body;
    }

    public static String post(String url, Object req) {
        HttpClient httpClient = HttpClients.createDefault();
        String body = null;
        try {
//            httpClient.getParams().setParameter("http.protocol.content-charset", HTTP.UTF_8);
//            httpClient.getParams().setParameter(HTTP.CONTENT_ENCODING, HTTP.UTF_8);
//            httpClient.getParams().setParameter(HTTP.CHARSET_PARAM, HTTP.UTF_8);
//            httpClient.getParams().setParameter(HTTP.DEFAULT_PROTOCOL_CHARSET, HTTP.UTF_8);
            // Post请求
            HttpPost httppost = new HttpPost(url);
            //设置post编码
            httppost.getParams().setParameter("http.protocol.content-charset", HTTP.UTF_8);
            httppost.getParams().setParameter(HTTP.CONTENT_ENCODING, HTTP.UTF_8);
            httppost.getParams().setParameter(HTTP.CHARSET_PARAM, HTTP.UTF_8);
            httppost.getParams().setParameter(HTTP.DEFAULT_PROTOCOL_CHARSET, HTTP.UTF_8);

            // 设置参数
//            List<NameValuePair> params = new ArrayList<NameValuePair>();
//            params.add(new BasicNameValuePair("$xmldata", reqXml));
//            httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            StringEntity entity1 = new StringEntity(new GsonBuilder().create().toJson(req), "UTF-8");
            entity1.setContentType("application/json;charset=UTF-8");
            entity1.setContentEncoding("UTF-8");
            httppost.setEntity(entity1);
            //设置报文头
            httppost.setHeader("Content-Type", "application/json;charset=UTF-8");
            // 发送请求
            HttpResponse httpresponse = httpClient.execute(httppost);
            // 获取返回数据
            HttpEntity entity = httpresponse.getEntity();
            body = EntityUtils.toString(entity);
            if (entity != null) {
                entity.consumeContent();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }
}
