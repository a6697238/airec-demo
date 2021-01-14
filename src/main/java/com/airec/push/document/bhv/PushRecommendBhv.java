package com.airec.push.document.bhv;

import com.airec.client.ClientUtil;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.airec.model.v20181012.PushDocumentRequest;
import com.aliyuncs.airec.model.v20181012.PushDocumentResponse;
import com.aliyuncs.http.FormatType;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * 构造历史用户行为
 * <p>
 * user_id  分别对 一些 item 有曝光，点击等行为
 */
public class PushRecommendBhv {

    public static void main(String[] args) {
        pushRecommendBhv();
    }

    public static void pushRecommendBhv() {
        DefaultAcsClient client = ClientUtil.getAcsClient();


        PushDocumentRequest request = new PushDocumentRequest();
        request.setAcceptFormat(FormatType.JSON);
        //填入实例id
        request.setInstanceId(ClientUtil.getInstanceId());
        //填入要上报的数据表名：user/item/behavior
        request.setTableName("behavior");
        List<List<Map<String, Object>>> pushList = buildRecommendBhv();
        for (List<Map<String, Object>> list : pushList) {
            String content = JSON.toJSONString(list);
            request.setHttpContent(content.getBytes(), "UTF-8", FormatType.JSON);
            try {
                PushDocumentResponse response = client.getAcsResponse(request);
                System.out.println(response.getResult());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static List<List<Map<String, Object>>> buildRecommendBhv() {
        String[] scene = new String[]{"scene_id1", "scene_id2"};
        String[] type = new String[]{"image", "item", "recipe", "shortvideo", "video", "audio", "article"};
        List<List<Map<String, Object>>> pushList = Lists.newArrayList();
        for (int i = 0; i < 1000; i++) {
            List<Map<String, Object>> recordList = Lists.newArrayList();
            for (int j = 0; j < 100; j++) {
                Map<String, Object> pushMap = Maps.newHashMap();
                Map<String, Object> bhvMap = Maps.newHashMap();
                bhvMap.put("trace_id", "selfhold");
                bhvMap.put("trace_info", "");
                bhvMap.put("platform", "platform" + j % 2);
                bhvMap.put("device_model", "device_model" + j % 2);
                bhvMap.put("app_version", "app_version" + j % 10);
                bhvMap.put("net_type", "net_type");
                bhvMap.put("longitude", "longitude");
                bhvMap.put("latitude", "latitude");
                bhvMap.put("ip", "ip");
                bhvMap.put("login", "login");
                bhvMap.put("report_src", "report_src");
                bhvMap.put("scene_id", scene[j % 2]);
                bhvMap.put("user_id", "user_id" + i);
                bhvMap.put("item_id", "item_id" + j);
                bhvMap.put("item_type", type[(i + j) % 7]);
                bhvMap.put("module_id", "module_id");
                bhvMap.put("page_id", "page_id");
                bhvMap.put("position", "position");
                if (j % 10 == 0) {
                    bhvMap.put("bhv_type", "click");
                } else {
                    bhvMap.put("bhv_type", "expose");
                }
                bhvMap.put("bhv_value", "1");
                bhvMap.put("bhv_time", System.currentTimeMillis() / 1000 + j);
                pushMap.put("fields", bhvMap);
                pushMap.put("cmd", "add");
                recordList.add(pushMap);
            }
            pushList.add(recordList);
        }
        return pushList;
    }
}
