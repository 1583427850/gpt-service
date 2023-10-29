package xyz.linyh.gptservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.linyh.gptservice.controller.GPTController;
import xyz.linyh.gptservice.entitys.GPTMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootTest(classes = GptServiceApplication.class )
class GptServiceApplicationTests {

    @Test
    void contextLoads() {
        List<GPTMessage> messages = new ArrayList<>();
        GPTController controller = new GPTController();
        boolean isFlag = true;

        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("请输入：");
            String str = scanner.nextLine();
            GPTMessage message = new GPTMessage();
            message.setRole("user");
            message.setContent(str);
            messages.add(message);
            List<GPTMessage> newMessages = controller.sendMessage(messages, "1");
            messages=newMessages;
            System.out.println(messages);
        }

    }

}
