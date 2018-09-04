/**
 * Copyright 2012-2018 The Feign Authors
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package cn.soft1010.http.java;

import java.nio.charset.Charset;
import java.util.*;


/**
 */
public final class Request {

    public enum HttpMethod {
        GET, HEAD, POST, PUT, DELETE, CONNECT, OPTIONS, TRACE, PATCH
    }

    public Request(Builder builder) {
        body = builder.body;
        url = builder.url;
        httpMethod = builder.httpMethod;
        headers = builder.headers;
        charset = builder.charset;
    }

    private HttpMethod httpMethod;
    private String url;
    private Map<String, Collection<String>> headers;
    private byte[] body;
    private Charset charset;

    public String method() {
        return httpMethod.name();
    }

    public HttpMethod httpMethod() {
        return this.httpMethod;
    }

    public String url() {
        return url;
    }

    public Map<String, Collection<String>> headers() {
        return headers;
    }

    public Charset charset() {
        return charset;
    }

    public byte[] body() {
        return body;
    }

    public static final class Builder {
        HttpMethod httpMethod;
        String url;
        Map<String, Collection<String>> headers = new HashMap<String, Collection<String>>();
        byte[] body;
        Charset charset;


        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder httpMethod(HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
            return this;
        }

        public Builder body(byte[] body) {
            this.body = body;
            return this;
        }

        public Builder header(Map<String, Collection<String>> headers) {
            this.headers = headers;
            return this;
        }

        public Builder addHeader(String name, String value) {
            if (this.headers.containsKey(name)) {
                this.headers.get(name).add(value);
            } else {
                List<String> values = new ArrayList<String>();
                values.add(value);
                this.headers.put(name, values);
            }
            return this;
        }

        public Builder charset(Charset charset) {
            this.charset = charset;
            return this;
        }

        public Request build() {
            return new Request(this);
        }
    }

    public static class Options {

        private final int connectTimeoutMillis;
        private final int readTimeoutMillis;
        private final boolean followRedirects;

        public Options(int connectTimeoutMillis, int readTimeoutMillis, boolean followRedirects) {
            this.connectTimeoutMillis = connectTimeoutMillis;
            this.readTimeoutMillis = readTimeoutMillis;
            this.followRedirects = followRedirects;
        }

        public Options(int connectTimeoutMillis, int readTimeoutMillis) {
            this(connectTimeoutMillis, readTimeoutMillis, true);
        }

        public Options() {
            this(10 * 1000, 60 * 1000);
        }

        public int connectTimeoutMillis() {
            return connectTimeoutMillis;
        }

        public int readTimeoutMillis() {
            return readTimeoutMillis;
        }

        public boolean isFollowRedirects() {
            return followRedirects;
        }
    }
}
