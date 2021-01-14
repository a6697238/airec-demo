package com.airec.push.document.item;

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

public class PushOneItem {

    public static void main(String[] args) {
        pushOneItem();
    }

    public static void pushOneItem() {
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

    public static List<Map<String, Object>> buildOneNewsItem() {
        List<Map<String, Object>> pushList = Lists.newArrayList();
        Map<String, Object> pushMap = Maps.newHashMap();
        Map<String, Object> itemMap = Maps.newHashMap();

        itemMap.put("scene_id", "scene_id1");
        itemMap.put("item_id", "new_element_001");
        itemMap.put("item_type", "title");
        itemMap.put("category_level", "2");
        itemMap.put("category_path", "1_1");
        itemMap.put("title", "title");
        itemMap.put("content", "content");
        itemMap.put("pub_time", System.currentTimeMillis() / 1000);
        itemMap.put("tags", "tag_update");
        itemMap.put("share_cnt", "1");
        itemMap.put("collect_cnt", "1");
        itemMap.put("pv_cnt", "1");
        itemMap.put("status", 1);
        itemMap.put("expire_time", System.currentTimeMillis() / 1000 + (3600 * 24));
        itemMap.put("last_modify_time", System.currentTimeMillis() / 1000);
        itemMap.put("origin_price", "");
        itemMap.put("cur_price", "");
        itemMap.put("buy_cnt", "");
        itemMap.put("source_buy_cnt", "");
        itemMap.put("comment_cnt", "");
        itemMap.put("brand_id", "brand_id");
        itemMap.put("shop_id", "shop_id");
        itemMap.put("source_id", "1");
        itemMap.put("add_fee", "");
        itemMap.put("features", "features");
        itemMap.put("num_features", "num_features");
        itemMap.put("weight", 1);
        pushMap.put("fields", itemMap);
        pushMap.put("cmd", "add");
        pushList.add(pushMap);
        return pushList;
    }
}
