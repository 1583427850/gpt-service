package xyz.linyh.gptservice.config;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import xyz.linyh.gptservice.entitys.GPTBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 将properties添加到容器里面，然后注入这里面
 * @author lin
 */
@Configuration
@EnableConfigurationProperties(GPTProperties.class)
@Slf4j
public class GPTConfig {

    private final GPTProperties gptProperties;

    public GPTConfig(GPTProperties gptProperties){
        this.gptProperties = gptProperties;
        System.out.println(gptProperties.getModel());
    }

//    @Bean
//    public HttpRequest initRequest(){
//        String host = gptProperties.getHost();
//        String model = gptProperties.getModel();
//        Boolean isStream = gptProperties.getIsStream();
//        double temperature = gptProperties.getTemperature();
//
//
//        String requestHost = "https://"+host+"/v1/chat/completions";
//
//        HttpRequest request = HttpRequest.post(requestHost).addHeaders(getHeaders());
//        GPTBody gptBody = new GPTBody();
//        gptBody.setStream(isStream);
//        gptBody.setModel(model);
//        gptBody.setTemperature(temperature);
//        return request;
//    }
//
//    private Map<String, String> getHeaders() {
//        HashMap<String, String> headers = new HashMap<>(2);
//        String apiKey = gptProperties.getApiKey();
//        if(StrUtil.isBlank(apiKey)){
//            log.error("没有配apikey");
//        }
//        headers.put("Authorization",apiKey);
//        headers.put("Content-Type","application/json");
//        return headers;
//    }

    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(45000);
        requestFactory.setReadTimeout(45000);
        restTemplate.setRequestFactory(requestFactory);
//        添加拦截器，每次用这个restTemplate发送请求都会携带上这些请求头
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization",  gptProperties.getApiKey());
            request.getHeaders().remove("Content-Type");
            request.getHeaders().add("Content-Type","application/json");
            return execution.execute(request, body);
        });

        return restTemplate;
    }

}
