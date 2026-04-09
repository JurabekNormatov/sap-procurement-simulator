package com.jurabek.procurement.controller;

import com.jurabek.procurement.model.PurchaseOrder;
import com.jurabek.procurement.service.PurchaseOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class PurchaseOrderController {

    private final PurchaseOrderService service;

    @PostMapping
    public ResponseEntity<PurchaseOrder> create(@Valid @RequestBody PurchaseOrder order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(order));
    }

    @GetMapping
    public ResponseEntity<List<PurchaseOrder>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrder> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<PurchaseOrder> confirm(@PathVariable Long id) {
        return ResponseEntity.ok(service.confirm(id));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<PurchaseOrder> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(service.cancel(id));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PurchaseOrder>> findByStatus(@PathVariable PurchaseOrder.Status status) {
        return ResponseEntity.ok(service.findByStatus(status));
    }
}