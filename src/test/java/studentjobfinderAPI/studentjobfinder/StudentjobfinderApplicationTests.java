package studentjobfinderAPI.studentjobfinder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import studentjobfinderAPI.studentjobfinder.Controller.CompanyController;
import studentjobfinderAPI.studentjobfinder.Model.Company;
import studentjobfinderAPI.studentjobfinder.Repository.CompanyRepository;


@RunWith(SpringRunner.class)
@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.MOCK,
  classes = CompanyController.class)
@AutoConfigureMockMvc
class StudentjobfinderApplicationTests {

/*	@Test
	public void testIsEmailUsed() {
	    // Envoi d'une requête GET à l'URL "/api/isEmailUsed?email=test@example.com"
	    ResponseEntity<Boolean> response = restTemplate.getForEntity("/api/isEmailUsed?email=test@example.com", Boolean.class);

	    // Vérification du code de réponse (devrait être 200 OK)
	    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	    // Vérification de la réponse (devrait être "true" si l'adresse email est déjà utilisée, "false" sinon)
	    assertThat(response.getBody()).isEqualTo(true/false);
	}



*/

}
