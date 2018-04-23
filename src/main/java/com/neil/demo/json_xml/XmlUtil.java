package com.neil.demo.json_xml;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Neil on 2018/4/19.
 */
public class XmlUtil {


    /**
     * dom4j方式解析xml
     * @param inputStream
     * @return
     * @throws Exception
     */
    public static Map<String, String> parseXml(InputStream inputStream) throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();

        // 从request中取得输入流
//        InputStream inputStream = new StringBufferInputStream(str);
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();

        // 遍历所有子节点
        for (Element e : elementList)
            map.put(e.getName(), e.getText());

        // 释放资源
        inputStream.close();
        inputStream = null;
        return map;
    }

    public static void main(String[] args){
        String xml = "<xml>\n" +
                "        <SuiteId><![CDATA[ww4asffe99e54c0f4c]]></SuiteId>\n" +
                "        <InfoType> <![CDATA[suite_ticket]]></InfoType>\n" +
                "        <TimeStamp>1403610513</TimeStamp>\n" +
                "        <SuiteTicket><![CDATA[asdfasfdasdfasdf]]></SuiteTicket>\n" +
                "    </xml>";

        try {
            Map<String, String> map = parseXml(new ByteArrayInputStream(xml.getBytes("utf-8")));
            for(Map.Entry<String,String> item:map.entrySet()){
                System.out.println(item.getKey()+":"+item.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
