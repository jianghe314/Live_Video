package com.szreach.ybolotvbox.utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Adams.Tsui on 2017/8/9 0009.
 */

public class StoreObjectUtils {
    final public static String SP_VOD_HISTORY = "sp_vod_history";
    final public static String DATA_VOD_HISTORY = "data_vod_history";

    final public static String SP_Plat = "sp_plat";
    final public static String DATA_Plat_Address = "data_plat_address";


    private Context context;
    private String spName;

    private static final ObjectMapper mapper = new ObjectMapper();
    private final SharedPreferences sp;

    public StoreObjectUtils(Context context, String spName) {
        this.context = context;
        this.spName = spName;
        sp = this.context.getSharedPreferences(this.spName, Context.MODE_PRIVATE);
    }

    public <T> void saveObject(String key, T data) {
        try {
            String json = mapper.writeValueAsString(data);
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            editor.putString(key, json);
            editor.commit();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> List<T> getList(String key, Class<T> clazz) {
        List<T> ret = null;
        try {
            String json = sp.getString(key, null);
            if (json != null && json.length() > 0) {
                ret = mapper.readValue(json, getCollectionType(ArrayList.class, clazz));
            }
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public <K, T> Map<K, T> getMap(String key, Class<K> clazz1, Class<T> clazz2) {
        Map<K, T> ret = null;
        try {
            String json = sp.getString(key, null);
            if(json != null && json.length() > 0) {
                ret = mapper.readValue(json, getCollectionType(HashMap.class, clazz1, clazz2));
            }
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    private JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    public String getString(String key) {
        String ret = null;
        String str = sp.getString(key, null);
        if(str != null && str.length() > 0) {
            ret = str.replace("\"", "");
        }
        if(ret != null && ret.equals("null")) {
            ret = null;
        }
        return ret;
    }

    public int getInt(String key) {
        return sp.getInt(key, 0);
    }

    public float getFloat(String key) {
        return sp.getFloat(key, 0f);
    }

    public boolean getBoolean(String key) {
        return sp.getBoolean(key, false);
    }
}
