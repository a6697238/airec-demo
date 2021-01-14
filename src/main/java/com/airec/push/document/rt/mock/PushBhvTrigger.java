package com.airec.push.document.rt.mock;

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

public class PushBhvTrigger {

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
        List<List<Map<String, Object>>> pushList = buildRecommendBhv("user_id1");
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

    public static List<List<Map<String, Object>>> buildRecommendBhv(String userId) {
        String[] scene = new String[]{"scene_id1", "scene_id2"};
        String[] type = new String[]{"image", "item", "recipe", "shortvideo", "video", "audio", "article"};
        List<List<Map<String, Object>>> pushList = Lists.newArrayList();
        List<Map<String, Object>> recordList = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            Map<String, Object> pushMap = Maps.newHashMap();
            Map<String, Object> bhvMap = Maps.newHashMap();
            bhvMap.put("trace_id", "selfhold");
            bhvMap.put("trace_info", "");
            bhvMap.put("platform", "platform");
            bhvMap.put("device_model", "device_model");
            bhvMap.put("app_version", "app_version");
            bhvMap.put("net_type", "net_type");
            bhvMap.put("longitude", "longitude");
            bhvMap.put("latitude", "latitude");
            bhvMap.put("ip", "ip");
            bhvMap.put("scene_id", "scene_id1");
            bhvMap.put("login", "login");
            bhvMap.put("report_src", "report_src");
            bhvMap.put("user_id", userId);
            bhvMap.put("item_id", "item_id" + i);
            bhvMap.put("item_type", "image");
            bhvMap.put("module_id", "module_id");
            bhvMap.put("page_id", "page_id");
            bhvMap.put("position", "position");
            if(i%10==0){
                bhvMap.put("bhv_type", "dislike");
                bhvMap.put("bhv_value", "dislike_class:cagetory_path=12_4_5");
            }if(i%7==0){
                bhvMap.put("bhv_type", "dislike");
                bhvMap.put("bhv_value", "dislike_related");
            }else {
                bhvMap.put("bhv_type", "click");
                bhvMap.put("bhv_value", "1");
            }
            bhvMap.put("bhv_time", System.currentTimeMillis() / 1000);
            pushMap.put("fields", bhvMap);
            pushMap.put("cmd", "add");
            recordList.add(pushMap);
        }
        pushList.add(recordList);
        return pushList;
    }
}
