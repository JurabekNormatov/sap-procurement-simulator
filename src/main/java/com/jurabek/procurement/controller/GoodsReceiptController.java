package com.jurabek.procurement.controller;

import com.jurabek.procurement.model.GoodsReceipt;
import com.jurabek.procurement.service.GoodsReceiptService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goods-receipts")
@RequiredArgsConstructor
public class GoodsReceiptController {

    private final GoodsReceiptService service;

    @PostMapping
    public ResponseEntity<GoodsReceipt> create(@Valid @RequestBody GoodsReceipt goodsReceipt) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(goodsReceipt));
    }

    @GetMapping
    public ResponseEntity<List<GoodsReceipt>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoodsReceipt> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/order/{purchaseOrderId}")
    public ResponseEntity<List<GoodsReceipt>> findByPurchaseOrderId(@PathVariable Long purchaseOrderId) {
        return ResponseEntity.ok(service.findByPurchaseOrderId(purchaseOrderId));
    }
}