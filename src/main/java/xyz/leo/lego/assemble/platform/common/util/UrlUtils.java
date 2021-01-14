package xyz.leo.lego.assemble.platform.common.util;

import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author xuyangze
 * @date 2019/1/17 2:56 PM
 */
@Slf4j
public class UrlUtils {
    private static final String UTF = "utf-8";

    private static final String GT = ">";
    private static final String GT_ENCODE = "&gt;";

    /**
     * url解码
     * @param source
     * @return
     */
    public static String urlDecode(String source) {
        try {
            return URLDecoder.decode(source, UTF);
        } catch (Exception e) {
            log.error("url decode error", e);
            return source;
        }
    }

    public static String urlEncode(String source) {
        try {
            return URLEncoder.encode(source, UTF);
        } catch (Exception e) {
            log.error("url encode error", e);
            return source;
        }
    }

    public static String uriEncode(String source) {
        try {
            StringBuilder newUri = new StringBuilder();
            StringBuilder encode = new StringBuilder();
            int index = 1;
            for (char value : source.toCharArray()) {
                if (value == '/' || value == '?' || index == source.length()) {
                    String encodeValue = URLEncoder.encode(encode.toString(), UTF);
                    encodeValue = encodeValue.replace("+", "%20");

                    newUri.append(encodeValue);
                    newUri.append(value);

                    encode = new StringBuilder();
                    continue;
                }

                encode.append(value);
                index ++;
            }

            String encodeValue = URLEncoder.encode(encode.toString(), UTF);
            encodeValue = encodeValue.replace("+", "%20");
            newUri.append(encodeValue);
            return newUri.toString();
        } catch (Exception e) {
            log.error("url encode error", e);
            return source;
        }
    }

    public static String encode(String source, String encode) {
        try {
            return new String(source.getBytes(), source);
        } catch (Exception e) {
            log.error("url encode error", e);
            return source;
        }
    }

    public static String decodeSql(String source) {
        return source.replace(GT_ENCODE, GT);
    }

    public static String getHost(String source) {
        String host = null;
        try {
            URL url = new URL(source);
            host = url.getHost();
            return host;
        } catch (Exception e) {
            log.error("get url error: " + source, e);
        }

        return null;
    }

    public static int getPort(String source) {
        try {
            URL url = new URL(source);
            int port = url.getPort();
            if (-1 == port) {
                return 80;
            }

            return port;
        } catch (Exception e) {
            log.error("get url error: " + source, e);
        }

        return 80;
    }

    public static String getUri(String source) {
        String uri = null;
        try {
            URL url = new URL(source);
            uri = url.toURI().getPath();
            if (null != uri && uri.length() > 0) {
                uri = uri.substring(1);
                return uri;
            }
        } catch (Exception e) {
            log.error("get uri error: " + source, e);
        }

        return null;
    }

    public static boolean isNotEmpty(String value) {
        return null != value && value.length() > 0;
    }
}
