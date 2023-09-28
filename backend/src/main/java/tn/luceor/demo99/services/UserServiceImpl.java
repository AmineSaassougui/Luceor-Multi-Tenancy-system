package tn.luceor.demo99.services;

import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tn.luceor.demo99.JWT.CustomerUsersDetailsServices;
import tn.luceor.demo99.JWT.JwtFilter;
import tn.luceor.demo99.JWT.JwtUtils;
import tn.luceor.demo99.Wrapper.UserWithoutPass;
import tn.luceor.demo99.entities.Role;
import tn.luceor.demo99.entities.User;
import tn.luceor.demo99.repositories.IUserRepository;
import tn.luceor.demo99.utils.EmailService;
import tn.luceor.demo99.utils.MangmentsUtils;

import java.util.*;


import tn.luceor.demo99.constents.MangmentsConstents;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

    @Autowired
    IUserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    CustomerUsersDetailsServices customerUsersDetailsServices;
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    JwtFilter jwtFilter ;

    @Autowired
    EmailService emailService ;



    @Override
    public ResponseEntity<String> signup(Map<String, String> requestmap) {
        log.info("Inside signup {}", requestmap);
        try {
            if (validateSignUpMap(requestmap)) {
                User user = userRepository.findByEmailID(requestmap.get("email"));
                if (Objects.isNull(user)) {
                    userRepository.save(getUserFromMap(requestmap));
                    log.info("user from requestMAP {}", getUserFromMap(requestmap));
                    return MangmentsUtils.getResponseEntity(MangmentsConstents.SIGNUP_SUCCESSFUL, HttpStatus.OK);

                } else {
                    return MangmentsUtils.getResponseEntity(MangmentsConstents.EMAIL_EXISTS_WARNING, HttpStatus.BAD_REQUEST);
                }

            } else {
                return MangmentsUtils.getResponseEntity(MangmentsConstents.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return MangmentsUtils.getResponseEntity(MangmentsConstents.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateSignUpMap(Map<String, String> reqMap) {
        return reqMap.containsKey("name") && reqMap.containsKey("contactNumber") && reqMap.containsKey("email") && reqMap.containsKey("password");

    }

    private User getUserFromMap(Map<String, String> reqMap) {
        User user = new User();
        user.setName(reqMap.get("name"));
        user.setEmail(reqMap.get("email"));
        user.setAddress(reqMap.get("address"));
        user.setPassword(reqMap.get("password"));
        user.setContactNumber(reqMap.get("contactNumber"));
        user.setStatus("false");
        user.setRole(Role.USER);
        return user;
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("inside login {}", requestMap);
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password"))
            );
            if (auth.isAuthenticated()) {
                if (customerUsersDetailsServices.getUserDetails().getStatus().equalsIgnoreCase("true")) {
                    return new ResponseEntity<String>("{\"token\":\"" +
                            jwtUtils.generateToken(customerUsersDetailsServices.getUserDetails().getEmail(),
                                    customerUsersDetailsServices.getUserDetails().getRole(), customerUsersDetailsServices.getUserDetails().getId()) + "\"}", HttpStatus.OK);
                } else {
                    return MangmentsUtils.getResponseEntity(MangmentsConstents.WAIT_FOR_ADMIN_APPROVAL, HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception ex) {
            log.error("{}", ex);
        }
        return MangmentsUtils.getResponseEntity(MangmentsConstents.BAD_CREDENTIALS, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<UserWithoutPass>> getAllUsers() {
        try{
            if(jwtFilter.isAdmin()){
                return new ResponseEntity<>(userRepository.getAllUsers(),HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> activateAccount(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
                Optional<User> user = userRepository.findById(Long.parseLong(requestMap.get("id")));
                if(!user.isEmpty()){
                    userRepository.updateStatus(requestMap.get("status"),Long.parseLong(requestMap.get("id")));
                    //sending emails to all admins to announce about newly activated or banned accounts
                    sendMailToAllAdmins(requestMap.get("status"),user.get().getEmail(),userRepository.getAllAdmins());
                    return MangmentsUtils.getResponseEntity(MangmentsConstents.STATUS_UPDATED, HttpStatus.OK);
                }
                else {
                    return MangmentsUtils.getResponseEntity(String.format("{\"message\":\"" + "no User with id %2d . " + "\"}",Long.parseLong(requestMap.get("userId"))), HttpStatus.NOT_FOUND);
                }
            }else{
                return MangmentsUtils.getResponseEntity(MangmentsConstents.NOT_AUTHORIZED,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return MangmentsUtils.getResponseEntity(MangmentsConstents.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void sendMailToAllAdmins(String status, String user, List<String> allAdmins) {
        //bech me teb3athch el mail el nefs el user elli 9a3ed teb3ath mennou
        allAdmins.remove(jwtFilter.getCurrentUser());
        if(status != null && status.equalsIgnoreCase("true")){
            emailService.sendSimpleMessage(jwtFilter.getCurrentUser(),"Account Activated","USER:~ "+user+" is approved by ADMIN:~ " + jwtFilter.getCurrentUser(),allAdmins);
        }else{
            emailService.sendSimpleMessage(jwtFilter.getCurrentUser(),"Account Disabled","USER:~ "+user+" is disabled by ADMIN:~ " + jwtFilter.getCurrentUser(),allAdmins);
        }

    }

    @Override
    public ResponseEntity<String> checkToken() {
        try{
            return MangmentsUtils.getResponseEntity("true",HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
            return MangmentsUtils.getResponseEntity(MangmentsConstents.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try {
            //el jwtFilter.getCurrentUser() traja3 el subject mel token elli houwe msemmih fel jwtFilter "username" elli houwe reellement el email 5ater nestaaml fel email comme etant username (me andich attribut fel class esmou username assl haka el email yaaml fel zouz)
            User user = userRepository.findByEmailID(jwtFilter.getCurrentUser());
            if(!user.equals(null)){
                if(user.getPassword().equals(requestMap.get("oldPassword"))){
                    if(user.getPassword().equals((requestMap.get("newPassword")))){
                        return MangmentsUtils.getResponseEntity(MangmentsConstents.PASSWORD_ALREADY_USED, HttpStatus.BAD_REQUEST);
                    }else {
                        user.setPassword(requestMap.get("newPassword"));
                        userRepository.save(user);
                        return MangmentsUtils.getResponseEntity(MangmentsConstents.PASSWORD_CHANGED_MESSAGE, HttpStatus.ACCEPTED);
                    }
                }else{
                    return MangmentsUtils.getResponseEntity(MangmentsConstents.INCORRECT_INPUT,HttpStatus.BAD_REQUEST);
                }
            }else{
                return MangmentsUtils.getResponseEntity(MangmentsConstents.EMAIL_NOT_FOUND_WARNING,HttpStatus.NOT_FOUND);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return MangmentsUtils.getResponseEntity(MangmentsConstents.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
        try {
            User user = userRepository.findByEmailID(requestMap.get("email"));
            if(!Objects.isNull(user) && !Strings.isNullOrEmpty(user.getEmail())) {
                emailService.forgotPasswordMail(user.getEmail(), "Credentials by Connect4Aid App", user.getPassword());
                return MangmentsUtils.getResponseEntity(MangmentsConstents.CHECK_MAIL_MESSAGE, HttpStatus.OK);
            }else{
                return MangmentsUtils.getResponseEntity(MangmentsConstents.EMAIL_NOT_REGISTRED,HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
            return MangmentsUtils.getResponseEntity(MangmentsConstents.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





}
