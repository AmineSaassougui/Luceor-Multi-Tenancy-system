package tn.luceor.demo99.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@NamedQuery(name = "User.findByEmailID",query = "select u from User u where u.email=:email")


@NamedQuery(name = "User.getAllUsers",query = "select new tn.luceor.demo99.Wrapper.UserWithoutPass(u.id,u.name,u.email,u.contactNumber,u.role,u.status) from User u")


@NamedQuery(name = "User.getAllAdmins",query = "select u.email from User u where u.role='ADMIN'")


@NamedQuery(name = "User.updateStatus",query = "update User u set u.status=:status where u.id=:id")



@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {
    private  static  final long serialVersionUID = 1L ;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private  String name ;
    private  String contactNumber ;
    private  String email ;
    private  String password ;
    private  String status ;
    @Enumerated(EnumType.STRING)
    Role role;
    private  String address;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.PERSIST)
    private List<Router> routers;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RouterRental> routerRentals;




}
