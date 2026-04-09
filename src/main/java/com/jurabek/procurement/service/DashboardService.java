package com.jurabek.procurement.service;

import com.jurabek.procurement.dto.DashboardDto;
import com.jurabek.procurement.model.Invoice;
import com.jurabek.procurement.model.PurchaseOrder;
import com.jurabek.procurement.model.PurchaseRequisition;
import com.jurabek.procurement.repository.InvoiceRepository;
import com.jurabek.procurement.repository.PurchaseOrderRepository;
import com.jurabek.procurement.repository.PurchaseRequisitionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final PurchaseRequisitionRepository requisitionRepository;
    private final PurchaseOrderRepository orderRepository;
    private final InvoiceRepository invoiceRepository;

    public DashboardDto getDashboard() {
        List<PurchaseRequisition> allRequisitions = requisitionRepository.findAll();
        List<PurchaseOrder> allOrders = orderRepository.findAll();
        List<Invoice> allInvoices = invoiceRepository.findAll();

        long openRequisitions = allRequisitions.stream()
                .filter(r -> r.getStatus() == PurchaseRequisition.Status.OPEN)
                .count();

        long approvedRequisitions = allRequisitions.stream()
                .filter(r -> r.getStatus() == PurchaseRequisition.Status.APPROVED)
                .count();

        long openOrders = allOrders.stream()
                .filter(o -> o.getStatus() == PurchaseOrder.Status.OPEN)
                .count();

        long deliveredOrders = allOrders.stream()
                .filter(o -> o.getStatus() == PurchaseOrder.Status.DELIVERED)
                .count();

        long pendingInvoices = allInvoices.stream()
                .filter(i -> i.getStatus() == Invoice.Status.PENDING)
                .count();

        long paidInvoices = allInvoices.stream()
                .filter(i -> i.getStatus() == Invoice.Status.PAID)
                .count();

        BigDecimal totalOrderValue = allOrders.stream()
                .map(PurchaseOrder::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPaidValue = allInvoices.stream()
                .filter(i -> i.getStatus() == Invoice.Status.PAID)
                .map(Invoice::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return DashboardDto.builder()
                .openRequisitions(openRequisitions)
                .approvedRequisitions(approvedRequisitions)
                .totalRequisitions(allRequisitions.size())
                .openOrders(openOrders)
                .deliveredOrders(deliveredOrders)
                .totalOrders(allOrders.size())
                .pendingInvoices(pendingInvoices)
                .paidInvoices(paidInvoices)
                .totalInvoices(allInvoices.size())
                .totalOrderValue(totalOrderValue)
                .totalPaidValue(totalPaidValue)
                .build();
    }
}