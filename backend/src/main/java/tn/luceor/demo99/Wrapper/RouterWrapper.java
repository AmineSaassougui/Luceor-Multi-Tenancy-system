package tn.luceor.demo99.Wrapper;

import lombok.Data;

import javax.persistence.Column;

@Data
public class RouterWrapper {

     Long idR ;
     String name ;
     String status;
     String description;
     Integer price ;
     Long  quantity ;

    public RouterWrapper() {
    }




    public RouterWrapper(Long idR, String name, String status, String description, Integer price, Long quantity) {
        this.idR = idR;
        this.name = name;
        this.status = status;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public RouterWrapper(Long idR, String name) {
        this.idR = idR;
        this.name = name;
    }
}
