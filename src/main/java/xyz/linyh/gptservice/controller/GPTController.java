package xyz.linyh.gptservice.controller;

import cn.hutool.core.util.StrUtil;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.linyh.gptservice.entitys.GPTMessage;
import xyz.linyh.gptservice.entitys.RoleMessage;
import xyz.linyh.gptservice.service.GptService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GPTController {

    @Autowired
    private GptService gptService;


    /**
     * 返回最新回复和这个话题里面的最新信息
     * @param
     * @param chatId 聊天id
     */
    @PostMapping("/send")
    public List<GPTMessage> sendMessage(@RequestBody List<GPTMessage> messages, String chatId) {
//        判断是否有历史信息，如果有那么取出来然后放到一起

        return gptService.sendRequest(messages);
    }

//    @PostMapping("/role/chat")
//    public String hasRoleChat(@RequestBody RoleMessage roleMessage){
//        String systemRole = roleMessage.getRoleMessage();
//        if(StrUtil.isNotBlank(systemRole)){
//            GPTMessage sysMessage = new GPTMessage();
//            sysMessage.setRole("system");
//            sysMessage.setContent(systemRole);
//        }
//        sendMessage()
//        return null;
//    }
}
