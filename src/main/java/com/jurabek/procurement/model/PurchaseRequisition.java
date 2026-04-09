package com.jurabek.procurement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "purchase_requisitions")
public class PurchaseRequisition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "banf_number", unique = true, nullable = false)
    private String banfNumber;

    @NotBlank
    @Column(name = "material_description", nullable = false)
    private String materialDescription;

    @NotNull
    @DecimalMin("0.1")
    private BigDecimal quantity;

    @NotBlank
    private String unit;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal price;

    @NotBlank
    @Column(name = "requested_by", nullable = false)
    private String requestedBy;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "needed_by")
    private LocalDate neededBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private String notes;

    public enum Status {
        OPEN,
        APPROVED,
        REJECTED,
        ORDERED
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.status = Status.OPEN;
        this.banfNumber = "BANF-" + System.currentTimeMillis();
    }
}