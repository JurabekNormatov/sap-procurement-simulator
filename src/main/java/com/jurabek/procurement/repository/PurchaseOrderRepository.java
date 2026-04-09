package com.jurabek.procurement.repository;

import com.jurabek.procurement.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseOrderRepository
        extends JpaRepository<PurchaseOrder, Long> {

    Optional<PurchaseOrder> findByPoNumber(String poNumber);

    List<PurchaseOrder> findByStatus(PurchaseOrder.Status status);

    List<PurchaseOrder> findByRequisitionId(Long requisitionId);
}