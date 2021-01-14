package xyz.leo.lego.assemble.platform.common.request.domain;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@Slf4j
@Data
public class HttpConnection{
    /**
     * 获取流
     */
    private InputStream inputStream;

    /**
     * 获取响应信息，但是不会解析body
     */
    private HttpResponse response;

    /**
     * 接收数据写入到response的body
     */
    public void receive() {
        try {
            InputStream in = inputStream;
            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(reader);

            StringBuffer stringBuffer = new StringBuffer();
            bufferedReader.lines().forEach(line -> stringBuffer.append(line).append("\n"));
            bufferedReader.close();
            in.close();
            reader.close();
            response.setBody(stringBuffer.toString());
        } catch (Exception e) {
            log.error("receive response error", e);
        }
    }
}
