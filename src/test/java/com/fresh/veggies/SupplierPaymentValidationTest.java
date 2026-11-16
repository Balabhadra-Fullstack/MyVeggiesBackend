package com.fresh.veggies;

import com.fresh.veggies.Controller.SupplierPaymentController;
import com.fresh.veggies.DTO.SupplierpaymentReq;
import com.fresh.veggies.Exception.GolbalExceptionHandler;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SupplierPaymentValidationTest {

    @Test
    void requestRequiresSupplierAndValidPaymentDetails() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            SupplierpaymentReq request = new SupplierpaymentReq();
            request.setId(0L);
            request.setPaymentamount(BigDecimal.ZERO);
            request.setPaymentmethod(" ");

            Set<String> invalidFields = validator.validate(request).stream()
                    .map(violation -> violation.getPropertyPath().toString())
                    .collect(Collectors.toSet());

            assertEquals(Set.of("id", "paymentamount", "paymentmethod", "paymenttime"), invalidFields);
        }
    }

    @Test
    void validationExceptionReturnsBadRequestWithFieldErrors() throws NoSuchMethodException {
        SupplierpaymentReq request = new SupplierpaymentReq();
        BeanPropertyBindingResult bindingResult =
                new BeanPropertyBindingResult(request, "supplierPayment");
        bindingResult.addError(new FieldError(
                "supplierPayment", "paymentamount", null, false, null, null,
                "Payment amount must be greater than zero"));
        MethodParameter parameter = new MethodParameter(
                SupplierPaymentController.class.getMethod(
                        "addSupplierPayment", SupplierpaymentReq.class), 0);
        MethodArgumentNotValidException exception =
                new MethodArgumentNotValidException(parameter, bindingResult);

        var response = new GolbalExceptionHandler().handleValidationExceptions(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Payment amount must be greater than zero",
                response.getBody().get("paymentamount"));
    }
}
