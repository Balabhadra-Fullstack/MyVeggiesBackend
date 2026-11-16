package com.fresh.veggies.Services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fresh.veggies.DTO.CommissionPurchaseItemDTO;
import com.fresh.veggies.DTO.CommissionResItemsDTO;
import com.fresh.veggies.DTO.CommissionResponse;
import com.fresh.veggies.DTO.CommisionRequest;
import com.fresh.veggies.Entity.CommissionPurchase;
import com.fresh.veggies.Entity.CommissionPurchaseItems;
import com.fresh.veggies.Entity.Supplier;
import com.fresh.veggies.Entity.Vegetable;
import com.fresh.veggies.Repository.CommissionPurchaseRepo;
import com.fresh.veggies.Repository.SupplierRepo;
import com.fresh.veggies.Repository.VegetableRepo;

@Service
public class CommissionPurchaseService {

    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);

    private final CommissionPurchaseRepo commissionPurchaseRepo;
    private final SupplierRepo supplierRepo;
    private final VegetableRepo vegetableRepo;

    public CommissionPurchaseService(CommissionPurchaseRepo commissionPurchaseRepo,
            SupplierRepo supplierRepo, VegetableRepo vegetableRepo) {
        this.commissionPurchaseRepo = commissionPurchaseRepo;
        this.supplierRepo = supplierRepo;
        this.vegetableRepo = vegetableRepo;
    }

    @Transactional
    public CommissionPurchase addCommissionPurchase(CommisionRequest request) {
        Supplier supplier = getSupplier(request.getSupplierId());
        CommissionPurchase commissionPurchase = new CommissionPurchase();
        commissionPurchase.setSupplier(supplier);
        applyRequest(commissionPurchase, request);
        return commissionPurchaseRepo.save(commissionPurchase);
    }

    public List<CommissionPurchase> getAllCommissionPurchases() {
        return commissionPurchaseRepo.findAll();
    }

    public CommissionPurchase getCommissionPurchaseById(Long id) {
        return commissionPurchaseRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Commission purchase not found with id: " + id));
    }

    public List<CommissionPurchase> getCommissionPurchasesBySupplierId(Long supplierId) {
        return commissionPurchaseRepo.findBySupplierId(supplierId);
    }

    public List<CommissionPurchase> getCommissionPurchasesByDate(LocalDate date) {
        return commissionPurchaseRepo.findByPurchaseDate(date);
    }

    @Transactional(readOnly = true)
    public Page<CommissionResponse> getdata(Long supplierId, LocalDate startDate, LocalDate endDate,
            Pageable pageable) {
        return commissionPurchaseRepo
                .findBySupplierIdAndPurchaseDateBetweenOrderByPurchaseDateDesc(supplierId, startDate, endDate, pageable)
                .map(this::convertToResponse);
    }

    @Transactional
    public CommissionPurchase updateCommissionPurchase(Long id, CommisionRequest request) {
        CommissionPurchase commissionPurchase = getCommissionPurchaseById(id);
        commissionPurchase.setSupplier(getSupplier(request.getSupplierId()));
        applyRequest(commissionPurchase, request);
        return commissionPurchaseRepo.save(commissionPurchase);
    }

    public void deleteCommissionPurchase(Long id) {
        commissionPurchaseRepo.delete(getCommissionPurchaseById(id));
    }

    private Supplier getSupplier(Long supplierId) {
        return supplierRepo.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + supplierId));
    }

    private void applyRequest(CommissionPurchase commissionPurchase, CommisionRequest request) {
        commissionPurchase.setCommissionRate(valueOrZero(request.getCommissionRate()));
        commissionPurchase.setTruckCharges(valueOrZero(request.getTruckcharges()));
        commissionPurchase.setLaborCharges(valueOrZero(request.getLaborCharges()));
        commissionPurchase.setOtherCharges(valueOrZero(request.getOtherCharges()));
        commissionPurchase.setTruckNumber(request.getTruckNumber());
        commissionPurchase.setPurchaseDate(request.getDate());
        commissionPurchase.getItems().clear();

        BigDecimal itemTotal = BigDecimal.ZERO;
        for (CommissionPurchaseItemDTO itemDTO : request.getItems()) {
            Vegetable vegetable = vegetableRepo.findById(itemDTO.getVegetableId())
                    .orElseThrow(() -> new RuntimeException(
                            "Vegetable not found with id: " + itemDTO.getVegetableId()));

            BigDecimal amount = itemDTO.getQuantity().multiply(itemDTO.getRate());
            CommissionPurchaseItems item = new CommissionPurchaseItems();
            item.setCommissionPurchase(commissionPurchase);
            item.setVegetable(vegetable);
            item.setQuantity(itemDTO.getQuantity());
            item.setRate(itemDTO.getRate());
            item.setAmount(amount);
            commissionPurchase.getItems().add(item);
            itemTotal = itemTotal.add(amount);
        }

        BigDecimal commission = itemTotal.multiply(commissionPurchase.getCommissionRate()).divide(ONE_HUNDRED);
        BigDecimal charges = commissionPurchase.getTruckCharges()
                .add(commissionPurchase.getLaborCharges())
                .add(commissionPurchase.getOtherCharges());
        commissionPurchase.setSupplierpayableAmount(itemTotal.subtract(commission).subtract(charges));
    }

    private BigDecimal valueOrZero(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }
    public List<CommissionResponse> getlast3CommissionPurchasesBySupplierId(Long supplierId) {
        List<CommissionPurchase> purchases = commissionPurchaseRepo.findTop3BySupplierIdOrderByPurchaseDateDesc(supplierId);
        return purchases.stream().map(this::convertToResponse).toList();
    }
    public List<CommissionResponse> getlast30CommissionPurchasesBySupplierId(Long supplierId) {
        List<CommissionPurchase> purchases = commissionPurchaseRepo.findTop30BySupplierIdOrderByPurchaseDateDesc(supplierId);
        return purchases.stream().map(this::convertToResponse).toList();
    }
    public CommissionResponse convertToResponse(CommissionPurchase purchase) {
        CommissionResponse response = new CommissionResponse();
        response.setId(purchase.getId());
        response.setSuppliername(purchase.getSupplier().getName());
        response.setTrucknumber(purchase.getTruckNumber());
        response.setCommission(purchase.getCommissionRate());
        response.setDate(purchase.getPurchaseDate());
        response.setTruckcharges(purchase.getTruckCharges());
        response.setLabourcharges(purchase.getLaborCharges());
        response.setOthercharges(purchase.getOtherCharges());
        response.setSupplierpayable(purchase.getSupplierpayableAmount());

    List<CommissionResItemsDTO> items = purchase.getItems()
    .stream()
    .<CommissionResItemsDTO>map(item -> {
        CommissionResItemsDTO itemDTO = new CommissionResItemsDTO();

        itemDTO.setVegetablename(item.getVegetable().getOdianame());
        itemDTO.setQuantity(item.getQuantity());
        itemDTO.setRate(item.getRate());
        itemDTO.setAmount(item.getAmount());

        return itemDTO;
    })
    .toList();
        response.setItems(items);

        return response;
    }
}