package xyz.linyh.gptservice.entitys;

import lombok.Data;
import lombok.ToString;

/**
 * 发送请求的message
 * @author lin
 */
@Data
@ToString
public class Text {

    private String role;

    private String content;
}
