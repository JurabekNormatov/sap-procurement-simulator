package com.jurabek.procurement.service;

import com.jurabek.procurement.model.PurchaseRequisition;
import com.jurabek.procurement.repository.PurchaseRequisitionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseRequisitionService {

    private final PurchaseRequisitionRepository repository;

    public PurchaseRequisition create(PurchaseRequisition requisition) {
        return repository.save(requisition);
    }

    public List<PurchaseRequisition> findAll() {
        return repository.findAll();
    }

    public PurchaseRequisition findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BANF nicht gefunden: " + id));
    }

    public PurchaseRequisition approve(Long id) {
        PurchaseRequisition requisition = findById(id);

        if (requisition.getStatus() != PurchaseRequisition.Status.OPEN) {
            throw new IllegalStateException("Nur offene BANFen können genehmigt werden");
        }

        requisition.setStatus(PurchaseRequisition.Status.APPROVED);
        return repository.save(requisition);
    }

    public PurchaseRequisition reject(Long id) {
        PurchaseRequisition requisition = findById(id);

        if (requisition.getStatus() != PurchaseRequisition.Status.OPEN) {
            throw new IllegalStateException("Nur offene BANFen können abgelehnt werden");
        }

        requisition.setStatus(PurchaseRequisition.Status.REJECTED);
        return repository.save(requisition);
    }

    public List<PurchaseRequisition> findByStatus(PurchaseRequisition.Status status) {
        return repository.findByStatus(status);
    }
}