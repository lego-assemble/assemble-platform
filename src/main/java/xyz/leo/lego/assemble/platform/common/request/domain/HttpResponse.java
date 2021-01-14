package xyz.leo.lego.assemble.platform.common.request.domain;

import java.util.Map;

public class HttpResponse {
    /**
     * 状态码
     */
    private int code;

    /**
     * 返回的header信息
     */
    private Map<String, Object> header;

    /**
     * 具体的body信息
     */
    private String body;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Map<String, Object> getHeader() {
        return header;
    }

    public void setHeader(Map<String, Object> header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
