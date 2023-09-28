package tn.luceor.demo99.services;

import org.springframework.http.ResponseEntity;
import tn.luceor.demo99.Wrapper.UserWithoutPass;

import java.util.List;
import java.util.Map;

public interface IUserService  {
    public ResponseEntity<String > signup(Map<String,String> requestmap) ;

    ResponseEntity<String> login(Map<String, String> requestMap);

    ResponseEntity<List<UserWithoutPass>> getAllUsers();

    ResponseEntity<String> activateAccount(Map<String, String> requestMap);

    ResponseEntity<String> checkToken();

    ResponseEntity<String> changePassword(Map<String, String> requestMap);

    ResponseEntity<String> forgotPassword(Map<String, String> requestMap);
}