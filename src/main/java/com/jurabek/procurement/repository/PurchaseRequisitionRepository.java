package com.jurabek.procurement.repository;

import com.jurabek.procurement.model.PurchaseRequisition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRequisitionRepository
        extends JpaRepository<PurchaseRequisition, Long> {

    Optional<PurchaseRequisition> findByBanfNumber(String banfNumber);

    List<PurchaseRequisition> findByStatus(PurchaseRequisition.Status status);

    List<PurchaseRequisition> findByRequestedBy(String requestedBy);
}