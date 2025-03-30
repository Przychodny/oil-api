package com.example.oil_api.repositories;

import com.example.oil_api.models.entities.UpdateBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface UpdateBalanceRepository extends JpaRepository<UpdateBalance, Integer> {

    @Query("SELECT COALESCE(SUM(ub.modificationAmount), 0) " +
            "FROM UpdateBalance ub " +
            "WHERE ub.driver.id = :driverId AND ub.modificationDate = :date")
    BigDecimal findSumByDriverIdAndModificationDate(@Param("driverId") int driverId, @Param("date") LocalDate date);
}
