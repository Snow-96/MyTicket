package snow.myticket.tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailHelper {
    @Autowired
    private JavaMailSender javaMailSender;

    //读取配置文件中的参数
    @Value("${spring.mail.username}")
    private String sender;

    public String sendSimpleEmail(String recipient) {
        SimpleMailMessage message = new SimpleMailMessage();
        // 发送者
        message.setFrom(sender);
        // 接收者
        message.setTo(recipient);
        // 邮件主题
        message.setSubject("主题：Ticket验证码");
        // 邮件内容
        String code = String.valueOf(getRandNum(1,999999));
        message.setText("验证码如下: " + code);
        // 发送
        javaMailSender.send(message);

        return code;
    }

    private int getRandNum(int min, int max) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }
}
