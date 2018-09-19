package cn.soft1010.http.httpclient;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by bjzhangjifu on 2018/9/19.
 */
public class HttpClientSimple {

    public static void main(String[] args) throws URISyntaxException {








        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        URI uri = new URIBuilder().
                setScheme("https").
                setHost("www.google.com").
                setPath("/search").
                setParameter("q", "张").
                setParameter("btnG","Google Search").
                setParameter("oq","").
                setParameter("aq","f").
                build();
        CloseableHttpResponse closeableHttpResponse = null;
        try {
            HttpGet httpGet = new HttpGet(uri);
            System.out.println(httpGet.getURI().toString());
            closeableHttpResponse = closeableHttpClient.execute(httpGet);
            System.out.println(closeableHttpResponse.getStatusLine().getStatusCode());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (closeableHttpResponse != null) {
                try {
                    closeableHttpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
