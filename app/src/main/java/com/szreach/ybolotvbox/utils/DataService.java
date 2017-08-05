package com.szreach.ybolotvbox.utils;

import com.szreach.ybolotvbox.beans.VideoBean;
import com.szreach.ybolotvbox.beans.VodGroupBean;
import com.szreach.ybolotvbox.jsonMsg.ResultItem;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Adams.Tsui on 2017/7/24 0024.
 */

public class DataService {
    public static final String URL_PREFIX = Constant.DataServerAdress + "/rest/AndroidService";
    public static final String URL_GET_VIDEOGROUP_LIST = "/getVideoGroup";
    public static final String URL_GET_VIDEO_LIST = "/getVideoList";

    private static final ObjectMapper mapper = new ObjectMapper();

    private static DataService ins;

    private DataService() {
    }

    public static DataService getInstance() {
        if (ins == null) {
            ins = new DataService();
        }
        return ins;
    }

    public ArrayList<VodGroupBean> getVodGroupList() {
        ArrayList<VodGroupBean> vodGroupBeanList = new ArrayList<VodGroupBean>();
        String urlPrefix = URL_PREFIX + URL_GET_VIDEOGROUP_LIST + "/10001/";
        String url = urlPrefix + "0";
        String retStr = HttpUtils.sendRequest(HttpUtils.METHOD_GET, url, null);

        try {
            ResultItem<ArrayList<VodGroupBean>> resultItem = mapper.readValue(retStr, new TypeReference<ResultItem<ArrayList<VodGroupBean>>>() {
            });
            if (resultItem.getMsgHeader().isResult() && resultItem.getData() != null && resultItem.getData().size() > 0) {
                for (VodGroupBean group : resultItem.getData()) {
                    url = urlPrefix + group.getGroupId();
                    retStr = HttpUtils.sendRequest(HttpUtils.METHOD_GET, url, null);
                    ResultItem<ArrayList<VodGroupBean>> resultItem_ = mapper.readValue(retStr, new TypeReference<ResultItem<ArrayList<VodGroupBean>>>() {
                    });
                    if(resultItem_.getMsgHeader().isResult() && resultItem_.getData() != null && resultItem_.getData().size() > 0) {
                        vodGroupBeanList.addAll(resultItem_.getData());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        VodGroupBean vodGroup = new VodGroupBean();
        vodGroup.setCoId(10001);
        vodGroup.setGroupName("全        部");
        vodGroup.setGroupId(0);
        vodGroupBeanList.add(0, vodGroup);
        return vodGroupBeanList;
    }

    public ArrayList<VideoBean> getVideopListByGroupId(long groupId) {
        ArrayList<VideoBean> videoList = new ArrayList<VideoBean>();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("coId", 10001);
        if(groupId == 0) {
            params.put("groupId", null);
        } else {
            params.put("groupId", groupId);
        }
        params.put("pageNumber", 0);
        params.put("pageSize", Integer.MAX_VALUE);

        String url = URL_PREFIX + URL_GET_VIDEO_LIST;
        String retStr = HttpUtils.sendRequest(HttpUtils.METHOD_POST, url, params);

        try {
            ResultItem<ArrayList<VideoBean>> resultItem = mapper.readValue(retStr, new TypeReference<ResultItem<ArrayList<VideoBean>>>() {
            });
            if(resultItem.getMsgHeader().isResult() && resultItem.getData() != null && resultItem.getData().size() > 0) {
                videoList.addAll(resultItem.getData());
            }

        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return videoList;
    }






    public static void main(String[] args) {
//        DataService.getInstance().getVodGroupList();
        DataService.getInstance().getVideopListByGroupId(0);
    }
}
