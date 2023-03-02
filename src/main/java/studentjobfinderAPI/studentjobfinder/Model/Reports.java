package studentjobfinderAPI.studentjobfinder.Model;

import java.util.List;
import java.util.Collections;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Reports {

	private List<String> messages ;
	private List<String> reportersId;
	private boolean isReported;

	public Reports() {
		this.isReported = false;
		this.messages = Collections.emptyList();
		this.reportersId = Collections.emptyList();
	}
	
	public List<String> getMessages() {
		return messages;
	}
	public List<String> getReportersId() {
		return reportersId;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	public void setReportersId(List<String> reportersId) {
		this.reportersId = reportersId;
	}
	public boolean isReported() {
		return isReported;
	}
	
	public void setReported(boolean isReported) {
		this.isReported = isReported;
	}
	
	
	
}