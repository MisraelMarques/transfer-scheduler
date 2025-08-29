
package com.example.transfers.repository;

import com.example.transfers.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, Long> { }
