package com.fresh.veggies.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
public class CommissionResponse {
    public Long id;
    public String suppliername;
    public String trucknumber;
    public BigDecimal commission;
    public LocalDate date;
    public List<CommissionResItemsDTO> items;
    public BigDecimal truckcharges;
    public BigDecimal labourcharges;
    public BigDecimal othercharges;
    public BigDecimal supplierpayable;
}
