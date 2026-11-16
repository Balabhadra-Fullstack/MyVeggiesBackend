
package com.fresh.veggies.Services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.fresh.veggies.DTO.*;
import com.fresh.veggies.Entity.*;
import com.fresh.veggies.Repository.CommissionPurchaseRepo;
import com.fresh.veggies.Repository.PurchaseRepo;
import com.fresh.veggies.Repository.SaleRepo;

@Service
public class ReportExcelService {

    private final PurchaseRepo pRepo;
    private final CommissionPurchaseRepo cRepo;
    private final SaleRepo sRepo;

    public ReportExcelService(
            PurchaseRepo pRepo,
            CommissionPurchaseRepo cRepo,
            SaleRepo sRepo) {

        this.pRepo = pRepo;
        this.cRepo = cRepo;
        this.sRepo = sRepo;
    }

    // MAIN METHOD
    public byte[] generateExcel(
            String reportType,
            String subType,
            Long id,
            LocalDate startDate,
            LocalDate endDate) {

        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Invalid date range");
        }

        if (reportType.equalsIgnoreCase("supplier")) {

            if ("normal".equalsIgnoreCase(subType)) {

                List<Purchase> purchases =
                        pRepo.findBySupplierIdAndEntryDateBetweenOrderByEntryDateDesc(
                                id, startDate, endDate);

                List<PurchaseResponseDTO> response = purchases.stream()
                        .map(this::convertDTO)
                        .toList();

                return generateNormalPurchaseExcel(response);
            }

            if ("commission".equalsIgnoreCase(subType)) {

                List<CommissionPurchase> purchases =
                        cRepo.findBySupplierIdAndPurchaseDateBetweenOrderByPurchaseDateDesc(
                                id, startDate, endDate);

                List<CommissionResponse> response = purchases.stream()
                        .map(this::convertToResponse)
                        .toList();

                return generateCommissionPurchaseExcel(response);
            }
        }

        if (reportType.equalsIgnoreCase("customer")) {

            List<Sale> sales =
                    sRepo.findByCustomerIdAndSaleDateBetweenOrderBySaleDateDesc(
                            id, startDate, endDate);

            List<SaleResponseDTO> response = sales.stream()
                    .map(this::convertToResponseDTO)
                    .toList();

            return generateCustomerSaleExcel(response);
        }

