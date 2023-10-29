package xyz.linyh.gptservice.entitys;

import lombok.Data;

/**
 * gpt请求message单个实体类
 * @author lin
 */
@Data
public class GPTMessage {

    private String role;

    private String content;
}
