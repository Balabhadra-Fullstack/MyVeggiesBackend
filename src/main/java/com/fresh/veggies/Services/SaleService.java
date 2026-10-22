package com.fresh.veggies.Services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fresh.veggies.DTO.CrateResponseDTO;
import com.fresh.veggies.DTO.SaleItemRequest;
import com.fresh.veggies.DTO.SaleItemResDTO;
import com.fresh.veggies.DTO.SaleRequest;
import com.fresh.veggies.DTO.SaleResponseDTO;
import com.fresh.veggies.Entity.CrateEntry;
import com.fresh.veggies.Entity.Customer;
import com.fresh.veggies.Entity.Sale;
import com.fresh.veggies.Entity.SaleItems;
import com.fresh.veggies.Entity.Vegetable;
import com.fresh.veggies.Exception.CustomerNotFoundException;
import com.fresh.veggies.Repository.CrateEntryRepo;
import com.fresh.veggies.Repository.CustomerRepo;
import com.fresh.veggies.Repository.SaleRepo;
import com.fresh.veggies.Repository.VegetableRepo;

@Service
public class SaleService {

    private final SaleRepo saleRepository;
    private final CustomerRepo customerRepository;
    private final VegetableRepo vegetableRepository;
    private final CrateEntryRepo crateEntryRepository;

    public SaleService(SaleRepo saleRepository,
                       CustomerRepo customerRepository,
                       VegetableRepo vegetableRepository,
                       CrateEntryRepo crateEntryRepository) {
        this.saleRepository = saleRepository;
        this.customerRepository = customerRepository;
        this.vegetableRepository = vegetableRepository;
        this.crateEntryRepository = crateEntryRepository;
    }

    @Transactional
    public String addSale(SaleRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + request.getCustomerId()));

        Sale sale = new Sale();
        sale.setCustomer(customer);
        sale.setSaleDate(request.getSaleDate());

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (SaleItemRequest itemRequest : request.getSaleItems()) {
            Vegetable vegetable = vegetableRepository.findById(itemRequest.getVegetableId())
                    .orElseThrow(() -> new RuntimeException("Vegetable not found with id: " + itemRequest.getVegetableId()));

            SaleItems saleItem = new SaleItems();
            saleItem.setSale(sale);
            saleItem.setVegitable(vegetable);
            saleItem.setKg(itemRequest.getKg());
            saleItem.setPrice(itemRequest.getSalePrice());

            BigDecimal itemTotal = itemRequest.getKg().multiply(itemRequest.getSalePrice());
            saleItem.setTotalunitprice(itemTotal);
            totalAmount = totalAmount.add(itemTotal);

            sale.getItems().add(saleItem);
        }

        sale.setTotalsaleamount(totalAmount);

        Sale savedSale = saleRepository.save(sale);

        if (request.getCrateCount() != null && request.getCrateCount().compareTo(BigDecimal.ZERO) > 0) {
            CrateEntry crateEntry = new CrateEntry();
            crateEntry.setCustomer(customer);
            crateEntry.setEntryDate(request.getSaleDate());
            crateEntry.setQuantity(request.getCrateCount());
            crateEntry.setSale(savedSale);
            crateEntryRepository.save(crateEntry);
        }

        return "Sale added successfully";
    }

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public Sale getSaleById(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sale not found with id: " + id));
    }

    public List<Sale> getSalesByCustomerId(Long customerId) {
        return saleRepository.findByCustomerId(customerId);
    }

    @Transactional
    public Sale updateSale(Long id, SaleRequest request) {
        Sale existingSale = getSaleById(id);
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + request.getCustomerId()));

        existingSale.setCustomer(customer);
        existingSale.setSaleDate(request.getSaleDate());

        BigDecimal totalAmount = BigDecimal.ZERO;
        existingSale.getItems().clear();

        for (SaleItemRequest itemRequest : request.getSaleItems()) {
            Vegetable vegetable = vegetableRepository.findById(itemRequest.getVegetableId())
                    .orElseThrow(() -> new RuntimeException("Vegetable not found with id: " + itemRequest.getVegetableId()));

            SaleItems saleItem = new SaleItems();
            saleItem.setSale(existingSale);
            saleItem.setVegitable(vegetable);
            saleItem.setKg(itemRequest.getKg());
            saleItem.setPrice(itemRequest.getSalePrice());

            BigDecimal itemTotal = itemRequest.getKg().multiply(itemRequest.getSalePrice());
            saleItem.setTotalunitprice(itemTotal);
            totalAmount = totalAmount.add(itemTotal);

            existingSale.getItems().add(saleItem);
        }

        existingSale.setTotalsaleamount(totalAmount);
        updateSaleCrateEntry(existingSale, customer, request);
        return saleRepository.save(existingSale);
    }

    public List<Sale> getByDate(LocalDate date) {
        return saleRepository.findBySaleDate(date);
    }

    public List<SaleResponseDTO> last3Sale(Long customerId) {
        List<Sale> sales = saleRepository.findTop3ByCustomerIdOrderBySaleDateDesc(customerId);
        return sales.stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    @Transactional
    public void deleteSale(Long id) {
        Sale sale = getSaleById(id);
        crateEntryRepository.findBySaleId(id).ifPresent(crateEntryRepository::delete);
        saleRepository.delete(sale);
    }

    private void updateSaleCrateEntry(Sale sale, Customer customer, SaleRequest request) {
        CrateEntry crateEntry = crateEntryRepository.findBySaleId(sale.getId()).orElse(null);
        if (request.getCrateCount() != null && request.getCrateCount().compareTo(BigDecimal.ZERO) > 0) {
            if (crateEntry == null) {
                crateEntry = new CrateEntry();
                crateEntry.setSale(sale);
            }
            crateEntry.setCustomer(customer);
            crateEntry.setEntryDate(request.getSaleDate());
            crateEntry.setQuantity(request.getCrateCount());
            crateEntryRepository.save(crateEntry);
        } else if (crateEntry != null) {
            crateEntryRepository.delete(crateEntry);
        }
    }
    public SaleResponseDTO convertToResponseDTO(Sale sale) {
        SaleResponseDTO responseDTO = new SaleResponseDTO();
        responseDTO.setSaleId(sale.getId());
        responseDTO.setCustomername(sale.getCustomer().getName());
        responseDTO.setSaleDate(sale.getSaleDate());
        responseDTO.setTotalsaleAmount(sale.getTotalsaleamount());

        List<SaleItemResDTO> saleItems = sale.getItems().stream().map(item -> {
            SaleItemResDTO itemResDTO = new SaleItemResDTO();
            itemResDTO.setVegetableName(item.getVegitable().getName());
            itemResDTO.setKg(item.getKg());
            itemResDTO.setSalePrice(item.getPrice());
            itemResDTO.setTotalAmount(item.getTotalunitprice());
            return itemResDTO;
        }).collect(Collectors.toList());

        responseDTO.setSaleItems(saleItems);
        return responseDTO;
    }
    public List<CrateResponseDTO> getCrateDetailsByCustomerId(Long customerId) {
        return saleRepository.findCrateDetailsByCustomerId(customerId);
    }
}
