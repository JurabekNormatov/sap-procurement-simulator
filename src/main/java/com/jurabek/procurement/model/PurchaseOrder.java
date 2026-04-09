package com.jurabek.procurement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "purchase_orders")
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "po_number", unique = true, nullable = false)
    private String poNumber;

    @ManyToOne
    @JoinColumn(name = "requisition_id", nullable = false)
    private PurchaseRequisition requisition;

    @NotBlank
    @Column(name = "supplier_name", nullable = false)
    private String supplierName;

    @NotBlank
    @Column(name = "supplier_email")
    private String supplierEmail;

    @NotNull
    @DecimalMin("0.0")
    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;

    private String notes;

    public enum Status {
        OPEN,
        SENT,
        CONFIRMED,
        DELIVERED,
        CANCELLED
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.status = Status.OPEN;
        this.poNumber = "PO-" + System.currentTimeMillis();
    }
}