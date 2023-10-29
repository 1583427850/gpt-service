package xyz.linyh.gptservice.entitys;

import lombok.Data;

import java.util.List;

@Data
public class GPTResponse {

    private String id;

    private String object;

    private int created;

    private String model;

    private List<Choice> choices;

    private Usage usage;


}