        throw new IllegalArgumentException("Invalid report type or subtype");
    }

    // COMMON HEADER STYLE
    private CellStyle createHeaderStyle(Workbook workbook) {

        CellStyle style = workbook.createCellStyle();

        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());

        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.DARK_GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        return style;
    }

    private void createHeaders(
            Sheet sheet,
            Workbook workbook,
            String[] headers) {

        Row headerRow = sheet.createRow(0);
        headerRow.setHeightInPoints(25);

        CellStyle headerStyle = createHeaderStyle(workbook);

        for (int i = 0; i < headers.length; i++) {

            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
    }

    private void autoSizeColumns(Sheet sheet, int count) {

        for (int i = 0; i < count; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void setText(Row row, int column, String value) {
        row.createCell(column).setCellValue(value == null ? "" : value);
    }

    private void setNumber(Row row, int column, Number value) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value == null ? 0 : value.doubleValue());
    }

    // NORMAL PURCHASE EXCEL
    private byte[] generateNormalPurchaseExcel(
            List<PurchaseResponseDTO> purchases) {

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Normal Purchase");

            String[] headers = {
                    "Purchase ID", "Date", "Supplier", "Vehicle",
                    "Truck Weight", "Vegetable", "KG",
                    "Purchase Price", "Item Total", "Net Amount"
            };

            createHeaders(sheet, workbook, headers);

            int rowIndex = 1;

            for (PurchaseResponseDTO purchase : purchases) {

                for (PurcahseItemResDTO item : purchase.getPurchaseItems()) {

                    Row row = sheet.createRow(rowIndex++);

                    setNumber(row, 0, purchase.getPurchaseId());
                    setText(row, 1, String.valueOf(purchase.getEntryDate()));
                    setText(row, 2, purchase.getSuppliername());
                    setText(row, 3, purchase.getVehicleNumber());
                    setNumber(row, 4, purchase.getTruckWeight());
                    setText(row, 5, item.getVegetableName());
                    setNumber(row, 6, item.getKg());
                    setNumber(row, 7, item.getPurchasePrice());
                    setNumber(row, 8, item.getTotalAmount());
                    setNumber(row, 9, purchase.getNetAmount());
                }
            }

            autoSizeColumns(sheet, headers.length);

            workbook.write(output);
            return output.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Error generating normal purchase Excel", e);
        }
    }

    // COMMISSION PURCHASE EXCEL
    private byte[] generateCommissionPurchaseExcel(
            List<CommissionResponse> purchases) {

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Commission Purchase");

            String[] headers = {
                    "Purchase ID", "Date", "Supplier", "Truck Number",
                    "Vegetable", "Quantity", "Rate", "Item Amount",
                    "Commission", "Truck Charges", "Labour Charges",
                    "Other Charges", "Supplier Payable"
            };

            createHeaders(sheet, workbook, headers);

            int rowIndex = 1;

            for (CommissionResponse purchase : purchases) {

                for (CommissionResItemsDTO item : purchase.getItems()) {

                    Row row = sheet.createRow(rowIndex++);

                    setNumber(row, 0, purchase.getId());
                    setText(row, 1, String.valueOf(purchase.getDate()));
                    setText(row, 2, purchase.getSuppliername());
                    setText(row, 3, purchase.getTrucknumber());
                    setText(row, 4, item.getVegetablename());
                    setNumber(row, 5, item.getQuantity());
                    setNumber(row, 6, item.getRate());
                    setNumber(row, 7, item.getAmount());
                    setNumber(row, 8, purchase.getCommission());
                    setNumber(row, 9, purchase.getTruckcharges());
                    setNumber(row, 10, purchase.getLabourcharges());
                    setNumber(row, 11, purchase.getOthercharges());
                    setNumber(row, 12, purchase.getSupplierpayable());
                }
            }

            autoSizeColumns(sheet, headers.length);

            workbook.write(output);
            return output.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Error generating commission purchase Excel", e);
        }
    }

    // CUSTOMER SALE EXCEL
    private byte[] generateCustomerSaleExcel(
            List<SaleResponseDTO> sales) {

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Customer Sales");

            String[] headers = {
                    "Sale ID", "Date", "Customer", "Vegetable",
                    "KG", "Sale Price", "Item Total", "Total Sale Amount"
            };

            createHeaders(sheet, workbook, headers);

            int rowIndex = 1;

            for (SaleResponseDTO sale : sales) {

                for (SaleItemResDTO item : sale.getSaleItems()) {

                    Row row = sheet.createRow(rowIndex++);

                    setNumber(row, 0, sale.getSaleId());
                    setText(row, 1, String.valueOf(sale.getSaleDate()));
                    setText(row, 2, sale.getCustomername());
                    setText(row, 3, item.getVegetableName());
                    setNumber(row, 4, item.getKg());
                    setNumber(row, 5, item.getSalePrice());
                    setNumber(row, 6, item.getTotalAmount());
                    setNumber(row, 7, sale.getTotalsaleAmount());
                }
            }

            autoSizeColumns(sheet, headers.length);

            workbook.write(output);
            return output.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Error generating customer sale Excel", e);
        }
    }

    // NORMAL PURCHASE DTO CONVERSION
    public PurchaseResponseDTO convertDTO(Purchase purchase) {

        PurchaseResponseDTO pr = new PurchaseResponseDTO();

        Supplier supplier = purchase.getSupplier();

        pr.setEntryDate(purchase.getEntryDate());
        pr.setSuppliername(supplier.getName());
        pr.setPurchaseId(purchase.getId());
        pr.setVehicleNumber(purchase.getVehiclename());
        pr.setTruckWeight(purchase.getTruckWeight());
        pr.setNetAmount(purchase.getNetAmount());

        List<PurcahseItemResDTO> items = purchase.getPurchaseItems()
                .stream()
                .map(item -> {

                    PurcahseItemResDTO response = new PurcahseItemResDTO();

                    Vegetable vegetable = item.getVegetable();

                    response.setVegetableName(vegetable.getName());
                    response.setKg(item.getKg());
                    response.setPurchasePrice(item.getPurchasePrice());
                    response.setTotalAmount(item.getTotalAmount());

                    return response;
                })
                .toList();

        pr.setPurchaseItems(items);

        return pr;
    }

    // COMMISSION DTO CONVERSION
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
                .map(item -> {

                    CommissionResItemsDTO dto = new CommissionResItemsDTO();

                    dto.setVegetablename(item.getVegetable().getName());
                    dto.setQuantity(item.getQuantity());
                    dto.setRate(item.getRate());
                    dto.setAmount(item.getAmount());

                    return dto;
                })
                .toList();

        response.setItems(items);

        return response;
    }

    // SALE DTO CONVERSION
    public SaleResponseDTO convertToResponseDTO(Sale sale) {

        SaleResponseDTO response = new SaleResponseDTO();

        response.setSaleId(sale.getId());
        response.setCustomername(sale.getCustomer().getName());
        response.setSaleDate(sale.getSaleDate());
        response.setTotalsaleAmount(sale.getTotalsaleamount());

        List<SaleItemResDTO> items = sale.getItems()
                .stream()
                .map(item -> {

                    SaleItemResDTO dto = new SaleItemResDTO();

                    dto.setVegetableName(item.getVegitable().getName());
                    dto.setKg(item.getKg());
                    dto.setSalePrice(item.getPrice());
                    dto.setTotalAmount(item.getTotalunitprice());

                    return dto;
                })
                .toList();

        response.setSaleItems(items);

        return response;
    }
}
