package tn.luceor.demo99.DTO;

public class RouterDTO {
    private Long idR;
    private String name;
    private String status;
    private String description;
    private Integer price;
    private Long quantity;

    public RouterDTO() {
    }

    public RouterDTO(Long idR, String name, String status, String description, Integer price, Long quantity) {
        this.idR = idR;
        this.name = name;
        this.status = status;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getIdR() {
        return idR;
    }

    public void setIdR(Long idR) {
        this.idR = idR;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
