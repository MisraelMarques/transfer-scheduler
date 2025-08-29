package com.example.transfers.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

public class TransferResponse {
    private Long id;
    private String fullName;
    private String cpf;
    private String sourceAccount;
    private String targetAccount;
    private BigDecimal amount;
    private BigDecimal fee;
    private BigDecimal total;
    private LocalDate transferDate;
    private OffsetDateTime scheduledAt;

    public TransferResponse() { }

    public TransferResponse(Long id, String fullName, String cpf,
                            String sourceAccount, String targetAccount,
                            BigDecimal amount, BigDecimal fee, BigDecimal total,
                            LocalDate transferDate, OffsetDateTime scheduledAt) {
        this.id = id;
        this.fullName = fullName;
        this.cpf = cpf;
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
        this.amount = amount;
        this.fee = fee;
        this.total = total;
        this.transferDate = transferDate;
        this.scheduledAt = scheduledAt;
    }

    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getCpf() { return cpf; }
    public String getSourceAccount() { return sourceAccount; }
    public String getTargetAccount() { return targetAccount; }
    public BigDecimal getAmount() { return amount; }
    public BigDecimal getFee() { return fee; }
    public BigDecimal getTotal() { return total; }
    public LocalDate getTransferDate() { return transferDate; }
    public OffsetDateTime getScheduledAt() { return scheduledAt; }
}
