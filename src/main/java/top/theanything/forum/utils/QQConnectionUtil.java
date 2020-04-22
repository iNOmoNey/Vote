package top.theanything.forum.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.Getter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.IOException;

/**
 * @author zhou
 * @version 1.0.0
 * @ClassName QQConnection.java
 * @Description
 * @createTime 2020年04月18日 15:44:00
 */


public class QQConnectionUtil {

    private static final String QQAppId = "101813698";
    private static final String QQ_APPKEY = "7265da646c6cf33f6326f6844c1c4ef3";
    private static final String QQ_REDIRECT_URI = "http%3A%2F%2F127.0.0.1%3A8080%2Flogin_qq" ;


    //登录页会回访问服务器并带回Authorization Code，再通过Authorization Code获取Access Token
    public static String getAccessToken(String code){
        String accessToken="";
        String url = "https://graph.qq.com/oauth2.0/token?display=pc&grant_type=authorization_code&client_id="
                +QQAppId+"&client_secret="+QQ_APPKEY+"&redirect_uri="+QQ_REDIRECT_URI+"&code="+code;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            assert response.body() != null;
            String responseString = response.body().string();
            accessToken = responseString.split("=")[1].split("&")[0];
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accessToken;
    }

    //通过accessToken获取用户的OpenId   {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"}
    public static JSONObject getUserOpenID(String accessToken) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String url = "https://graph.qq.com/oauth2.0/me?access_token="+accessToken;
        Request request = new Request.Builder()
                .url(url)
                .build();

        String response = client.newCall(request).execute().body().string();
        String json = response.split(" ")[1];
        JSONObject object = (JSONObject) JSONObject.parse(json);
       return object;
    }


    //访问腾讯get_user_info接口
    public static JSONObject getUserInfo(String accessToken, String client_id, String openid) throws IOException {
        String url = "https://graph.qq.com/user/get_user_info?access_token="+accessToken+"&oauth_consumer_key="+client_id+"&openid="+openid;

        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        String response = httpClient.newCall(request).execute().body().string();
        JSONObject user_info = JSONObject.parseObject(response);
        return user_info;
    }
}
