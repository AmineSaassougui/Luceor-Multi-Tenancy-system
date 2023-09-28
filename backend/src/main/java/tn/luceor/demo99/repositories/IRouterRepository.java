package tn.luceor.demo99.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.luceor.demo99.Wrapper.RouterWrapper;
import tn.luceor.demo99.entities.Router;
import tn.luceor.demo99.entities.User;

import java.util.List;

public interface IRouterRepository extends JpaRepository<Router,Long> {

    List<RouterWrapper> getAllRouter();

    RouterWrapper getRouterById(@Param("idR") Long idR);

    @Modifying
    @Query("UPDATE Router r SET r.status = :status WHERE r.idR = :idR")
    Integer updateRouterStatus(@Param("idR") Long idR, @Param("status") String status);


    @Query("SELECT r FROM Router r WHERE r.admin.id = :adminId")
    List<Router> findByAdminId(Long adminId);

    @Query("SELECT r FROM Router r WHERE r.name LIKE %:keyword% OR r.description LIKE %:keyword% ")
    List<Router> findByKeyword(@Param("keyword") String keyword);


}
