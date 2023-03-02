package studentjobfinderAPI.studentjobfinder.Configuration;

import org.springframework.data.rest.core.event.ExceptionEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {


    @ResponseBody
    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<String> unauthorizedException(BadCredentialsException exception) {
    	return  new ResponseEntity<>("Email ou mot de passe incorrect", HttpStatus.UNAUTHORIZED);
    }
    @ResponseBody
    @ExceptionHandler(DisabledException.class )
    public ResponseEntity<String> forbiddenException(DisabledException exception1) {
        return  new ResponseEntity<>("Your account is banned , please contact us at admin@studentjobfinder.com", HttpStatus.FORBIDDEN);
    }

    @ResponseBody
    @ExceptionHandler(ExceptionInInitializerError.class )
    public ResponseEntity<String> exceptionEvent(ExceptionInInitializerError exception1) {
        return  new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class )
    public ResponseEntity<String> exceptionEvent(Exception exception1) {

        return  new ResponseEntity<>("Veuillez vérifier votre email avant de vous connecter" ,HttpStatus.UNAUTHORIZED);
    }



}

