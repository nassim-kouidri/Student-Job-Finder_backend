package studentjobfinderAPI.studentjobfinder.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Candidacy {


    private List<String> answers;
    private  String offerId;

	public List<String> getAnswers() {
		return answers;
	}

	public String getOfferId() {
		return offerId;
	}

	public void setAnswers(List<String> answers) {
		this.answers = answers;
	}

	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}
    
    
}
