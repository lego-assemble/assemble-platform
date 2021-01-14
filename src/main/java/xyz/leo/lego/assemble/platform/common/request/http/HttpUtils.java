package xyz.leo.lego.assemble.platform.common.request.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.*;
import xyz.leo.lego.assemble.platform.common.request.domain.HttpRequest;

import java.lang.reflect.Constructor;
import java.util.EnumMap;
import java.util.Map;

/**
 * @author xuyangze
 * @date 2019/1/31 11:27 AM
 */
@Slf4j
public class HttpUtils {
    private final static Map<HttpRequest.HttpMethod, Constructor<? extends HttpMethodBase>> methodMap = new EnumMap<>(HttpRequest.HttpMethod.class);

    static {
        for (HttpRequest.HttpMethod httpMethod : HttpRequest.HttpMethod.values()) {
            switch (httpMethod) {
                case DELETE: {
                    methodMap.put(httpMethod, getConstructor(DeleteMethod.class));
                    break;
                }
                case PUT: {
                    methodMap.put(httpMethod, getConstructor(PutMethod.class));
                    break;
                }
                case HEAD: {
                    methodMap.put(httpMethod, getConstructor(HeadMethod.class));
                    break;
                }
                case POST: {
                    methodMap.put(httpMethod, getConstructor(PostMethod.class));
                    break;
                }
                case PATCH: {
                    methodMap.put(httpMethod, getConstructor(PutMethod.class));
                    break;
                }
                case TRACE: {
                    methodMap.put(httpMethod, getConstructor(TraceMethod.class));
                    break;
                }
                case OPTIONS: {
                    methodMap.put(httpMethod, getConstructor(OptionsMethod.class));
                    break;
                }
                case GET:
                default: {
                    methodMap.put(httpMethod, getConstructor(GetMethod.class));
                    break;
                }
            }
        }
    }

    private static Constructor getConstructor(Class<? extends HttpMethodBase> aClass) {
        try {
            Constructor constructor = aClass.getConstructor(new Class[]{String.class});
            return constructor;
        } catch (Exception e) {
            log.error("no constructor", e);
        }

        return null;
    }

    public static HttpMethodBase getHttpMethod(HttpRequest.HttpMethod method, String uri) {
        Constructor<? extends HttpMethodBase> constructor = methodMap.get(method);

        HttpMethodBase httpMethodBase = null;
        try {
            httpMethodBase = constructor.newInstance(uri);
        } catch (Exception e) {
            log.error("method create HttpMethodError: {}", method, e);
        }

        return httpMethodBase;
    }
}
