package com.airec.client;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;

public class ClientUtil {

    public final static String BEIJING = "beijing";
    public final static String HANGZHOU = "hangzhou";
    public final static String SHENZHEN = "shenzhen";
    public final static String SHANGHAI = "shanghai";
    public final static String SG = "sg";

    public static Map<String,String> regionMap = Maps.newHashMap();
    public static Map<String,String> endpointMap = Maps.newHashMap();

    public static String instanceId= "xxxxxxx";



    public static String accessId = "xxxxxxxxx";
    public static String accessKey = "xxxxxxxxx";
    public static String env = SG;



    static {
        regionMap.put(BEIJING,"cn-beijing");
        regionMap.put(HANGZHOU,"cn-hangzhou");
        regionMap.put(SHENZHEN,"cn-shenzhen");
        regionMap.put(SHANGHAI,"cn-shanghai");
        regionMap.put(SG,"ap-southeast-1");

        endpointMap.put(BEIJING,"airec.cn-beijing.aliyuncs.com");
        endpointMap.put(HANGZHOU,"airec.cn-hangzhou.aliyuncs.com");
        endpointMap.put(SHENZHEN,"airec.cn-shenzhen.aliyuncs.com");
        endpointMap.put(SHANGHAI,"airec.cn-shanghai.aliyuncs.com");
        endpointMap.put(SG,"airec.ap-southeast-1.aliyuncs.com");

    }


    public static String getInstanceId(){
        return instanceId;
    }

    public static DefaultAcsClient getAcsClient(String accessId,String accessKey,String env){
        // 1.创建 Profile。
        // 生成 IClientProfile 的对象 profile，该对象存放 AccessKeyID 和 AccessKeySecret 和默认的地域信息，如示例中的 cn-hangzhou
        IClientProfile profile = DefaultProfile.getProfile(regionMap.get(env), "accessKeyID", "accessKeySecret");
        // 2.设置 Endpoint。
        // 调用 DefaultProfile 的 addEndpoint 方法，传入 regionId、product 名称、服务接入地址。
        DefaultProfile.addEndpoint("cn-hangzhou", "Airec", endpointMap.get(env));
        // 3.创建 Client。
        // 从 IClientProfile 类中再生成 IAcsClient 的对象 client，后续获得 response 都需要从 IClientProfile 中获得
        return new DefaultAcsClient(profile);
    }

    public static DefaultAcsClient getAcsClient(){
        // 1.创建 Profile。
        // 生成 IClientProfile 的对象 profile，该对象存放 AccessKeyID 和 AccessKeySecret 和默认的地域信息，如示例中的 cn-hangzhou
        IClientProfile profile = DefaultProfile.getProfile(regionMap.get(env), accessId, accessKey);
        // 2.设置 Endpoint。
        // 调用 DefaultProfile 的 addEndpoint 方法，传入 regionId、product 名称、服务接入地址。
        DefaultProfile.addEndpoint(regionMap.get(env), "Airec", endpointMap.get(env));
        // 3.创建 Client。
        // 从 IClientProfile 类中再生成 IAcsClient 的对象 client，后续获得 response 都需要从 IClientProfile 中获得
        return new DefaultAcsClient(profile);
    }


}
