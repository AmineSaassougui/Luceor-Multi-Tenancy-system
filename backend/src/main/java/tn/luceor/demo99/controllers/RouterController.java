package tn.luceor.demo99.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.luceor.demo99.DTO.RouterDTO;
import tn.luceor.demo99.JWT.JwtFilter;
import tn.luceor.demo99.JWT.JwtUtils;
import tn.luceor.demo99.Wrapper.RouterWrapper;
import tn.luceor.demo99.services.IRouterService;


import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/router")
@CrossOrigin("*")
public class RouterController {
    @Autowired
    IRouterService iRouterService ;
    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    JwtUtils jwtUtils;


    @PostMapping(path = "/add")
    public ResponseEntity<String> addNewRouter(@RequestBody Map<String, String> requestMap, HttpServletRequest request) {
        // Get the JWT token from the request
        String token = jwtUtils.resolveToken(request);

        // Extract the admin ID from the token
        Long adminId = jwtUtils.extractAdminIdFromToken(token);

        if (adminId != null) {
            return iRouterService.addNewRouter(requestMap, adminId);
        } else {
            // Handle the case where adminId is not found in the JWT token
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Admin ID not found in JWT token.");
        }
    }

    @GetMapping(path = "/get")
    ResponseEntity<List<RouterWrapper>> getAllRouter(){
        return iRouterService.getAllRouter();

    }

    @GetMapping(path = "/getById/{idR}")
    ResponseEntity<RouterWrapper> getRouterById(@PathVariable Long idR){
        return iRouterService.getRouterById(idR);
    }

    @DeleteMapping(path = "/delete/{idR}")
    ResponseEntity<String> deleteRouter(@PathVariable Long idR){
        return iRouterService.deleteRouter(idR);
    }

    @PostMapping(path = "/updateStatus")
    ResponseEntity<String> updateStatus(@RequestBody Map<String, String> requestMap){
        return iRouterService.updateStatus(requestMap);

    }

    @PostMapping(path = "/update")
    ResponseEntity<String> updateRouter(@RequestBody Map<String, String> requestMap){
        return iRouterService.updateRouter(requestMap);

    }


    @GetMapping("/listbyAdmin")
    public ResponseEntity<List<RouterDTO>> listRoutersByAdmin(HttpServletRequest request) {
        // Get the adminId from the JWT token
        String token = jwtUtils.resolveToken(request);
        Long adminId = jwtUtils.extractAdminIdFromToken(token);

        if (adminId != null) {
            // Call the service method to list routers for the admin
            List<RouterDTO> routerDTOs = iRouterService.listRoutersByAdmin(adminId);
            return ResponseEntity.ok(routerDTOs);
        } else {
            // Handle the case where adminId is not found in the JWT token
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }


    @GetMapping("/search")
    public ResponseEntity<List<RouterWrapper>> searchRouters(@RequestParam String keyword, HttpServletRequest request) {
        // Get the user ID from the JWT token
        Long userId = jwtUtils.extractUserIdFromToken(jwtUtils.resolveToken(request));

        if (userId != null) {
            // Call the service method to search routers by keyword
            List<RouterWrapper> routers = iRouterService.searchRouter(keyword);
            return ResponseEntity.ok(routers);
        } else {
            // Handle the case where userId is not found in the JWT token
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }




}
