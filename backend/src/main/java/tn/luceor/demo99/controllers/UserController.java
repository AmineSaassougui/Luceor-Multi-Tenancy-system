package tn.luceor.demo99.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tn.luceor.demo99.Wrapper.UserWithoutPass;

import tn.luceor.demo99.entities.User;
import tn.luceor.demo99.repositories.IUserRepository;
import tn.luceor.demo99.services.IUserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    IUserService iUserService;
    @Autowired
    IUserRepository userRepository;





    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody  Map<String, String> requestMap) {
        return iUserService.signup(requestMap);
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String,String> requestMap){
        return iUserService.login(requestMap);
    }

    @GetMapping("/get")
    public ResponseEntity<List<UserWithoutPass>> getAllUsers(){
        return iUserService.getAllUsers();
    }


    @PostMapping("/activateaccount")
    public ResponseEntity<String> updateStatus(@RequestBody Map<String,String> requestMap){
        return iUserService.activateAccount(requestMap);
    }

    @GetMapping("/checktoken")
    public ResponseEntity<String > checkToken(){
        return iUserService.checkToken();
    }

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody Map<String,String> requestMap){
        return iUserService.changePassword(requestMap);
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String,String> requestMap){
        return iUserService.forgotPassword(requestMap);
    }

   @GetMapping("/getInfo")
    public ResponseEntity<Map<String, Object>> getUserInfo() {
        // Get the authenticated user's email from the SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName(); // Assuming email is the username

        // Fetch the user's information from the database
        User user = userRepository.findByEmailID(userEmail);

        if (user != null) {
            // Create a response map to structure the data
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("username", user.getName());
            userInfo.put("email", user.getEmail());
            userInfo.put("contactNumber", user.getContactNumber());
            userInfo.put("status", user.getStatus());
            userInfo.put("role", user.getRole());
            userInfo.put("address", user.getAddress());
            // Add more fields as needed

            return ResponseEntity.ok(userInfo);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }



}