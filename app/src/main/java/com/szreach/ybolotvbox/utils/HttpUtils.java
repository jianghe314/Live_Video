package com.szreach.ybolotvbox.utils;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtils {
    public static Map<String, String> sendRequest() throws IOException {
        OkHttpClient client = new OkHttpClient();
        String url = "http://baidu.com";
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        response.body().string();

        ObjectMapper mapper = new ObjectMapper();

        // 读取Map转换成JSON
        Map<String, String> fieldMap = new HashMap<String, String>();
        fieldMap.put("videoId", "abcdfg123456");
        fieldMap.put("userName", "Adams.Tsui徐注光");
        String json = mapper.writeValueAsString(fieldMap);
        System.out.println(json);

        // 返回JSON转换成Map
        String str = "{\"code\":1,\"msg\":\"成功\",\"result\":\"201607121644019558\"}";
        Map<String, String> result = mapper.readValue(str, new TypeReference<Map<String, String>>() {});

        return result;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(sendRequest().get("result"));
    }
}
