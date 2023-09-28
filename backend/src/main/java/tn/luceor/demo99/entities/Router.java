package tn.luceor.demo99.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@NamedQuery(name = "Router.getAllRouter", query = "SELECT new tn.luceor.demo99.Wrapper.RouterWrapper(r.idR, r.name,r.status, r.description, r.price,r.quantity) FROM Router r")
@NamedQuery(name = "Router.getRouterById", query = "SELECT new tn.luceor.demo99.Wrapper.RouterWrapper(r.idR, r.name,r.status, r.description, r.price,r.quantity) FROM Router r WHERE r.idR=:idR")
/*@NamedQuery(name = "Router.updateRouterStatus", query = "UPDATE Router r SET r.status=:status WHERE r.idR=:idR")


 */


@Data
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@Entity
@Table(name = "routers")
public class Router implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idR ;
    private String name ;
    @Column(name = "status")
    private String status;
    private String description;
    private Integer price ;
    private Long  quantity ;

    @ManyToOne
    @JoinColumn(name = "admin_id") // Replace with your actual column name
    private User admin;

    @OneToMany(mappedBy = "router", cascade = CascadeType.ALL)
    private List<RouterRental> routerRentals;




}
