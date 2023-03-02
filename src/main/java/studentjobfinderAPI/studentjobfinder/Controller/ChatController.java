package studentjobfinderAPI.studentjobfinder.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import studentjobfinderAPI.studentjobfinder.Model.ChatRoom;
import studentjobfinderAPI.studentjobfinder.Model.Message;
import studentjobfinderAPI.studentjobfinder.Model.OutputMessage;
import studentjobfinderAPI.studentjobfinder.Model.Role;
import studentjobfinderAPI.studentjobfinder.Model.Student;
import studentjobfinderAPI.studentjobfinder.Repository.ChatRepository;
import studentjobfinderAPI.studentjobfinder.Repository.CompanyRepository;
import studentjobfinderAPI.studentjobfinder.Repository.StudentRepository;
import studentjobfinderAPI.studentjobfinder.Service.ChatService;

@Controller
public class ChatController {
	
	@Autowired
	ChatService chatService;
	
	@Autowired
	CompanyRepository companyRepository;
	@Autowired
	ChatRepository chatRepository;
	@Autowired
	StudentRepository studentRepository;
	
	
	
	@MessageMapping("/chat/{chatRoomId}")
	@SendTo("/topic/{chatRoomId}")
	public OutputMessage send(Message message, @DestinationVariable("chatRoomId") String chatRoomId) throws Exception {
	    String time = new SimpleDateFormat("HH:mm").format(new Date());
	    OutputMessage outputMessage = new OutputMessage();
	    outputMessage.setMessage(message);
	    outputMessage.setTimeStamp(time);
	    chatService.sendMessage(outputMessage, chatRepository.findById(chatRoomId).get());
		System.out.println(message.getText());
	    return outputMessage;
	}




}
