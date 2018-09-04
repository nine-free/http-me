package cn.soft1010.http.java;

import java.io.*;
import java.net.HttpURLConnection;


import java.net.URL;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPOutputStream;

import static java.lang.String.format;

/**
 * Created by bjzhangjifu on 2018/8/27.
 */
public class HttpSimple {
    public static void main(String[] args) throws IOException {
        String str = "{\n" +
                "  \"page\":1,\n" +
                "  \"pageSize\":10\n" +
                "}";
        byte[] bytes = str.getBytes();
        Request request = new Request.Builder()
                .url("http://localhost:8061/admin/action/list")
                .addHeader("Content-Type", "application/json")
                .addHeader("Token", "1aHI_wGCM_UiYnJgn3nO5mmP2aRyvCVXUFfUxUTiFYqNSZzFmaz8qXzTdHAdE8tVnGZuVKoWf97eAuGuDp_xNA")
                .httpMethod(Request.HttpMethod.POST)
                .body(bytes)
                .build();
        HttpURLConnection connection = send(request, new Request.Options(60 * 1000, 10 * 1000, true));
        Object obj = accept(connection);
        System.out.println(obj);



        Request requestGet = new Request.Builder()
                .url("http://localhost:8061/admin/action/all")
//                .addHeader("Content-Type", "application/json")
                .addHeader("Token", "1aHI_wGCM_UiYnJgn3nO5mmP2aRyvCVXUFfUxUTiFYqNSZzFmaz8qXzTdHAdE8tVnGZuVKoWf97eAuGuDp_xNA")
                .httpMethod(Request.HttpMethod.GET)
//                .body(bytes)
                .build();
        HttpURLConnection connectionGet = send(requestGet, new Request.Options(60 * 1000, 10 * 1000, true));
        Object objGet = accept(connectionGet);
        System.out.println(objGet);





    }

    private static HttpURLConnection send(Request request, Request.Options options) throws IOException {
        final HttpURLConnection connection =
                (HttpURLConnection) new URL(request.url()).openConnection();
        connection.setConnectTimeout(options.connectTimeoutMillis());
        connection.setReadTimeout(options.readTimeoutMillis());
        connection.setAllowUserInteraction(false);
        connection.setInstanceFollowRedirects(options.isFollowRedirects());
        connection.setRequestMethod(request.httpMethod().name());

        Collection<String> contentEncodingValues = request.headers().get(Util.CONTENT_ENCODING);
        boolean gzipEncodedRequest =
                contentEncodingValues != null && contentEncodingValues.contains(Util.ENCODING_GZIP);
        boolean deflateEncodedRequest =
                contentEncodingValues != null && contentEncodingValues.contains(Util.ENCODING_DEFLATE);

        boolean hasAcceptHeader = false;
        Integer contentLength = null;
        for (String field : request.headers().keySet()) {
            if (field.equalsIgnoreCase("Accept")) {
                hasAcceptHeader = true;
            }
            for (String value : request.headers().get(field)) {
                if (field.equals(Util.CONTENT_LENGTH)) {
                    if (!gzipEncodedRequest && !deflateEncodedRequest) {
                        contentLength = Integer.valueOf(value);
                        connection.addRequestProperty(field, value);
                    }
                } else {
                    connection.addRequestProperty(field, value);
                }
            }
        }
        // Some servers choke on the default accept string.
        if (!hasAcceptHeader) {
            connection.addRequestProperty("Accept", "*/*");
        }

        if (request.body() != null) {
            if (contentLength != null) {
                connection.setFixedLengthStreamingMode(contentLength);
            } else {
                connection.setChunkedStreamingMode(8196);
            }
            connection.setDoOutput(true);
            OutputStream out = connection.getOutputStream();
            if (gzipEncodedRequest) {
                out = new GZIPOutputStream(out);
            } else if (deflateEncodedRequest) {
                out = new DeflaterOutputStream(out);
            }
            try {
                out.write(request.body());
            } finally {
                try {
                    out.close();
                } catch (IOException suppressed) { // NOPMD
                }
            }
        }
        return connection;
    }

    private static Object accept(HttpURLConnection connection) throws IOException {
        int status = connection.getResponseCode();
        String reason = connection.getResponseMessage();

        if (status < 0) {
            throw new IOException(format("Invalid status(%s) executing %s %s", status,
                    connection.getRequestMethod(), connection.getURL()));
        }

        Map<String, Collection<String>> headers = new LinkedHashMap<String, Collection<String>>();
        for (Map.Entry<String, List<String>> field : connection.getHeaderFields().entrySet()) {
            // response message
            if (field.getKey() != null) {
                headers.put(field.getKey(), field.getValue());
            }
        }

        Integer length = connection.getContentLength();
        if (length == -1) {
            length = null;
        }
        InputStream stream;
        if (status >= 400) {
            stream = connection.getErrorStream();
        } else {
            stream = connection.getInputStream();
        }
        Reader reader = new InputStreamReader(stream);
        String result = Util.toString(reader);
        return result;
    }
}
