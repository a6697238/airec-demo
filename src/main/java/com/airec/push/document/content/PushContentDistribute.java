package com.airec.push.document.content;

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
 * 测试内容行业物品质量分布功能
 * <p>
 * 总共推送1000条数据
 */
public class PushContentDistribute {

    public static void main(String[] args) {
        pusDistributeContent();
    }

    public static void pusDistributeContent() {
        DefaultAcsClient client = ClientUtil.getAcsClient();


        PushDocumentRequest request = new PushDocumentRequest();
        request.setAcceptFormat(FormatType.JSON);
        //填入实例id
        request.setInstanceId(ClientUtil.getInstanceId());
        //填入要上报的数据表名：user/item/behavior
        request.setTableName("item");
        List<List<Map<String, Object>>> res = builContentItemDistribute();
        for (List<Map<String, Object>> list : res) {
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

    public static List<List<Map<String, Object>>> builContentItemDistribute() {
        String[] scene = new String[]{"scene_id1", "scene_id2"};
        String[] type = new String[]{"image", "item", "recipe", "shortvideo", "video", "audio", "article"};
        String[] content = new String[]{"There are moments in life when you miss someone so much that you just want " +
                "to pick them from your dreams and hug them for real! Dream what you want to dream;go where you want " +
                "to go;be what you want to be,because you have only one life and one chance to do all the things you " +
                "want to do", "May you have enough happiness to make you sweet,enough trials to make you strong," +
                "enough sorrow to keep you human,enough hope to make you happy? Always put yourself in others’shoes" +
                ".If you feel that it hurts you,it probably hurts the other person, too", "The happiest of people " +
                "don’t necessarily have the best of everything;they just make the most of everything that comes along" +
                " their way.Happiness lies for those who cry,those who hurt, those who have searched,and those who " +
                "have tried,for only they can appreciate the importance of people", "who have touched their lives" +
                ".Love begins with a smile,grows with a kiss and ends with a tear.The brightest future will always be" +
                " based on a forgotten past, you can’t go on well in lifeuntil you let go of your past failures and " +
                "heartaches.",
                "When you were born,you were crying and everyone around you was smiling.Live your life so that when " +
                        "you die,you're the one who is smiling and everyone around you is crying.", "Please send this" +
                " message to those people who mean something to you,to those who have touched your life in one way or" +
                " another,to those who make you smile when you really need it,to those that make you see the brighter" +
                " side of things when you are really down", "to those who you want to let them know that you " +
                "appreciate their friendship.And if you don’t, don’t worry,nothing bad will happen to you,you will " +
                "just miss out on the opportunity to brighten someone’s day with this message."};
        String[] title = new String[]{"The power of smiles", "The meaning of life", "Value every minute", "Never give" +
                " up", "The greatest pain in life", "Yesterday, today and tomorrow", "Love is understanding"};

        List<List<Map<String, Object>>> resList = Lists.newArrayList();
        for (int i = 0; i < 7; i++) {
            List<Map<String, Object>> pushList = Lists.newArrayList();
            for (int j = 0; j < 1000; j++) {
                Map<String, Object> pushMap = Maps.newHashMap();
                Map<String, Object> itemMap = Maps.newHashMap();
                itemMap.put("item_id", "item_id" + j);
                itemMap.put("item_type", type[i]);
                itemMap.put("title", title[j%7]);
                itemMap.put("content", content[(i+j)%7]);
                itemMap.put("user_id", "user_id" + j);
                itemMap.put("pub_time", System.currentTimeMillis() / 1000 + (3600 * 24) * (10 - j % 20));
                itemMap.put("status", 1);
                itemMap.put("expire_time", System.currentTimeMillis() / 1000 + (3600 * 24) * (5 - j % 10));
                itemMap.put("last_modify_time", System.currentTimeMillis() / 1000);
                itemMap.put("scene_id", scene[j % 2]);
                itemMap.put("duration", "10");
                itemMap.put("category_level", "3");
                itemMap.put("category_path", i + "_" + j % 10 + "_" + j % 10);
                itemMap.put("tags", "tag" + j % 10);
                itemMap.put("channel", "channel" + j % 10);
                itemMap.put("organization", "organization" + j % 10);
                itemMap.put("author", "user_id" + j % 10);
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
                itemMap.put("country", "country" + j % 10);
                itemMap.put("city", "city" + j % 10);
                itemMap.put("features", "features");
                if(j%2==0){
                    itemMap.put("weight", 1);
                }else {
                    itemMap.put("weight", 100);
                }
                itemMap.put("num_features", "num_features");
                pushMap.put("fields", itemMap);
                pushMap.put("cmd", "add");
                pushList.add(pushMap);
            }
            resList.add(pushList);
        }

        return resList;
    }
}
