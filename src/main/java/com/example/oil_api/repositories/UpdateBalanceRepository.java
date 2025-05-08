package com.example.oil_api.repositories;

import com.example.oil_api.common.BalanceModifier;
import com.example.oil_api.models.entities.UpdateBalance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface UpdateBalanceRepository extends JpaRepository<UpdateBalance, Integer> {

    @Query("SELECT COALESCE(SUM(ub.modificationAmount), 0) " +
            "FROM UpdateBalance ub " +
            "WHERE ub.driver.id = :driverId AND ub.modifier = :modifier AND ub.modificationDate = :date")
    BigDecimal findSumByDriverIdAndOperationAndDate(
            @Param("driverId") int driverId,
            @Param("modifier") BalanceModifier modifier,
            @Param("date") LocalDate date);

    Page<UpdateBalance> findAllByDriver(int driverId, Pageable pageable);
}
