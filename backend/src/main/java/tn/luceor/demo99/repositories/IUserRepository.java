package tn.luceor.demo99.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import tn.luceor.demo99.Wrapper.UserWithoutPass;
import tn.luceor.demo99.entities.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<User,Integer> {
    List<UserWithoutPass> getAllUsers();
    User findByEmailID(@Param("email") String email) ;

    List<String> getAllAdmins();
    @Transactional
    @Modifying
    Integer updateStatus(@Param("status") String status,@Param("id") Long id);

    Optional<User> findById(long id);
}
