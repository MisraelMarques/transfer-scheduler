package com.example.transfers.service;

import com.example.transfers.dto.*;
import com.example.transfers.model.Transfer;
import com.example.transfers.repository.TransferRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;


@Service
public class TransferService {

    private final TransferRepository repository;

    public TransferService(TransferRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public TransferResponse schedule(TransferRequest req) {
        var zone = java.time.ZoneId.of("America/Sao_Paulo");

        var scheduledAt = java.time.ZonedDateTime.now(zone).toOffsetDateTime();
        LocalDate today = scheduledAt.toLocalDate();

        BigDecimal fee = FeeCalculator.calculate(req.getAmount(), today, req.getTransferDate());
        BigDecimal total = req.getAmount().subtract(fee);


        if (total.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Valor da transferência não pode ser menor ou igual à taxa."
            );
        }

        Transfer entity = new Transfer(
                req.getFullName(),
                req.getCpf(),
                req.getSourceAccount(),
                req.getTargetAccount(),
                req.getAmount(),
                fee,
                req.getTransferDate(),
                scheduledAt
        );
        entity = repository.save(entity);
        return toResponse(entity);
    }

    public TransferResponse toResponse(Transfer t) {
        return new TransferResponse(
                t.getId(),
                t.getFullName(),
                t.getCpf(),
                t.getSourceAccount(),
                t.getTargetAccount(),
                t.getAmount(),
                t.getFee(),
                t.getAmount().subtract(t.getFee()),
                t.getTransferDate(),
                t.getScheduledAt()
        );
    }
}
