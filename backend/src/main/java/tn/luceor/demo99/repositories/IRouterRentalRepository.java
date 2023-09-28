package tn.luceor.demo99.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.luceor.demo99.entities.Role;
import tn.luceor.demo99.entities.RouterRental;

import java.util.List;

public interface IRouterRentalRepository extends JpaRepository<RouterRental,Long> {
    @Query("SELECT rt.id, rt.router.name, rt.router.description, rt.router.price, rt.router.admin.id, rt.router.admin.name, rt.rentalDate FROM RouterRental rt WHERE rt.user.id = :userId")
    List<Object[]> findRentalsByUserId(Long userId);

    @Query("SELECT rt.id, rt.router.name ,rt.router.description, rt.router.price, rt.router.admin.id, rt.router.admin.name, rt.rentalDate,rt.user.id,rt.user.name,rt.user.contactNumber " +
            "FROM RouterRental rt")
    List<Object[]> findAllRentals();






}
