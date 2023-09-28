package tn.luceor.demo99.DTO;

import java.util.Date;

public class RentalSummaryDTO {
    private Long id;
    private String routerName;
    private String routerDescription;
    private Integer routerPrice;
    private Long routerAdminId;
    private String routerAdminName;
    private Date rentalDate;
    private Long userId; // New field userId
    private String username; // New field username
    private String contactNumber; // New field contactNumber

    public RentalSummaryDTO() {
        // Default constructor
    }

    public RentalSummaryDTO(Long id, String routerName, String routerDescription, Integer routerPrice, Long routerAdminId, String routerAdminName, Date rentalDate, Long userId, String username, String contactNumber) {
        this.id = id;
        this.routerName = routerName;
        this.routerDescription = routerDescription;
        this.routerPrice = routerPrice;
        this.routerAdminId = routerAdminId;
        this.routerAdminName = routerAdminName;
        this.rentalDate = rentalDate;
        this.userId = userId; // Initialize the userId field
        this.username = username; // Initialize the username field
        this.contactNumber = contactNumber; // Initialize the contactNumber field
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRouterName() {
        return routerName;
    }

    public void setRouterName(String routerName) {
        this.routerName = routerName;
    }

    public String getRouterDescription() {
        return routerDescription;
    }

    public void setRouterDescription(String routerDescription) {
        this.routerDescription = routerDescription;
    }

    public Integer getRouterPrice() {
        return routerPrice;
    }

    public void setRouterPrice(Integer routerPrice) {
        this.routerPrice = routerPrice;
    }

    public Long getRouterAdminId() {
        return routerAdminId;
    }

    public void setRouterAdminId(Long routerAdminId) {
        this.routerAdminId = routerAdminId;
    }

    public String getRouterAdminName() {
        return routerAdminName;
    }

    public void setRouterAdminName(String routerAdminName) {
        this.routerAdminName = routerAdminName;
    }

    public Date getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }

    // Getters and setters for userId, username, and contactNumber
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
