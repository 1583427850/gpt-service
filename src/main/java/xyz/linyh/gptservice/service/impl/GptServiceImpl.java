package xyz.linyh.gptservice.service.impl;

import okhttp3.HttpUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import xyz.linyh.gptservice.config.GPTProperties;
import xyz.linyh.gptservice.entitys.GPTBody;
import xyz.linyh.gptservice.entitys.GPTMessage;
import xyz.linyh.gptservice.entitys.GPTResponse;
import xyz.linyh.gptservice.service.GptService;

import javax.annotation.Resource;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import  java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class GptServiceImpl implements GptService {

    private final String ADDRESS_PRE = "https://";

    private final String ADDRESS_AFTER = "/v1/chat/completions";

    @Autowired
    private GPTProperties properties;


    @Resource
    RestTemplate restTemplate;

//    请求如果出现错误返回优化 todo
    @Override
    public List<GPTMessage> sendRequest(List<GPTMessage> messages) {

//        插入初始化配置
        GPTBody gptBody = new GPTBody.Builder()
                .messages(messages)
                .model(properties.getModel())
                .temperature(properties.getTemperature())
                .stream(properties.getIsStream())
                .build();

        HttpEntity<GPTBody> httpEntity = new HttpEntity<>(gptBody);
//        在配置里面配置了通过resTemplate发送请求可以添加apikey请求头
        try {
            ResponseEntity<GPTResponse> responseEntity = restTemplate.postForEntity(requestURL(), httpEntity, GPTResponse.class);

            HttpStatus statusCode = responseEntity.getStatusCode();

            if(statusCode == HttpStatus.OK){
                GPTResponse response = responseEntity.getBody();
                GPTMessage message = response.getChoices().get(0).getMessage();
                System.out.println(message);

//                将请求后的结果保存到messages中，然后返回
            messages.add(message);
            return messages;
            }else{
                System.out.println("请求相应出现错误");
            }
        } catch (RestClientException e) {
            System.out.println(e);
            System.out.println("发送请求出错");
        }
//        如果发送异常，那么就直接将gpt回复设置为空的然后返回给前端
        GPTMessage message = new GPTMessage();
        message.setRole("assistant");
        message.setContent("");
        messages.add(message);
        return messages;
    }


    public String requestURL(){
        String host = properties.getHost();

        String requestAddress = ADDRESS_PRE+host+ADDRESS_AFTER;

        return requestAddress;
    }

    /**
     *
     * @param hostUrl 请求接口的地址
     * @param apiKey    用户apikey
     * @param apiSecret 用户apiSecret
     * @return
     * @throws Exception
     */
    public String getAuthUrl(String hostUrl,String apiKey,String apiSecret) throws Exception{

        URL url = new URL(hostUrl);
        //        获取date
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.CHINA);
        String date = format.format(new Date());

        String key = properties.getApiKey();
        String secret = properties.getApiSecret();

        // 拼接
        String preStr = "host: " + url.getHost() + "\n" +
                "date: " + date + "\n" +
                "GET " + url.getPath() + " HTTP/1.1";

        // SHA256加密
        Mac mac = Mac.getInstance("hmacsha256");
        SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), "hmacsha256");
        mac.init(spec);

        byte[] hexDigits = mac.doFinal(preStr.getBytes(StandardCharsets.UTF_8));
        // Base64加密
        String sha = Base64.getEncoder().encodeToString(hexDigits);

        String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", key, "hmac-sha256", "host date request-line", sha);

        // 拼接地址
        HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse("https://" + url.getHost() + url.getPath())).newBuilder()
                .addQueryParameter("authorization", Base64.getEncoder().encodeToString(authorization.getBytes(StandardCharsets.UTF_8)))
                .addQueryParameter("date", date).
                addQueryParameter("host", url.getHost())
                .build();

//        返回认证后的url
        return httpUrl.toString();
    }
}
