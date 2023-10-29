package xyz.linyh.gptservice.entitys;

import lombok.Data;

import java.util.List;

@Data
public class RoleMessage {

    private String roleMessage;

    private List<GPTMessage> messages;
}
