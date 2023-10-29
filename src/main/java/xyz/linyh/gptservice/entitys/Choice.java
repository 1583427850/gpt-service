package xyz.linyh.gptservice.entitys;

import lombok.Data;

@Data
public class Choice {
    private int index;

    /**
     * gpt返回的信息
     */
    private GPTMessage message;

    private String finish_reason;


}
