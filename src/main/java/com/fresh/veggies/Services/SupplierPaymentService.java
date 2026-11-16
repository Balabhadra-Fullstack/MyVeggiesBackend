package com.fresh.veggies.Services;

import com.fresh.veggies.DTO.SupplierpaymentReq;
import com.fresh.veggies.DTO.SupplierPaymentResponseDTO;
import com.fresh.veggies.Entity.Supplier;
import com.fresh.veggies.Entity.SupplierPayment;
import com.fresh.veggies.Exception.SupplierNotFoundException;
import com.fresh.veggies.Repository.SupplierPaymentRepo;
import com.fresh.veggies.Repository.SupplierRepo;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SupplierPaymentService {

    private final SupplierPaymentRepo supplierPaymentRepository;
    private final SupplierRepo repo;
    public SupplierPaymentService(SupplierPaymentRepo supplierPaymentRepository,SupplierRepo repo) {
        this.supplierPaymentRepository = supplierPaymentRepository;
        this.repo=repo;
    }
    @Transactional
    public SupplierPayment addSupplierPayment(SupplierpaymentReq supplierPayment) {
                                       Supplier supplier=repo.findById(supplierPayment.getId()).orElseThrow(()->new SupplierNotFoundException("Supplier Not Found With This Id"+supplierPayment.getId()));
                        SupplierPayment payment = new SupplierPayment();
                        payment.setSupplier(supplier);
                        payment.setPaymentAmount(supplierPayment.getPaymentamount());
                        payment.setPaymentMethod(supplierPayment.getPaymentmethod());
                        payment.setPaymentDateTime(supplierPayment.getPaymenttime());
        return supplierPaymentRepository.save(payment);
    }

    public List<SupplierPayment> getAllSupplierPayments() {
        return supplierPaymentRepository.findAll();
    }

    public SupplierPayment getSupplierPaymentById(Long id) {
        return supplierPaymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier payment not found with id: " + id));
    }

    public SupplierPayment updateSupplierPayment(Long id, SupplierPayment supplierPayment) {
        SupplierPayment existingPayment = getSupplierPaymentById(id);

        existingPayment.setSupplier(supplierPayment.getSupplier());
        existingPayment.setPaymentAmount(supplierPayment.getPaymentAmount());
        existingPayment.setPaymentMethod(supplierPayment.getPaymentMethod());
        existingPayment.setPaymentDateTime(supplierPayment.getPaymentDateTime());

        return supplierPaymentRepository.save(existingPayment);
    }

    public void deleteSupplierPayment(Long id) {
        SupplierPayment existingPayment = getSupplierPaymentById(id);
        supplierPaymentRepository.delete(existingPayment);
    }

    public List<SupplierPaymentResponseDTO> last3PaymentBySupplierId(Long supplierId) {
        List<SupplierPayment> payments = supplierPaymentRepository.findTop3BySupplierIdOrderByPaymentDateTimeDesc(supplierId);
        return payments.stream().map(this::convertToDTO).toList();
    }

    public SupplierPaymentResponseDTO convertToDTO(SupplierPayment payment) {
        SupplierPaymentResponseDTO dto = new SupplierPaymentResponseDTO();
        dto.setPaymentId(payment.getId());
        dto.setSupplierId(payment.getSupplier().getId());
        dto.setSupplierName(payment.getSupplier().getName());
        dto.setAmount(payment.getPaymentAmount());
        dto.setMethod(payment.getPaymentMethod());
        dto.setDateTime(payment.getPaymentDateTime());
        return dto;
    }
    public Page<SupplierPaymentResponseDTO> getSupplierPaymentsBySupplierId(Long supplierId, org.springframework.data.domain.Pageable pageable,LocalDate startDate, LocalDate endDate) {
        Page<SupplierPayment> paymentsPage = supplierPaymentRepository.findBySupplierIdAndPaymentDateTimeBetweenOrderByPaymentDateTimeDesc(supplierId, startDate, endDate, pageable);
        return paymentsPage.map(this::convertToDTO);
    }
    public SupplierPayment getByPaymentId(Long paymenId){
        return supplierPaymentRepository.findById(paymenId).orElseThrow(()->new RuntimeException("Supplier Payment Not Found With This Id"+paymenId));
    }
}