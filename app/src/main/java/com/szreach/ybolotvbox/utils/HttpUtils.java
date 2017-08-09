package com.szreach.ybolotvbox.utils;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtils {
    public static final int METHOD_GET = 1;
    public static final int METHOD_POST = 2;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static String sendRequest(int method, String url, Map<String, Object> params) {
        String retStr = "";
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            if (method == HttpUtils.METHOD_POST) {
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(params);
                RequestBody requestBody = RequestBody.create(JSON, json);
                request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();
            }

            Response response = client.newCall(request).execute();
            retStr = response.body().string();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retStr;
    }

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        // 读取Map转换成JSON
        Map<String, String> fieldMap = new HashMap<String, String>();
        fieldMap.put("videoId", "abcdfg123456");
        fieldMap.put("userName", "Adams.Tsui徐注光");
        String json = mapper.writeValueAsString(fieldMap);
        System.out.println(json);

        // 返回JSON转换成Map
        String str = "{\"code\":1,\"msg\":\"成功\",\"result\":\"201607121644019558\"}";
        Map<String, String> result = mapper.readValue(str, new TypeReference<Map<String, String>>() {
        });

        String ret = HttpUtils.sendRequest(HttpUtils.METHOD_GET, "http://192.168.0.211/rest/AndroidService/getVideoGroup/10001/11", null);
        System.out.println(ret);

    }
}
