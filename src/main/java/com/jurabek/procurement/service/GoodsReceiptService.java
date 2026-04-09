package com.jurabek.procurement.service;

import com.jurabek.procurement.model.GoodsReceipt;
import com.jurabek.procurement.model.PurchaseOrder;
import com.jurabek.procurement.repository.GoodsReceiptRepository;
import com.jurabek.procurement.repository.PurchaseOrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodsReceiptService {

    private final GoodsReceiptRepository repository;
    private final PurchaseOrderRepository purchaseOrderRepository;

    @Transactional
    public GoodsReceipt create(GoodsReceipt goodsReceipt) {
        PurchaseOrder order = purchaseOrderRepository
                .findById(goodsReceipt.getPurchaseOrder().getId())
                .orElseThrow(() -> new EntityNotFoundException("Bestellung nicht gefunden"));

        if (order.getStatus() == PurchaseOrder.Status.CANCELLED) {
            throw new IllegalStateException("Wareneingang für stornierte Bestellung nicht möglich");
        }

        order.setStatus(PurchaseOrder.Status.DELIVERED);
        purchaseOrderRepository.save(order);

        goodsReceipt.setPurchaseOrder(order);
        return repository.save(goodsReceipt);
    }

    public List<GoodsReceipt> findAll() {
        return repository.findAll();
    }

    public GoodsReceipt findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Wareneingang nicht gefunden: " + id));
    }

    public List<GoodsReceipt> findByPurchaseOrderId(Long purchaseOrderId) {
        return repository.findByPurchaseOrderId(purchaseOrderId);
    }
}