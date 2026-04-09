package com.jurabek.procurement.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class DashboardDto {

    private long openRequisitions;
    private long approvedRequisitions;
    private long totalRequisitions;

    private long openOrders;
    private long deliveredOrders;
    private long totalOrders;

    private long pendingInvoices;
    private long paidInvoices;
    private long totalInvoices;

    private BigDecimal totalOrderValue;
    private BigDecimal totalPaidValue;
}