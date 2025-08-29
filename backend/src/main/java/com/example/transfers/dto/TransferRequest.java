package com.example.transfers.dto;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class TransferRequest {

    @NotBlank
    @Size(max = 120)
    private String fullName;

    @NotBlank
    @Pattern(
            regexp = "^(\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}|\\d{11})$",
            message = "CPF deve estar no formato 000.000.000-00 ou somente 11 dígitos."
    )
    private String cpf;

    @Pattern(regexp = "^[0-9]{10}$", message = "Conta de origem deve ter 10 digitos (XXXXXXXXXX).")
    @NotBlank
    private String sourceAccount;

    @Pattern(regexp = "^[0-9]{10}$", message = "Conta de destino deve ter 10 digitos (XXXXXXXXXX).")
    @NotBlank
    private String targetAccount;

    @NotNull @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero.")
    private BigDecimal amount;

    @NotNull
    private LocalDate transferDate;

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getSourceAccount() { return sourceAccount; }
    public void setSourceAccount(String sourceAccount) { this.sourceAccount = sourceAccount; }

    public String getTargetAccount() { return targetAccount; }
    public void setTargetAccount(String targetAccount) { this.targetAccount = targetAccount; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public LocalDate getTransferDate() { return transferDate; }
    public void setTransferDate(LocalDate transferDate) { this.transferDate = transferDate; }
}
