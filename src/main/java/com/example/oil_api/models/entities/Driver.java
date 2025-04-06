package com.example.oil_api.models.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SoftDelete;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@Entity
@SoftDelete
public class Driver extends User {

    @OneToOne(cascade = CascadeType.ALL)
    private Car car;

    private BigDecimal balance = BigDecimal.ZERO;

    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY) //lazy nie jest przypadkiem domyslnym?
    private Set<Pickup> pickups;

    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY)
    private Set<Expense> expenses;

}
