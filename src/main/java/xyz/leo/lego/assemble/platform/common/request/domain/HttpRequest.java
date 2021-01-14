package xyz.leo.lego.assemble.platform.common.request.domain;

import lombok.Data;
import lombok.ToString;
import xyz.leo.lego.assemble.platform.common.request.http.HttpSender;
import xyz.leo.lego.assemble.platform.common.util.UrlUtils;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.Future;

@Data
@ToString
public class HttpRequest
{
    public enum HttpMethod {
        GET,
        POST,
        DELETE,
        PUT,
        HEAD,
        OPTIONS,
        PATCH,
        TRACE,
        CONNECT
    }

    public enum BodyEncode {
        /**
         * 以query形式提交body
         */
        QUERY,

        /**
         * 以json形式提交body
         */
        JSON
    }

    /**
     * 域名，不包含http前缀
     */
    private String host;

    /**
     * 端口号，默认80
     */
    private int port;

    /**
     * uri地址，不包含'/'
     */
    private String uri;

    /**
     * 是否是https
     */
    private boolean https;

    /**
     * 请求类型，默认为GET
     */
    private HttpMethod method;

    /**
     * body的编码方式，默认为query
     */
    private BodyEncode bodyEncode;

    /**
     * 自定义的header信息
     */
    private Map<String, String> headers;

    /**
     * query的参数
     */
    private Map<String, Object> params;

    /**
     * query信息，如果存在query，则不会使用params生成query
     */
    private String query;

    /**
     * body的参数
     */
    private Map<String, Object> bodyParams;

    /**
     * body信息，当且存在body的时候bodyParams不生效，注意body需要手动设置content-type
     */
    private String body;

    /**
     * 超时时间
     */
    private int timeout;

    /**
     * 代理地址
     */
    private String proxyHost;

    /**
     * 代理端口号
     */
    private int proxyPort;

    /**
     * 是否自动重定向，GET方法生效
     */
    private boolean followRedirects;

    public HttpRequest() {
        this(null);
    }

    public HttpRequest(String url) {
        this.timeout = 1000;
        this.port = 80;
        this.followRedirects = false;
        this.method = HttpMethod.GET;
        this.bodyEncode = BodyEncode.QUERY;
        if (null != url && url.length() != 0) {
            this.host = UrlUtils.getHost(url);
            this.uri = '/' + UrlUtils.getUri(url);
            this.port = UrlUtils.getPort(url);
            this.https = url.startsWith("https://");
        }
    }

    /**
     * 发送请求，同步等待返回结果
     * @return
     */
    public HttpResponse send() {
        return HttpSender.send(this);
    }

    /**
     * 接收数据但不对body进行解析
     * @return
     */
    public HttpConnection receive() {
        return HttpSender.receive(this);
    }

    /**
     * 发送请求，同步等待返回结果，以数据流的形式返回
     * @return
     */
    public InputStream sendAsStream() {
        HttpConnection httpConnection = HttpSender.receive(this);
        if (null == httpConnection) {
            return null;
        }

        return httpConnection.getInputStream();
    }

    /**
     * 发送请求，异步等待返回结果
     * @return
     */
    public Future<HttpResponse> sendAsync() {
        return HttpSender.submitRequest(this);
    }

    /**
     * 发送请求，异步等待返回结果，以数据流的形式返回
     * @return
     */
    public Future<InputStream> sendAsyncAsStream() {
        return HttpSender.submitAsStream(this);
    }
}
