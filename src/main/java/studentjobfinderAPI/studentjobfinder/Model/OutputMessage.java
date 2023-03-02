package studentjobfinderAPI.studentjobfinder.Model;

public class OutputMessage {

	
	Message message;
	String timeStamp;
	
	public OutputMessage(Message message, String timestamp) {
		this.message.setText("WEEEE");
		this.timeStamp=timestamp;
	}
	
	public OutputMessage() {
		// TODO Auto-generated constructor stub
	}

	public Message getMessage() {
		return message;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
}
