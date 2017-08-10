package com.szreach.ybolotvbox.utils;

import com.szreach.ybolotvbox.beans.LiveBean;
import com.szreach.ybolotvbox.beans.NewsBean;
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
    public static final String URL_PREFIX = "/rest/AndroidService";
    public static final String URL_GET_LIVE_LIST = "/getLiveList";
    public static final String URL_GET_LIVE = "/getLive";
    public static final String URL_GET_GROUP_LIST = "/getVideoGroup";
    public static final String URL_GET_VIDEO_LIST = "/getVideoList";
    public static final String URL_GET_VIDEO_INFO = "/getVideoInfo";
    public static final String URL_GET_NEWS_LIST = "/getNewsList";
    public static final String URL_GET_NEWS_URL = "/getNewsNnUrl";

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

    /**
     * 获取直播列表
     * @return
     */
    public ArrayList<LiveBean> getLiveList() {
        ArrayList<LiveBean> liveBeanArrayList = new ArrayList<LiveBean>();
        String urlPrefix = Constant.DataServerAdress + URL_PREFIX + URL_GET_LIVE_LIST;
        String url = urlPrefix + "/10001";

        try {
            String retStr = HttpUtils.sendRequest(HttpUtils.METHOD_GET, url, null);
            ResultItem<ArrayList<LiveBean>> resultItem = mapper.readValue(retStr, new TypeReference<ResultItem<ArrayList<LiveBean>>>() {
            });
            if (resultItem.getMsgHeader().isResult() && resultItem.getData() != null && resultItem.getData().size() > 0) {
                liveBeanArrayList = resultItem.getData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return liveBeanArrayList;
    }

    /**
     * 获取直播详细信息
     * @param coId
     * @param liveId
     * @return
     */
    public LiveBean getLive(long coId, String liveId) {
        LiveBean live = null;
        String urlPrefix = Constant.DataServerAdress + URL_PREFIX + URL_GET_LIVE;
        String url = urlPrefix + "/" + coId + "/" + liveId;

        try {
            String retStr = HttpUtils.sendRequest(HttpUtils.METHOD_GET, url, null);
            ResultItem<LiveBean> resultItem = mapper.readValue(retStr, new TypeReference<ResultItem<LiveBean>>() {
            });
            if (resultItem.getMsgHeader().isResult() && resultItem.getData() != null) {
                live = resultItem.getData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return live;
    }

    /**
     * 获取视频分组
     * @return
     */
    public ArrayList<VodGroupBean> getVodGroupList() {
        ArrayList<VodGroupBean> vodGroupBeanList = new ArrayList<VodGroupBean>();
        String urlPrefix = Constant.DataServerAdress + URL_PREFIX + URL_GET_GROUP_LIST + "/10001/";
        String url = urlPrefix + "0";

        try {
            String retStr = HttpUtils.sendRequest(HttpUtils.METHOD_GET, url, null);
            ResultItem<ArrayList<VodGroupBean>> resultItem = mapper.readValue(retStr, new TypeReference<ResultItem<ArrayList<VodGroupBean>>>() {
            });
            if (resultItem.getMsgHeader().isResult() && resultItem.getData() != null && resultItem.getData().size() > 0) {
                for (VodGroupBean group : resultItem.getData()) {
                    url = urlPrefix + group.getGroupId();
                    retStr = HttpUtils.sendRequest(HttpUtils.METHOD_GET, url, null);
                    ResultItem<ArrayList<VodGroupBean>> resultItem_ = mapper.readValue(retStr, new TypeReference<ResultItem<ArrayList<VodGroupBean>>>() {
                    });
                    if (resultItem_.getMsgHeader().isResult() && resultItem_.getData() != null && resultItem_.getData().size() > 0) {
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

    /**
     * 获取视频列表
     * @param groupId
     * @return
     */
    public ArrayList<VideoBean> getVideopListByGroupId(long groupId) {
        ArrayList<VideoBean> videoList = new ArrayList<VideoBean>();
        String url = Constant.DataServerAdress + URL_PREFIX + URL_GET_VIDEO_LIST;

        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("coId", 10001);
            if (groupId == 0) {
                params.put("groupId", null);
            } else {
                params.put("groupId", groupId);
            }
            params.put("pageNumber", 0);
            params.put("pageSize", Integer.MAX_VALUE);

            String retStr = HttpUtils.sendRequest(HttpUtils.METHOD_POST, url, params);
            ResultItem<ArrayList<VideoBean>> resultItem = mapper.readValue(retStr, new TypeReference<ResultItem<ArrayList<VideoBean>>>() {
            });
            if (resultItem.getMsgHeader().isResult() && resultItem.getData() != null && resultItem.getData().size() > 0) {
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

    /**
     * 获取视频播放地址
     * @param coId
     * @param videoId
     * @return
     */
    public HashMap<String, String> getVideoPlayPath(long coId, String videoId) {
        HashMap<String, String> ret = null;
        String url = Constant.DataServerAdress + URL_PREFIX + URL_GET_VIDEO_INFO + "/" + coId + "/" + videoId;

        try {
            String retStr = HttpUtils.sendRequest(HttpUtils.METHOD_GET, url, null);
            ResultItem<HashMap<String, String>> resultItem = mapper.readValue(retStr, new TypeReference<ResultItem<HashMap<String, String>>>() {
            });
            if (resultItem.getMsgHeader().isResult() && resultItem.getData() != null && resultItem.getData().size() > 0) {
                ret = resultItem.getData();
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

    /**
     * 获取新闻列表
     * @return
     */
    public ArrayList<NewsBean> getNewsList() {
        ArrayList<NewsBean> newsList = new ArrayList<NewsBean>();
        String url = Constant.DataServerAdress + URL_PREFIX + URL_GET_NEWS_LIST;

        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("coId", 10001);
            params.put("pageNumber", 0);
            params.put("pageSize", Integer.MAX_VALUE);

            String retStr = HttpUtils.sendRequest(HttpUtils.METHOD_POST, url, params);
            ResultItem<ArrayList<NewsBean>> resultItem = mapper.readValue(retStr, new TypeReference<ResultItem<ArrayList<NewsBean>>>() {
            });
            if (resultItem.getMsgHeader().isResult() && resultItem.getData() != null && resultItem.getData().size() > 0) {
                newsList = resultItem.getData();
            }

        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newsList;
    }

    /**
     * 获取新闻地址
     * @param coId
     * @param nnId
     * @return
     */
    public String getNewsUrl(long coId, String nnId) {
        String ret = null;
        String url = Constant.DataServerAdress + URL_PREFIX + URL_GET_NEWS_URL + "/" + coId + "/" + nnId;

        try {
            String retStr = HttpUtils.sendRequest(HttpUtils.METHOD_GET, url, null);
            ResultItem<Map<String, String>> resultItem = mapper.readValue(retStr, new TypeReference<ResultItem<Map<String, String>>>() {
            });
            if (resultItem.getMsgHeader().isResult() && resultItem.getData() != null && resultItem.getData().size() > 0) {
                ret = resultItem.getData().get("pcUrl");
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

    /**
     * 获取首页新闻
     * @return
     */
    public NewsBean getMainNews() {
        NewsBean news = null;
        String url = Constant.DataServerAdress + URL_PREFIX + URL_GET_NEWS_LIST;

        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("coId", 10001);
            params.put("pageNumber", 0);
            params.put("pageSize", 1);

            String retStr = HttpUtils.sendRequest(HttpUtils.METHOD_POST, url, params);

            ResultItem<ArrayList<NewsBean>> resultItem = mapper.readValue(retStr, new TypeReference<ResultItem<ArrayList<NewsBean>>>() {
            });
            if (resultItem.getMsgHeader().isResult() && resultItem.getData() != null && resultItem.getData().size() > 0) {
                news = resultItem.getData().get(0);
            }

        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return news;
    }

    public static void main(String[] args) {
//        DataService.getInstance().getVodGroupList();
//        DataService.getInstance().getVideopListByGroupId(0);
//        DataService.getInstance().getLiveList();
//        DataService.getInstance().getNewsList();
    }

}
