package tn.luceor.demo99.DTO;

public class RouterRentalDTO {
    private Long userId;
    private Long routerId;

    public RouterRentalDTO() {
        // Default constructor
    }

    public RouterRentalDTO(Long userId, Long routerId) {
        this.userId = userId;
        this.routerId = routerId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRouterId() {
        return routerId;
    }

    public void setRouterId(Long routerId) {
        this.routerId = routerId;
    }
}

