
package com.example.transfers.controller;

import com.example.transfers.dto.*;
import com.example.transfers.repository.*;
import com.example.transfers.service.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/transfers")
@Validated
public class TransferController {

    private final TransferService service;
    private final TransferRepository repository;

    public TransferController(TransferService service, TransferRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<TransferResponse> schedule(@Valid @RequestBody TransferRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.schedule(request));
    }

    @GetMapping
    public List<TransferResponse> list() {
        return repository.findAll().stream().map(service::toResponse).collect(Collectors.toList());
    }

    @GetMapping("/quote")
    public QuoteResponse quote(@RequestParam BigDecimal amount,
                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate transferDate) {
        var zone = java.time.ZoneId.of("America/Sao_Paulo");
        LocalDate today = LocalDate.now(zone);
        var fee = FeeCalculator.calculate(amount, today, transferDate);
        return new QuoteResponse(fee, amount.subtract(fee));
    }
}
