package com.airec.recommend;

import com.airec.client.ClientUtil;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.airec.model.v20181012.RecommendRequest;
import com.aliyuncs.airec.model.v20181012.RecommendResponse;
import com.aliyuncs.http.FormatType;
import lombok.SneakyThrows;

public class RecommendQps {

    public static void main(String[] args) {
        recommendQps(10);
    }

    @SneakyThrows
    public static void recommendQps(int qps) {
        DefaultAcsClient client = ClientUtil.getAcsClient();

        while (true){
            RecommendRequest request = new RecommendRequest();
            request.setInstanceId(ClientUtil.getInstanceId());
            request.setUserId("10");
            request.setReturnCount(10);
            request.setSceneId("1");
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
            Thread.sleep(1000/ (qps));

        }
    }


}
