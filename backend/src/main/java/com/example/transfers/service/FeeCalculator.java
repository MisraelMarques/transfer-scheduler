
package com.example.transfers.service;

import com.example.transfers.exception.FeeNotApplicableException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class FeeCalculator {

    public static BigDecimal calculate(BigDecimal amount, LocalDate scheduledDate, LocalDate transferDate) {
        long days = ChronoUnit.DAYS.between(scheduledDate, transferDate);
        if (days < 0) {
            throw new FeeNotApplicableException("Data de transferencia nao pode ser anterior a hoje.");
        }

        BigDecimal fee;
        if (days == 0) {
            fee = BigDecimal.valueOf(3.00).add(amount.multiply(BigDecimal.valueOf(0.025)));
        } else if (days >= 1 && days <= 10) {
            fee = BigDecimal.valueOf(12.00);
        } else if (days >= 11 && days <= 20) {
            fee = amount.multiply(BigDecimal.valueOf(0.082));
        } else if (days >= 21 && days <= 30) {
            fee = amount.multiply(BigDecimal.valueOf(0.069));
        } else if (days >= 31 && days <= 40) {
            fee = amount.multiply(BigDecimal.valueOf(0.047));
        } else if (days >= 41 && days <= 50) {
            fee = amount.multiply(BigDecimal.valueOf(0.017));
        } else {
            throw new FeeNotApplicableException("Sem taxa aplicavel para a data informada.");
        }
        return fee.setScale(2, RoundingMode.HALF_UP);
    }
}
