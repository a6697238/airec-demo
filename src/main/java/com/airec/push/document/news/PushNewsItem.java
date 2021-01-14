package com.airec.push.document.news;

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

public class PushNewsItem {

    public static void main(String[] args) {
        pushOneItem();
    }

    public static void pushOneItem(){
        DefaultAcsClient client = ClientUtil.getAcsClient();


        PushDocumentRequest request = new PushDocumentRequest();
        request.setAcceptFormat(FormatType.JSON);
        //填入实例id
        request.setInstanceId(ClientUtil.getInstanceId());
        //填入要上报的数据表名：user/item/behavior
        request.setTableName("item");

        String content = JSON.toJSONString(buildOneNewsItem());
        request.setHttpContent(content.getBytes(), "UTF-8", FormatType.JSON);

        try {
            PushDocumentResponse response = client.getAcsResponse(request);
            System.out.println(response.getResult());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static  List<Map<String,Object>> buildOneNewsItem(){
        List<Map<String,Object>> pushList = Lists.newArrayList();
        Map<String,Object> pushMap = Maps.newHashMap();
        Map<String,Object> itemMap = Maps.newHashMap();

        itemMap.put("item_id","item_id1");
        itemMap.put("item_type", "image");
        itemMap.put("title", "title");
        itemMap.put("content", "content");
        itemMap.put("user_id", "user_id1");
        itemMap.put("pub_time", System.currentTimeMillis()/1000);
        itemMap.put("status", 1);
        itemMap.put("expire_time", System.currentTimeMillis()/1000+3600*24);
        itemMap.put("last_modify_time", System.currentTimeMillis()/1000);
        itemMap.put("scene_id", "scene_id1");
        itemMap.put("duration", "10");
        itemMap.put("category_level", "2");
        itemMap.put("category_path", "1_1");
        itemMap.put("tags", "tags");
        itemMap.put("channel", "channel");
        itemMap.put("organization", "organization");
        itemMap.put("author", "author");
        itemMap.put("pv_cnt", "1");
        itemMap.put("click_cnt", "1");
        itemMap.put("like_cnt", "1");
        itemMap.put("unlike_cnt", "1");
        itemMap.put("comment_cnt", "1");
        itemMap.put("collect_cnt", "1");
        itemMap.put("share_cnt", "1");
        itemMap.put("download_cnt", "1");
        itemMap.put("tip_cnt", "1");
        itemMap.put("subscribe_cnt", "1");
        itemMap.put("source_id", "1");
        itemMap.put("country", "country");
        itemMap.put("city", "city");
        itemMap.put("features", "features");
        itemMap.put("num_features", "num_features");
        pushMap.put("fields",itemMap);
        pushMap.put("cmd","add");
        pushList.add(pushMap);
        return pushList;
    }
}
