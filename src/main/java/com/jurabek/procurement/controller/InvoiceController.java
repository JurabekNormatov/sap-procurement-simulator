package com.jurabek.procurement.controller;

import com.jurabek.procurement.model.Invoice;
import com.jurabek.procurement.service.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService service;

    @PostMapping
    public ResponseEntity<Invoice> create(@Valid @RequestBody Invoice invoice) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(invoice));
    }

    @GetMapping
    public ResponseEntity<List<Invoice>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}/verify")
    public ResponseEntity<Invoice> verify(@PathVariable Long id) {
        return ResponseEntity.ok(service.verify(id));
    }

    @PutMapping("/{id}/pay")
    public ResponseEntity<Invoice> pay(@PathVariable Long id) {
        return ResponseEntity.ok(service.pay(id));
    }

    @PutMapping("/{id}/block")
    public ResponseEntity<Invoice> block(@PathVariable Long id) {
        return ResponseEntity.ok(service.block(id));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Invoice>> findByStatus(@PathVariable Invoice.Status status) {
        return ResponseEntity.ok(service.findByStatus(status));
    }
}