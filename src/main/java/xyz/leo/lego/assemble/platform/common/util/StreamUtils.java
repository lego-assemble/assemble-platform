package xyz.leo.lego.assemble.platform.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.regex.Pattern;

/**
 * @author xuyangze
 * @date 2019/5/30 10:26 PM
 */
@Slf4j
public class StreamUtils {
    public static byte[] read2ByteArray(InputStream inStream) {
        if (null == inStream) {
            return null;
        }

        try {
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = inStream.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }

            byte[] in2b = swapStream.toByteArray();
            return in2b;
        } catch (Exception e) {
            log.error("input2byte error", e);
        }

        return null;
    }

    public static String read2String(InputStream inStream) {
        return read2String(inStream, "utf-8");
    }

    public static String read2String(InputStream inStream, String encoding) {
        byte[] data = read2ByteArray(inStream);
        if (null == data) {
            return null;
        }

        try {
            return new String(data, encoding);
        } catch (Exception e) {
            log.error("error", e);
        }

        return null;
    }

    public static String html2Text(String inputString) {
        String htmlStr = inputString; //含html标签的字符串
        String textStr = "";
        Pattern p_script;
        java.util.regex.Matcher m_script;
        Pattern p_style;
        java.util.regex.Matcher m_style;
        Pattern p_html;
        java.util.regex.Matcher m_html;

        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> }
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style> }
            String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式

            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); //过滤script标签

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); //过滤style标签

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); //过滤html标签
            htmlStr = htmlStr.replace("  ", "");
            htmlStr = htmlStr.replace("\n", "");
            htmlStr = htmlStr.replace("\t", "");
            htmlStr = htmlStr.replace("\r", "");
            htmlStr = htmlStr.replace("&nbsp;", "");
            textStr = htmlStr.trim();
            textStr = htmlStr;
        } catch (Exception e) {

        }

        return textStr;//返回文本字符串
    }
}
