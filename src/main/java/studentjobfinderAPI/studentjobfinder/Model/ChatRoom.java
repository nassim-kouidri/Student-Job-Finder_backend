package studentjobfinderAPI.studentjobfinder.Model;

import java.util.Collections;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ChatRoom {
	
	private String companyId;
	private String studentId;
	private String studentName;
	private String companyName;
	private Image studentImage;
	private Image companyImage;
	private List<OutputMessage> messages;
	@Id
	private String chatRoomId;
	
	
	public ChatRoom(String companyId, String studentId, String companyName, String studentName, Image companyImage, Image studentImage) {
		this.companyId=companyId;
		this.studentId=studentId;
		this.companyName=companyName;
		this.studentName=studentName;
		this.companyImage=companyImage;
		this.studentImage=studentImage;
		messages = Collections.emptyList();
	}
	
	
	
	public Image getStudentImage() {
		return studentImage;
	}



	public Image getCompanyImage() {
		return companyImage;
	}



	public void setStudentImage(Image studentImage) {
		this.studentImage = studentImage;
	}



	public void setCompanyImage(Image companyImage) {
		this.companyImage = companyImage;
	}



	public String getCompanyId() {
		return companyId;
	}


	public String getStudentId() {
		return studentId;
	}


	public String getStudentName() {
		return studentName;
	}


	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}


	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}


	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public String getChatRoomId() {
		return chatRoomId;
	}

	public void setChatRoomId(String chatRoomId) {
		this.chatRoomId = chatRoomId;
	}

	public String getCompany() {
		return companyId;
	}

	public String getStudent() {
		return studentId;
	}

	public void setCompany(String company) {
		this.companyId = company;
	}

	public void setStudent(String student) {
		this.studentId = student;
	}

	public List<OutputMessage> getMessages() {
		return messages;
	}
	
	public void setMessages(List<OutputMessage> messages) {
		this.messages = messages;
	} 
	

	
}
