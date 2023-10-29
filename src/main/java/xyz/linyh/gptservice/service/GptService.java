package xyz.linyh.gptservice.service;

import cn.hutool.http.HttpRequest;
import xyz.linyh.gptservice.entitys.GPTMessage;

import java.util.List;

/**
 * gpt的一些操作处理
 */
public interface GptService {


    public List<GPTMessage> sendRequest(List<GPTMessage> messages);



}
