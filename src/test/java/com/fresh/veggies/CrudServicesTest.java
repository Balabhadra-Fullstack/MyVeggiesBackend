package com.fresh.veggies;

import com.fresh.veggies.DTO.SaleRequest;
import com.fresh.veggies.DTO.addVeggies;
import com.fresh.veggies.Entity.*;
import com.fresh.veggies.Repository.*;
import com.fresh.veggies.Services.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CrudServicesTest {

    @Mock
    private OwnerRepo ownerRepo;

    @Mock
    private VegetableRepo vegetableRepo;

  
    @Mock
    private VegCategoryRepo vegCategoryRepo;

    @Mock
    private SaleRepo saleRepo;

    @Mock
    private CustomerRepo customerRepo;

    @Mock
    private CrateEntryRepo crateEntryRepo;

    @Mock
    private VegetableRepo vegetableRepository;

    @InjectMocks
    private OwnerService ownerService;

    @InjectMocks
    private VegetableService vegetableService;



    @InjectMocks
    private VegCategoryService vegCategoryService;

    @InjectMocks
    private SaleService saleService;

    @Test
    void ownerService_shouldReturnSavedOwner() {
        Owner owner = new Owner();
        owner.setId(1L);
        owner.setName("Aman");

        when(ownerRepo.save(owner)).thenReturn(owner);

        assertEquals(owner, ownerService.addOwner(owner));
    }

    @Test
    void vegetableService_shouldReturnAllVegetables() {
        Vegetable vegetable = new Vegetable();
        vegetable.setId(1L);
        vegetable.setName("Tomato");

        when(vegetableRepo.findAll()).thenReturn(List.of(vegetable));

        assertEquals(1, vegetableService.getAllVegetables().size());
    }

    @Test
    void vegetableService_patchShouldPreserveOmittedFields() {
        Vegetable vegetable = new Vegetable();
        vegetable.setId(1L);
        vegetable.setName("Tomato");
        vegetable.setOdianame("Tamatar");
        vegetable.setImage("old-image");

        addVeggies patch = new addVeggies();
        patch.setImage("new-image");

        when(vegetableRepo.findById(1L)).thenReturn(java.util.Optional.of(vegetable));
        when(vegetableRepo.save(vegetable)).thenReturn(vegetable);

        Vegetable updated = vegetableService.patchVegetable(1L, patch);

        assertEquals("Tomato", updated.getName());
        assertEquals("Tamatar", updated.getOdianame());
        assertEquals("new-image", updated.getImage());
        verify(vegetableRepo).save(vegetable);
    }


    @Test
    void vegCategoryService_shouldReturnSavedCategory() {
        VegCategory category = new VegCategory();
        category.setId(1L);
        category.setName("Leafy");

        when(vegCategoryRepo.save(category)).thenReturn(category);

        assertEquals(category, vegCategoryService.addVegCategory(category));
    }

    @Test
    void addSale_shouldLinkCrateEntryToSavedSale() {
        Customer customer = new Customer();
        customer.setId(1L);
        SaleRequest request = new SaleRequest();
        request.setCustomerId(1L);
        request.setSaleDate(LocalDate.of(2026, 10, 12));
        request.setCrateCount(new BigDecimal("4"));

        when(customerRepo.findById(1L)).thenReturn(Optional.of(customer));
        when(saleRepo.save(any(Sale.class))).thenAnswer(invocation -> {
            Sale sale = invocation.getArgument(0);
            sale.setId(42L);
            return sale;
        });

        saleService.addSale(request);

        ArgumentCaptor<CrateEntry> crateEntryCaptor = ArgumentCaptor.forClass(CrateEntry.class);
        verify(crateEntryRepo).save(crateEntryCaptor.capture());
        CrateEntry savedCrateEntry = crateEntryCaptor.getValue();
        assertEquals(42L, savedCrateEntry.getSale().getId());
        assertSame(customer, savedCrateEntry.getCustomer());
        assertEquals(new BigDecimal("4"), savedCrateEntry.getQuantity());
    }

    @Test
    void updateSale_shouldUpdateItsExistingCrateEntry() {
        Customer customer = new Customer();
        customer.setId(2L);
        Sale sale = new Sale();
        sale.setId(42L);
        CrateEntry crateEntry = new CrateEntry();
        crateEntry.setSale(sale);
        crateEntry.setQuantity(BigDecimal.ONE);

        SaleRequest request = new SaleRequest();
        request.setCustomerId(2L);
        request.setSaleDate(LocalDate.of(2026, 10, 12));
        request.setCrateCount(new BigDecimal("3"));

        when(saleRepo.findById(42L)).thenReturn(Optional.of(sale));
        when(customerRepo.findById(2L)).thenReturn(Optional.of(customer));
        when(crateEntryRepo.findBySaleId(42L)).thenReturn(Optional.of(crateEntry));
        when(saleRepo.save(sale)).thenReturn(sale);

        saleService.updateSale(42L, request);

        verify(crateEntryRepo).save(crateEntry);
        assertSame(sale, crateEntry.getSale());
        assertSame(customer, crateEntry.getCustomer());
        assertEquals(new BigDecimal("3"), crateEntry.getQuantity());
        assertEquals(request.getSaleDate(), crateEntry.getEntryDate());
        verify(crateEntryRepo, never()).delete(crateEntry);
    }

    @Test
    void updateSale_shouldDeleteCrateEntryWhenCrateCountIsRemoved() {
        Sale sale = new Sale();
        sale.setId(42L);
        CrateEntry crateEntry = new CrateEntry();
        SaleRequest request = new SaleRequest();
        request.setCustomerId(1L);

        when(saleRepo.findById(42L)).thenReturn(Optional.of(sale));
        when(customerRepo.findById(1L)).thenReturn(Optional.of(new Customer()));
        when(crateEntryRepo.findBySaleId(42L)).thenReturn(Optional.of(crateEntry));
        when(saleRepo.save(sale)).thenReturn(sale);

        saleService.updateSale(42L, request);

        verify(crateEntryRepo).delete(crateEntry);
        verify(crateEntryRepo, never()).save(any(CrateEntry.class));
    }

    @Test
    void deleteSale_shouldDeleteLinkedCrateEntryBeforeSale() {
        Sale sale = new Sale();
        sale.setId(42L);
        CrateEntry crateEntry = new CrateEntry();
        when(saleRepo.findById(42L)).thenReturn(Optional.of(sale));
        when(crateEntryRepo.findBySaleId(42L)).thenReturn(Optional.of(crateEntry));

        saleService.deleteSale(42L);

        org.mockito.InOrder order = org.mockito.Mockito.inOrder(crateEntryRepo, saleRepo);
        order.verify(crateEntryRepo).delete(crateEntry);
        order.verify(saleRepo).delete(sale);
    }
}
