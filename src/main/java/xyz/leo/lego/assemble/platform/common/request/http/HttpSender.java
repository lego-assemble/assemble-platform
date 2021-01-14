package xyz.leo.lego.assemble.platform.common.request.http;

import com.alibaba.fastjson.JSON;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.protocol.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.leo.lego.assemble.platform.common.request.domain.HttpConnection;
import xyz.leo.lego.assemble.platform.common.request.domain.HttpRequest;
import xyz.leo.lego.assemble.platform.common.request.domain.HttpResponse;
import xyz.leo.lego.assemble.platform.common.request.domain.HttpsProtocol;
import xyz.leo.lego.assemble.platform.common.request.thread.HttpThreadPool;
import xyz.leo.lego.assemble.platform.common.util.UrlUtils;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class HttpSender {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpSender.class);

    /**
     * 提交请求到线程池，返回流信息
     * @param request
     * @return
     */
    public static Future<HttpConnection> submit(HttpRequest request) {
        if(request == null || request.getHost() == null) {
            return null;
        }

        HttpConnectionCallable callable = new HttpConnectionCallable(request);
        return HttpThreadPool.submit(callable);
    }

    /**
     * 提交请求到线程池，返回流信息
     * @param request
     * @return
     */
    public static Future<InputStream> submitAsStream(HttpRequest request) {
        if(request == null || request.getHost() == null) {
            return null;
        }

        HttpResponseStreamCallable callable = new HttpResponseStreamCallable(request);
        return HttpThreadPool.submit(callable);
    }

    /**
     * 提交请求到线程池，返回流后直接解析成body
     * @param request
     * @return
     */
    public static Future<HttpResponse> submitRequest(HttpRequest request) {
        if(request == null || request.getHost() == null) {
            return null;
        }

        HttpResponseCallable callable = new HttpResponseCallable(request);
        return HttpThreadPool.submit(callable);
    }

    /**
     * 同步接收流信息
     * @param request
     * @return
     */
    public static HttpConnection receive(HttpRequest request) {
        try {
            HttpConnection connection = doRequest(request);
            return connection;
        } catch (Exception e) {
            LOGGER.error("Exception", e);
        }

        return null;
    }

    /**
     * 同步接收信息
     * @param request
     * @return
     */
    public static HttpResponse send(HttpRequest request) {
        HttpConnection connection = receive(request);
        if(null == connection) {
            return null;
        }

        connection.receive();
        return connection.getResponse();
    }

    private static String query(HttpRequest request) {
        if (null == request.getQuery() || request.getQuery().length() == 0) {
            return query(request.getParams());
        }

        return request.getQuery();
    }

    private static String query(Map<String, Object> params) {
        if(params == null || params.size() == 0) {
            return "";
        }

        StringBuffer query = new StringBuffer();
        for(String key : params.keySet()) {
            String encode = "";
            try {
                Object value = params.get(key);
                if (null == value) {
                    continue;
                }

                encode = URLEncoder.encode(value.toString(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("UnsupportedEncodingException", e);
            }

            if(encode == null || encode.equals("")) {
                continue;
            }

            query.append(key + "=" + encode + "&");
        }

        return query.toString();
    }

    private static HttpConnection doRequest(HttpRequest request) throws Exception {
        HttpClient httpClient = new HttpClient();
        HttpMethod httpMethod;

        String uri = resolverRequestUri(request);
        httpMethod = HttpUtils.getHttpMethod(request.getMethod(), uri);
        if(httpMethod instanceof EntityEnclosingMethod) {
            EntityEnclosingMethod postMethod = (EntityEnclosingMethod)httpMethod;
            if (null != request.getBody()) {
                postMethod.setRequestEntity(new StringRequestEntity(request.getBody(), request.getBodyEncode() == HttpRequest.BodyEncode.JSON ? "application/json" : "application/x-www-form-urlencode", "utf-8"));
            } else if (request.getBodyEncode() == HttpRequest.BodyEncode.QUERY) {
                if(request.getBodyParams() != null && request.getBodyParams().size() > 0) {
                    postMethod.setRequestEntity(new StringRequestEntity(query(request.getBodyParams()), "application/x-www-form-urlencode", "utf-8"));
                }
            } else if (request.getBodyEncode() == HttpRequest.BodyEncode.JSON) {
                if(request.getBodyParams() != null && request.getBodyParams().size() > 0) {
                    postMethod.setRequestEntity(new StringRequestEntity(JSON.toJSONString(request.getBodyParams()), "application/json", "utf-8"));
                }
            }

            httpMethod = postMethod;
        } else {
            if (request.isFollowRedirects()) {
                httpMethod.setFollowRedirects(request.isFollowRedirects());
            }
        }

        if(request.getHeaders() != null && request.getHeaders().size() > 0) {
            for(String key : request.getHeaders().keySet()) {
                httpMethod.setRequestHeader(key, request.getHeaders().get(key));
            }
        }

        if(request.isHttps()) {
            Protocol https = new Protocol("https", new HttpsProtocol(), 443);
            httpClient.getHostConfiguration().setHost(request.getHost(), 443, https);
        } else {
            httpClient.getHostConfiguration().setHost(request.getHost(), request.getPort());
        }

        if (null != request.getProxyHost()) {
            httpClient.getHostConfiguration().setProxy(request.getProxyHost(), request.getProxyPort());
        }

        int timeout = request.getTimeout();
        if(timeout < 0) {
            timeout = 2000;
        }

        httpClient.getHttpConnectionManager().getParams().setSoTimeout(timeout);
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
        int code = httpClient.executeMethod(httpMethod);

        HttpResponse response = new HttpResponse();
        response.setCode(code);

        Map<String, Object> responseHeaders = new HashMap<String, Object>();
        for(Header header : httpMethod.getResponseHeaders()) {
            responseHeaders.put(header.getName(), header.getValue());
        }

        response.setHeader(responseHeaders);
        HttpConnection connection = new HttpConnection();
        connection.setResponse(response);
        connection.setInputStream(httpMethod.getResponseBodyAsStream());
        return connection;
    }

    private static String resolverRequestUri(HttpRequest request) {
        String uri = request.getUri();
        if (null != uri) {
            uri = UrlUtils.uriEncode(uri);
            String query = query(request);
            if (uri.startsWith("/")) {
                if (UrlUtils.isNotEmpty(query)) {
                    uri = request.getUri() + "?" + query;
                }

            } else {
                if (UrlUtils.isNotEmpty(query)) {
                    uri = "/" + request.getUri() + "?" + query;
                }
            }
        }

        return uri;
    }

    static class HttpConnectionCallable implements Callable {
        private HttpRequest request;

        public HttpConnectionCallable(HttpRequest request) {
            this.request = request;
        }

        public HttpConnection call() throws Exception {
            return HttpSender.doRequest(request);
        }
    }

    static class HttpResponseCallable implements Callable {
        private HttpRequest request;

        public HttpResponseCallable(HttpRequest request) {
            this.request = request;
        }

        public HttpResponse call() throws Exception {
            HttpConnection connection = HttpSender.doRequest(request);
            connection.receive();
            return connection.getResponse();
        }
    }

    static class HttpResponseStreamCallable implements Callable {
        private HttpRequest request;

        public HttpResponseStreamCallable(HttpRequest request) {
            this.request = request;
        }

        public InputStream call() throws Exception {
            HttpConnection connection = HttpSender.doRequest(request);
            return connection.getInputStream();
        }
    }
}
