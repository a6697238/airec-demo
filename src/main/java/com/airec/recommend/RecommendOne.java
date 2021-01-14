package com.airec.recommend;

import com.airec.client.ClientUtil;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.airec.model.v20181012.PushDocumentRequest;
import com.aliyuncs.airec.model.v20181012.PushDocumentResponse;
import com.aliyuncs.airec.model.v20181012.RecommendRequest;
import com.aliyuncs.airec.model.v20181012.RecommendResponse;
import com.aliyuncs.http.FormatType;

import java.util.List;
import java.util.Map;

public class RecommendOne {

    public static void main(String[] args) {
        recommendOne();
    }

    public static void recommendOne() {
        DefaultAcsClient client = ClientUtil.getAcsClient();

        RecommendRequest request = new RecommendRequest();
        request.setInstanceId(ClientUtil.getInstanceId());
        request.setUserId("user_id1");
        request.setReturnCount(10);
        request.setSceneId("scene_id1");
//        request.setItems();
        request.setAcceptFormat(FormatType.JSON);
        try {
            RecommendResponse response = client.getAcsResponse(request);
            for (RecommendResponse.ResultItem item : response.getResult()) {
                System.out.println(item.getItemId());
                System.out.println(item.getItemType());
                System.out.println(item.getTraceId());
                System.out.println(item.getTraceInfo());
                System.out.println(item.getMatchInfo());
                System.out.println(item.getWeight());
                System.out.println(item.getPosition());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
