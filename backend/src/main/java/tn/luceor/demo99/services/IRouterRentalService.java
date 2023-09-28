package tn.luceor.demo99.services;

import org.springframework.http.ResponseEntity;
import tn.luceor.demo99.DTO.RentalSummaryDTO;
import tn.luceor.demo99.DTO.RouterRentalDTO;
import tn.luceor.demo99.entities.RouterRental;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface IRouterRentalService {

    ResponseEntity<String> rentRouterByUser(RouterRentalDTO rentalDTO, Long userId);


    List<RentalSummaryDTO> listRentalSummariesByUser(Long userId);


    List<RentalSummaryDTO> listAllRentals(Long userId);

    void deleteRentalByIdForUser(Long rentalId, Long userId) throws AccessDeniedException;
}
