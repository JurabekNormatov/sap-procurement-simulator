package com.jurabek.procurement.service;

import com.jurabek.procurement.model.PurchaseOrder;
import com.jurabek.procurement.model.PurchaseRequisition;
import com.jurabek.procurement.repository.PurchaseOrderRepository;
import com.jurabek.procurement.repository.PurchaseRequisitionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseOrderService {

    private final PurchaseOrderRepository repository;
    private final PurchaseRequisitionRepository requisitionRepository;

    @Transactional
    public PurchaseOrder create(PurchaseOrder order) {
        PurchaseRequisition requisition = requisitionRepository.findById(order.getRequisition().getId())
                .orElseThrow(() -> new EntityNotFoundException("BANF nicht gefunden"));

        if (requisition.getStatus() != PurchaseRequisition.Status.APPROVED) {
            throw new IllegalStateException("BANF muss zuerst genehmigt werden");
        }

        requisition.setStatus(PurchaseRequisition.Status.ORDERED);
        requisitionRepository.save(requisition);

        order.setRequisition(requisition);
        return repository.save(order);
    }

    public List<PurchaseOrder> findAll() {
        return repository.findAll();
    }

    public PurchaseOrder findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bestellung nicht gefunden: " + id));
    }

    @Transactional
    public PurchaseOrder confirm(Long id) {
        PurchaseOrder order = findById(id);

        if (order.getStatus() != PurchaseOrder.Status.OPEN) {
            throw new IllegalStateException("Nur offene Bestellungen können bestätigt werden");
        }

        order.setStatus(PurchaseOrder.Status.CONFIRMED);
        return repository.save(order);
    }

    @Transactional
    public PurchaseOrder cancel(Long id) {
        PurchaseOrder order = findById(id);

        if (order.getStatus() == PurchaseOrder.Status.DELIVERED) {
            throw new IllegalStateException("Gelieferte Bestellungen können nicht storniert werden");
        }

        order.setStatus(PurchaseOrder.Status.CANCELLED);
        return repository.save(order);
    }

    public List<PurchaseOrder> findByStatus(PurchaseOrder.Status status) {
        return repository.findByStatus(status);
    }
}