package priv.alisa.community.provider;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;
import priv.alisa.community.dto.Accesstoken;
import priv.alisa.community.dto.GithubUser;

import java.io.IOException;


@Component
public class GithubProvider {
    public String getAccesstoken(Accesstoken accesstoken){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON.toJSONString(accesstoken),mediaType);
        Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String res = response.body().string();
            //res:access_token=b19543b5691e56f51f8728bf43d20d5a0b09232a&scope=user&token_type=bearer
            //截取&左边，=右边的access_token值
            String token = res.split("&")[0].split("=")[1];
            System.out.println(token);
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accesstoken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accesstoken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String res = response.body().string();
            System.out.println(res);
            //把string的json对象解析成GithubUser对象
            GithubUser githubUser = JSON.parseObject(res, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
