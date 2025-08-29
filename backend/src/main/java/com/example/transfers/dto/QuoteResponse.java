
package com.example.transfers.dto;

import java.math.BigDecimal;

public class QuoteResponse {
    private BigDecimal fee;
    private BigDecimal total;

    public QuoteResponse(BigDecimal fee, BigDecimal total) {
        this.fee = fee;
        this.total = total;
    }

    public BigDecimal getFee() { return fee; }
    public BigDecimal getTotal() { return total; }
}
