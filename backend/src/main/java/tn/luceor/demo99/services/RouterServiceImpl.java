package tn.luceor.demo99.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import tn.luceor.demo99.DTO.RouterDTO;
import tn.luceor.demo99.JWT.CustomerUsersDetailsServices;
import tn.luceor.demo99.JWT.JwtFilter;
import tn.luceor.demo99.JWT.JwtUtils;
import tn.luceor.demo99.Wrapper.RouterWrapper;
import tn.luceor.demo99.constents.MangmentsConstents;
import tn.luceor.demo99.entities.Router;
import tn.luceor.demo99.entities.User;
import tn.luceor.demo99.repositories.IRouterRepository;
import tn.luceor.demo99.repositories.IUserRepository;
import tn.luceor.demo99.utils.MangmentsUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class RouterServiceImpl implements IRouterService{

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IRouterRepository routerRepository;


    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    CustomerUsersDetailsServices customerUsersDetailsServices;
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    JwtFilter jwtFilter ;


    @Override
    public ResponseEntity<String> addNewRouter(Map<String, String> requestMap, Long adminId) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateRouterMap(requestMap, false)) {
                    // Create a new router and set its properties
                    Router router = getRouterFromMap(requestMap, false);

                    // Set the admin by ID
                    User admin = new User();
                    admin.setId(adminId);
                    router.setAdmin(admin);

                    routerRepository.save(router);

                    return MangmentsUtils.getResponseEntity("Product Added Successfully", HttpStatus.OK);
                }
                return MangmentsUtils.getResponseEntity(MangmentsConstents.INVALID_DATA, HttpStatus.BAD_REQUEST);
            } else {
                return MangmentsUtils.getResponseEntity(MangmentsConstents.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return MangmentsUtils.getResponseEntity(MangmentsConstents.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private boolean validateRouterMap(Map<String, String> requestMap, boolean validateId) {
        if(requestMap.containsKey("name")) {
            if(requestMap.containsKey("idR") && validateId) {
                return true;
            } else if (!validateId) {
                return true;
            }
        }
        return false;
    }

    private Router getRouterFromMap(Map<String, String> requestMap, boolean isAdd) {
        Router router = new Router();
        if (isAdd) {
            router.setIdR(Long.parseLong(requestMap.get("idR")));
        } else {
            router.setStatus("true");
        }
        router.setName(requestMap.get("name"));
        router.setDescription(requestMap.get("description"));
        router.setPrice(Integer.parseInt(requestMap.get("price")));
        router.setQuantity(Long.parseLong(requestMap.get("quantity")));
        return router;
    }

    @Override
    public ResponseEntity<List<RouterWrapper>> getAllRouter() {
        try {
            return new ResponseEntity<>(routerRepository.getAllRouter(), HttpStatus.OK);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Override
    public ResponseEntity<RouterWrapper> getRouterById(Long idR) {
        try {
            return new ResponseEntity<>(routerRepository.getRouterById(idR), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new RouterWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteRouter(Long idR) {
        try {
            if(jwtFilter.isAdmin()) {
                Optional optional = routerRepository.findById(idR);
                if(optional.isPresent()) {
                    routerRepository.deleteById(idR);
                    return MangmentsUtils.getResponseEntity("Product deleted successfully.", HttpStatus.OK);
                } else {
                    return MangmentsUtils.getResponseEntity("Product id does not exist.", HttpStatus.OK);
                }
            } else {
                return MangmentsUtils.getResponseEntity(MangmentsConstents.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return MangmentsUtils.getResponseEntity(MangmentsConstents.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Transactional
    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                Long idR = Long.parseLong(requestMap.get("idR"));
                Optional<Router> optional = routerRepository.findById(idR);

                if (optional.isPresent()) {
                    String newStatus = requestMap.get("status");
                    routerRepository.updateRouterStatus(idR, newStatus);
                    return MangmentsUtils.getResponseEntity("Router status updated successfully", HttpStatus.OK);
                } else {
                    return MangmentsUtils.getResponseEntity("Router with the given id does not exist.", HttpStatus.NOT_FOUND);
                }
            } else {
                return MangmentsUtils.getResponseEntity("Unauthorized access.", HttpStatus.UNAUTHORIZED);
            }
        } catch (NumberFormatException ex) {
            // Handle the case where the provided idR cannot be parsed as a Long.
            return MangmentsUtils.getResponseEntity("Invalid idR format.", HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            // Handle other exceptions, e.g., database errors.
            ex.printStackTrace();
            return MangmentsUtils.getResponseEntity("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<String> updateRouter(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()) {
                if(validateRouterMap(requestMap, true)) {
                    Optional<Router> optional = routerRepository.findById(Long.parseLong(requestMap.get("idR")));
                    if(optional.isPresent()) {
                        Router product = getRouterFromMap(requestMap, true);
                        product.setStatus(optional.get().getStatus());
                        routerRepository.save(product);
                        return MangmentsUtils.getResponseEntity("Product Updated Successfully", HttpStatus.OK);
                    } else {
                        return MangmentsUtils.getResponseEntity("Product id does not exist.", HttpStatus.OK);
                    }
                } else {
                    return MangmentsUtils.getResponseEntity(MangmentsConstents.INVALID_DATA, HttpStatus.BAD_REQUEST);
                }
            } else {
                return MangmentsUtils.getResponseEntity(MangmentsConstents.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return MangmentsUtils.getResponseEntity(MangmentsConstents.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Override
    public List<RouterDTO> listRoutersByAdmin(Long adminId) {
        List<Router> routers = routerRepository.findByAdminId(adminId);
        return routers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private RouterDTO convertToDTO(Router router) {
        RouterDTO dto = new RouterDTO();
        dto.setIdR(router.getIdR());
        dto.setName(router.getName());
        dto.setStatus(router.getStatus());
        dto.setDescription(router.getDescription());
        dto.setPrice(router.getPrice());
        dto.setQuantity(router.getQuantity());
        return dto;
    }


    @Override
    public List<RouterWrapper> searchRouter(String keyword) {
        List<Router> routers = routerRepository.findByKeyword(keyword);
        // Convert routers to DTOs (RouterWrapper) as needed
        List<RouterWrapper> routerWrappers = new ArrayList<>();
        for (Router router : routers) {
            routerWrappers.add(new RouterWrapper(
                    router.getIdR(),
                    router.getName(),
                    router.getStatus(),
                    router.getDescription(),
                    router.getPrice(),
                    router.getQuantity()
            ));
        }
        return routerWrappers;
    }
















}
