package tn.luceor.demo99.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.luceor.demo99.DTO.RentalSummaryDTO;
import tn.luceor.demo99.DTO.RouterRentalDTO;
import tn.luceor.demo99.JWT.JwtUtils;
import tn.luceor.demo99.entities.RouterRental;
import tn.luceor.demo99.services.IRouterRentalService;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/rental")
@CrossOrigin("*")
public class RouterRentalController {
    @Autowired
    IRouterRentalService iRouterRentalService ;
    @Autowired
    JwtUtils jwtUtils;


    @PostMapping("/rentRouter")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> rentRouter(@RequestBody RouterRentalDTO rentalDTO, HttpServletRequest request) {
        // Get the authenticated user's ID from the JWT token
        Long userId = jwtUtils.extractUserIdFromToken(jwtUtils.resolveToken(request));

        if (userId != null) {
            return iRouterRentalService.rentRouterByUser(rentalDTO, userId);
        } else {
            // Handle the case where userId is not found in the JWT token
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User ID not found in JWT token.");
        }
    }

    @GetMapping("/listByUser")
    public ResponseEntity<List<RentalSummaryDTO>> listRentalsByUser(HttpServletRequest request) {
        // Get the userId from the JWT token
        String token = jwtUtils.resolveToken(request);
        Long userId = jwtUtils.extractUserIdFromToken(token);

        if (userId != null) {
            // Call the service method to list rentals for the user
            List<RentalSummaryDTO> rentalDTOs = iRouterRentalService.listRentalSummariesByUser(userId);
            return ResponseEntity.ok(rentalDTOs);
        } else {
            // Handle the case where userId is not found in the JWT token
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }


    @GetMapping("/listAllRentals")
    public ResponseEntity<List<RentalSummaryDTO>> listAllRentals(HttpServletRequest request) {
        // Get the userId from the JWT token
        String token = jwtUtils.resolveToken(request);
        Long userId = jwtUtils.extractUserIdFromToken(token);

        List<RentalSummaryDTO> rentalDTOs = iRouterRentalService.listAllRentals(userId);
        return ResponseEntity.ok(rentalDTOs);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRental(@PathVariable Long id, HttpServletRequest request) {
        // Get the authenticated user's ID from the JWT token
        Long userId = jwtUtils.extractUserIdFromToken(jwtUtils.resolveToken(request));

        if (userId != null) {
            try {
                // Call the service method to delete the rental by ID for the authenticated user
                iRouterRentalService.deleteRentalByIdForUser(id, userId);
                return ResponseEntity.ok("Rental deleted successfully.");
            } catch (AccessDeniedException e) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied. Rental does not belong to the authenticated user.");
            } catch (EntityNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rental not found with ID: " + id);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete rental.");
            }
        } else {
            // Handle the case where userId is not found in the JWT token
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User ID not found in JWT token.");
        }

    }


}
