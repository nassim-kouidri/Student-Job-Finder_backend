package studentjobfinderAPI.studentjobfinder.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SimpleMailMessage preConfiguredMessage;
    
    
    public void sendNewMail(String to, String body, String name)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Welcome to StudentJobFinder !");
        message.setText("Hello "+name+", your verification code is : "+body);
        mailSender.send(message);
    }

}
