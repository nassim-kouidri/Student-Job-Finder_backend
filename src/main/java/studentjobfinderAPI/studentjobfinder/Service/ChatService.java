package studentjobfinderAPI.studentjobfinder.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import studentjobfinderAPI.studentjobfinder.Model.ChatRoom;
import studentjobfinderAPI.studentjobfinder.Model.OutputMessage;
import studentjobfinderAPI.studentjobfinder.Repository.ChatRepository;
import studentjobfinderAPI.studentjobfinder.Repository.CompanyRepository;
import studentjobfinderAPI.studentjobfinder.Repository.StudentRepository;

@Service
public class ChatService {

	@Autowired 
	ChatRepository chatRepository;
	@Autowired
	CompanyRepository companyRepository;
	@Autowired
	StudentRepository studentRepository;
	
	
	public ChatRoom createChatRoom(String company,String student) {
		ChatRoom chatRoom = new ChatRoom(company, student,companyRepository.findById(company).get().getName(),studentRepository.findById(student).get().getName(),companyRepository.findById(company).get().getLogo(),studentRepository.findById(student).get().getImage());
		chatRepository.save(chatRoom);
		return chatRoom;
	}
	
	public void sendMessage(OutputMessage message, ChatRoom chatRoom) {
		chatRoom.getMessages().add(message);
		chatRepository.save(chatRoom);
	}

   

}

