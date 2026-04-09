package com.jurabek.procurement.controller;

import com.jurabek.procurement.model.PurchaseRequisition;
import com.jurabek.procurement.service.PurchaseRequisitionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requisitions")
@RequiredArgsConstructor
public class PurchaseRequisitionController {

    private final PurchaseRequisitionService service;

    @PostMapping
    public ResponseEntity<PurchaseRequisition> create(@Valid @RequestBody PurchaseRequisition requisition) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(requisition));
    }

    @GetMapping
    public ResponseEntity<List<PurchaseRequisition>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseRequisition> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<PurchaseRequisition> approve(@PathVariable Long id) {
        return ResponseEntity.ok(service.approve(id));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<PurchaseRequisition> reject(@PathVariable Long id) {
        return ResponseEntity.ok(service.reject(id));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PurchaseRequisition>> findByStatus(@PathVariable PurchaseRequisition.Status status) {
        return ResponseEntity.ok(service.findByStatus(status));
    }
}