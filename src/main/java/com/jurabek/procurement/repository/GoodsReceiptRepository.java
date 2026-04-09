package com.jurabek.procurement.repository;

import com.jurabek.procurement.model.GoodsReceipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsReceiptRepository
        extends JpaRepository<GoodsReceipt, Long> {

    List<GoodsReceipt> findByPurchaseOrderId(Long purchaseOrderId);
}