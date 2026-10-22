package com.fresh.veggies.Services;

import com.fresh.veggies.DTO.PurcahseItemResDTO;
import com.fresh.veggies.DTO.PurchaseItemDTO;
import com.fresh.veggies.DTO.PurchaseRequestDTO;
import com.fresh.veggies.DTO.PurchaseResponseDTO;
import com.fresh.veggies.Entity.Purchase;
import com.fresh.veggies.Entity.PurchaseItems;
import com.fresh.veggies.Entity.Supplier;
import com.fresh.veggies.Entity.Vegetable;
import com.fresh.veggies.Exception.SupplierNotFoundException;
import com.fresh.veggies.Repository.PurchaseRepo;
import com.fresh.veggies.Repository.SupplierRepo;
import com.fresh.veggies.Repository.VegetableRepo;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class PurchaseService {

    private final PurchaseRepo purchaseRepository;
    private final SupplierRepo supplierRepository;
    private final VegetableRepo vegrepo;
    

    public PurchaseService(PurchaseRepo purchaseRepository, SupplierRepo supplierRepository,  VegetableRepo vegrepo) {
        this.purchaseRepository = purchaseRepository;
        this.supplierRepository = supplierRepository;
        this.vegrepo = vegrepo;
    
    }
        @Transactional
  public String addPurchase(PurchaseRequestDTO purchase) {

    Supplier supplier = supplierRepository.findById(purchase.getSupplierId())
            .orElseThrow(() ->
                    new SupplierNotFoundException("Supplier not found with id: "
                            + purchase.getSupplierId()));

    Purchase purchaseEntity = new Purchase();

    purchaseEntity.setSupplier(supplier);
    purchaseEntity.setVehiclename(purchase.getVehiclename());
    purchaseEntity.setEntryDate(purchase.getEntryDateTime());
    purchaseEntity.setTruckWeight(purchase.getTruckWeight());

    BigDecimal netAmount = BigDecimal.ZERO;

    for (PurchaseItemDTO itemDTO : purchase.getPurchaseItems()) {

        PurchaseItems purchaseItem = new PurchaseItems();

        Vegetable vegetable = vegrepo.findById(itemDTO.getVegetableId())
                .orElseThrow(() ->
                        new RuntimeException("Vegetable not found with id: "
                                + itemDTO.getVegetableId()));

        purchaseItem.setVegetable(vegetable);
        purchaseItem.setKg(itemDTO.getKg());
        purchaseItem.setPurchasePrice(itemDTO.getPurchasePrice());
        purchaseItem.setPurchase(purchaseEntity);

        // One item's total
        BigDecimal itemTotal =
                itemDTO.getKg().multiply(itemDTO.getPurchasePrice());

        purchaseItem.setTotalAmount(itemTotal);

        // All items total
        netAmount = netAmount.add(itemTotal);

        purchaseEntity.getPurchaseItems().add(purchaseItem);
    }

    // Save total in Purchase
    purchaseEntity.setNetAmount(netAmount);
    
 purchaseRepository.save(purchaseEntity);
 return "Purchase added successfully";

}

    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    public Purchase getPurchaseById(Long id) {
        return purchaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase not found with id: " + id));
    }

    public List<Purchase> getPurchasesBySupplierId(Long supplierId) {
        
        return purchaseRepository.findBySupplierId(supplierId);
    }
  @Transactional
    public Purchase updatePurchase(Long id, PurchaseRequestDTO purchase) {
        Purchase existingPurchase = getPurchaseById(id);
        Supplier supplier = supplierRepository.findById(purchase.getSupplierId())
            .orElseThrow(() ->
                new RuntimeException("Supplier not found with id: "
                    + purchase.getSupplierId()));

        existingPurchase.setEntryDate(purchase.getEntryDateTime());
        existingPurchase.setTruckWeight(purchase.getTruckWeight());
        existingPurchase.setSupplier(supplier);
        existingPurchase.setVehiclename(purchase.getVehiclename());
        BigDecimal netAmount = BigDecimal.ZERO;

        existingPurchase.getPurchaseItems().clear();

    for (PurchaseItemDTO itemDTO : purchase.getPurchaseItems()) {

        PurchaseItems purchaseItem = new PurchaseItems();

        Vegetable vegetable = vegrepo.findById(itemDTO.getVegetableId())
                .orElseThrow(() ->
                        new RuntimeException("Vegetable not found with id: "
                                + itemDTO.getVegetableId()));

        purchaseItem.setVegetable(vegetable);
        purchaseItem.setKg(itemDTO.getKg());
        purchaseItem.setPurchasePrice(itemDTO.getPurchasePrice());
        purchaseItem.setPurchase(existingPurchase);
         BigDecimal itemTotal =
                itemDTO.getKg().multiply(itemDTO.getPurchasePrice());
        netAmount = netAmount.add(itemTotal);
          purchaseItem.setTotalAmount(itemTotal);
        existingPurchase.getPurchaseItems().add(purchaseItem);
    }

        existingPurchase.setNetAmount(netAmount);
        

        return purchaseRepository.save(existingPurchase);
    }

    public void deletePurchase(Long id) {
        Purchase existingPurchase = getPurchaseById(id);
        purchaseRepository.delete(existingPurchase);
    }
    public List<Purchase>getByDate(LocalDate date){
         return purchaseRepository.findByEntryDate(date);
    }
    public List<PurchaseResponseDTO> last3Purchase(Long supplierId){
        List<Purchase> purchase = purchaseRepository.findTop3BySupplierIdOrderByEntryDateDesc(supplierId);
               return purchase.stream().map(this::convertDTO).toList();

    }
    public PurchaseResponseDTO convertDTO(Purchase purchase){
        PurchaseResponseDTO pr= new PurchaseResponseDTO();
                       Supplier supplier =purchase.getSupplier();
        pr.setEntryDate(purchase.getEntryDate());
        pr.setSuppliername(supplier.getName());
        pr.setPurchaseId(purchase.getId());
        pr.setVehicleNumber(purchase.getVehiclename());
        pr.setNetAmount(purchase.getNetAmount());
        List<PurcahseItemResDTO> item = purchase.getPurchaseItems().stream().map( items-> {
                      PurcahseItemResDTO response= new PurcahseItemResDTO();
                        Vegetable veg=items.getVegetable();
                      response.setVegetableName(veg.getName());
                      response.setKg(items.getKg());
                      response.setPurchasePrice(items.getPurchasePrice());
                      response.setTotalAmount(items.getTotalAmount());
                      return response;
        }).toList();

           pr.setPurchaseItems(item);
           return pr;
    }
}
