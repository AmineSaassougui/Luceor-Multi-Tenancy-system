package tn.luceor.demo99.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class MangmentsUtils {

    private MangmentsUtils(){
    }
    public static ResponseEntity<String> getResponseEntity (String responsemessage, HttpStatus httpStatus){
        return new ResponseEntity<String>("{\"message\":\""+responsemessage+"\"}",httpStatus);

    }

}
