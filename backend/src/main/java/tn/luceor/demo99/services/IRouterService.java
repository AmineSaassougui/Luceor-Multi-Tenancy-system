package tn.luceor.demo99.services;

import org.springframework.http.ResponseEntity;
import tn.luceor.demo99.DTO.RouterDTO;
import tn.luceor.demo99.Wrapper.RouterWrapper;
import tn.luceor.demo99.entities.Router;

import java.util.List;
import java.util.Map;

public interface IRouterService {


    ResponseEntity<String> addNewRouter(Map<String, String> requestMap, Long adminId);

    ResponseEntity<List<RouterWrapper>> getAllRouter();

    ResponseEntity<RouterWrapper> getRouterById(Long idR);

    ResponseEntity<String> deleteRouter(Long idR);

    ResponseEntity<String> updateStatus(Map<String, String> requestMap);

    ResponseEntity<String> updateRouter(Map<String, String> requestMap);


    List<RouterDTO> listRoutersByAdmin(Long adminId);


    List<RouterWrapper> searchRouter(String keyword);
}
