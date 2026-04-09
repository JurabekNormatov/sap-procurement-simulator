package com.jurabek.procurement.service;

import com.jurabek.procurement.model.Invoice;
import com.jurabek.procurement.model.PurchaseOrder;
import com.jurabek.procurement.repository.InvoiceRepository;
import com.jurabek.procurement.repository.PurchaseOrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository repository;
    private final PurchaseOrderRepository purchaseOrderRepository;

    @Transactional
    public Invoice create(Invoice invoice) {
        PurchaseOrder order = purchaseOrderRepository
                .findById(invoice.getPurchaseOrder().getId())
                .orElseThrow(() -> new EntityNotFoundException("Bestellung nicht gefunden"));

        if (order.getStatus() != PurchaseOrder.Status.DELIVERED) {
            throw new IllegalStateException("Rechnung nur nach Wareneingang möglich");
        }

        invoice.setPurchaseOrder(order);
        return repository.save(invoice);
    }

    public List<Invoice> findAll() {
        return repository.findAll();
    }

    public Invoice findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rechnung nicht gefunden: " + id));
    }

    @Transactional
    public Invoice verify(Long id) {
        Invoice invoice = findById(id);

        if (invoice.getStatus() != Invoice.Status.PENDING) {
            throw new IllegalStateException("Nur offene Rechnungen können geprüft werden");
        }

        invoice.setStatus(Invoice.Status.VERIFIED);
        return repository.save(invoice);
    }

    @Transactional
    public Invoice pay(Long id) {
        Invoice invoice = findById(id);

        if (invoice.getStatus() != Invoice.Status.VERIFIED) {
            throw new IllegalStateException("Nur geprüfte Rechnungen können bezahlt werden");
        }

        invoice.setStatus(Invoice.Status.PAID);
        invoice.setPaidAt(LocalDateTime.now());
        return repository.save(invoice);
    }

    @Transactional
    public Invoice block(Long id) {
        Invoice invoice = findById(id);

        if (invoice.getStatus() == Invoice.Status.PAID) {
            throw new IllegalStateException("Bezahlte Rechnungen können nicht gesperrt werden");
        }

        invoice.setStatus(Invoice.Status.BLOCKED);
        return repository.save(invoice);
    }

    public List<Invoice> findByStatus(Invoice.Status status) {
        return repository.findByStatus(status);
    }
}