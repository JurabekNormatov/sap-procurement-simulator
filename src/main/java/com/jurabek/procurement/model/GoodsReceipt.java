package com.jurabek.procurement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "goods_receipts")
public class GoodsReceipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gr_number", unique = true, nullable = false)
    private String grNumber;

    @ManyToOne
    @JoinColumn(name = "purchase_order_id", nullable = false)
    private PurchaseOrder purchaseOrder;

    @NotNull
    @DecimalMin("0.1")
    @Column(name = "received_quantity", nullable = false)
    private BigDecimal receivedQuantity;

    @NotBlank
    @Column(name = "received_by", nullable = false)
    private String receivedBy;

    @Column(name = "received_at")
    private LocalDateTime receivedAt;

    private String notes;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        POSTED,
        REVERSED
    }

    @PrePersist
    public void prePersist() {
        this.receivedAt = LocalDateTime.now();
        this.status = Status.POSTED;
        this.grNumber = "GR-" + System.currentTimeMillis();
    }
}