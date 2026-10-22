package com.fresh.veggies.Exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GolbalExceptionHandler {
    
@ExceptionHandler(SupplierNotFoundException.class)
public ResponseEntity<?>supplierNotFound(SupplierNotFoundException ex){
    Map<String,String>map=new HashMap<>();
    map.put("message", ex.getMessage());
    
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
}
@ExceptionHandler(CustomerNotFoundException.class)
public ResponseEntity<?>supplierNotFound(CustomerNotFoundException ex){
    Map<String,String>map=new HashMap<>();
    map.put("message",ex.getMessage());
    
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
}

}
