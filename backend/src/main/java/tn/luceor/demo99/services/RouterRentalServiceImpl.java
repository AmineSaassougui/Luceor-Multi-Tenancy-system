package tn.luceor.demo99.services;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tn.luceor.demo99.DTO.RentalSummaryDTO;
import tn.luceor.demo99.DTO.RouterRentalDTO;
import tn.luceor.demo99.JWT.JwtFilter;
import tn.luceor.demo99.entities.Router;
import tn.luceor.demo99.entities.RouterRental;
import tn.luceor.demo99.entities.User;
import tn.luceor.demo99.repositories.IRouterRentalRepository;
import tn.luceor.demo99.repositories.IRouterRepository;
import tn.luceor.demo99.repositories.IUserRepository;
import tn.luceor.demo99.utils.MangmentsUtils;

import javax.persistence.EntityNotFoundException;
import java.nio.file.AccessDeniedException;
import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class RouterRentalServiceImpl implements IRouterRentalService {

    @Autowired
    IRouterRentalRepository routerRentalRepository;
    @Autowired
    IUserRepository userRepository;
    @Autowired
    IRouterRepository routerRepository;
    @Autowired
    JwtFilter jwtFilter ;


    @Override
    public ResponseEntity<String> rentRouterByUser(RouterRentalDTO rentalDTO, Long userId) {
        try {
            // Check if the user exists
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isEmpty()) {
                return MangmentsUtils.getResponseEntity("User not found.", HttpStatus.NOT_FOUND);
            }

            // Check if the router exists
            Optional<Router> routerOptional = routerRepository.findById(rentalDTO.getRouterId());
            if (routerOptional.isEmpty()) {
                return MangmentsUtils.getResponseEntity("Router not found.", HttpStatus.NOT_FOUND);
            }

            // Create a new RouterRental entity
            RouterRental routerRental = new RouterRental();
            routerRental.setUser(userOptional.get());
            routerRental.setRouter(routerOptional.get());
            // Set any additional rental information

            // Set the rental date to the current date
            routerRental.setRentalDate(new Date()); // This sets the rental date to the current date and time


            // Save the router rental
            routerRentalRepository.save(routerRental);

            return MangmentsUtils.getResponseEntity("Router rented successfully.", HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return MangmentsUtils.getResponseEntity("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<RentalSummaryDTO> listRentalSummariesByUser(Long userId) {
        List<Object[]> resultList = routerRentalRepository.findRentalsByUserId(userId);
        List<RentalSummaryDTO> rentalSummaries = new ArrayList<>();

        for (Object[] result : resultList) {
            // Construct RentalSummaryDTO objects from the array elements
            RentalSummaryDTO summary = new RentalSummaryDTO();
            summary.setId((Long) result[0]);
            summary.setRouterName((String) result[1]);
            summary.setRouterDescription((String) result[2]);
            summary.setRouterPrice((Integer) result[3]);
            summary.setRouterAdminId((Long) result[4]);
            summary.setRouterAdminName((String) result[5]);
            summary.setRentalDate((Date) result[6]);

            rentalSummaries.add(summary);
        }

        return rentalSummaries;
    }

    @Override
    public List<RentalSummaryDTO> listAllRentals(Long userId) {
        if (jwtFilter.isAdmin()) {
            // If the user has the "ADMIN" role, fetch all rentals
            List<Object[]> resultList = routerRentalRepository.findAllRentals();

            List<RentalSummaryDTO> rentalSummaries = new ArrayList<>();

            for (Object[] result : resultList) {
                // Construct RentalSummaryDTO objects from the array elements
                RentalSummaryDTO summary = new RentalSummaryDTO();
                summary.setId((Long) result[0]);
                summary.setRouterName((String) result[1]);
                summary.setRouterDescription((String) result[2]);
                summary.setRouterPrice((Integer) result[3]);
                summary.setRouterAdminId((Long) result[4]);
                summary.setRouterAdminName((String) result[5]);
                summary.setRentalDate((Date) result[6]);
                summary.setUserId((Long) result[7]);
                summary.setUsername((String) result[8]);
                summary.setContactNumber((String) result[9]);


                rentalSummaries.add(summary);
            }

            return rentalSummaries;
        } else {
            // If the user is not an admin, they are not allowed to access all rentals
            return Collections.emptyList(); // Return an empty list or handle it as needed
        }
    }

    @Override
    public void deleteRentalByIdForUser(Long rentalId, Long userId) throws AccessDeniedException {
        // Find the rental by ID
        Optional<RouterRental> rentalOptional = routerRentalRepository.findById(rentalId);

        if (rentalOptional.isPresent()) {
            RouterRental routerRental = rentalOptional.get();

            // Check if the rental belongs to the authenticated user
            if (!routerRental.getUser().getId().equals(userId)) {
                throw new AccessDeniedException("Access denied. Rental does not belong to the authenticated user.");
            }

            // Delete the rental
            routerRentalRepository.delete(routerRental);
        } else {
            throw new EntityNotFoundException("Rental not found with ID: " + rentalId);
        }
    }



}


