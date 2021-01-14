package com.airec.push.document.user;

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

public class PushRecommendUser {

    public static void main(String[] args) {
        pushRecommendUser();
    }

    public static void pushRecommendUser(){
        DefaultAcsClient client = ClientUtil.getAcsClient();


        PushDocumentRequest request = new PushDocumentRequest();
        request.setAcceptFormat(FormatType.JSON);
        //填入实例id
        request.setInstanceId(ClientUtil.getInstanceId());
        //填入要上报的数据表名：user/item/behavior
        request.setTableName("user");

        String content = JSON.toJSONString(buildRecommendUser());
        request.setHttpContent(content.getBytes(), "UTF-8", FormatType.JSON);

        try {
            PushDocumentResponse response = client.getAcsResponse(request);
            System.out.println(response.getResult());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static  List<Map<String,Object>> buildRecommendUser(){
        List<Map<String,Object>> pushList = Lists.newArrayList();
        for(int i=0;i<1000;i++){
            Map<String,Object> pushMap = Maps.newHashMap();
            Map<String,Object> userMap = Maps.newHashMap();
            userMap.put("user_id", "user_id"+i);
            userMap.put("user_id_type", "user_id_type");
            userMap.put("third_user_name", "third_user_name");
            userMap.put("third_user_type", "third_user_type");
            userMap.put("phone_md5", "phone_md5");
            userMap.put("imei", "");
            userMap.put("content", "content");
            userMap.put("gender", "gender");
            userMap.put("age", "1");
            userMap.put("age_group", "age_group");
            userMap.put("country", "country");
            userMap.put("city", "city");
            userMap.put("ip", "ip");
            userMap.put("device_model", "device_model");
            userMap.put("register_time", System.currentTimeMillis()/1000);
            userMap.put("last_login_time", System.currentTimeMillis()/1000);
            userMap.put("last_modify_time", System.currentTimeMillis()/1000);
            userMap.put("tags", "tags");
            userMap.put("source", "source");
            userMap.put("features", "features");
            userMap.put("num_features", "num_features");
            pushMap.put("fields",userMap);
            pushMap.put("cmd","add");
            pushList.add(pushMap);
        }

        return pushList;
    }
}
