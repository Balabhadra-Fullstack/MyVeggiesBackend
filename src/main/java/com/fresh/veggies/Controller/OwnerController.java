package com.fresh.veggies.Controller;

import com.fresh.veggies.Entity.Owner;
import com.fresh.veggies.DTO.ApiResponse;
import com.fresh.veggies.DTO.OwnerResponseDTO;
import com.fresh.veggies.Services.OwnerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<OwnerResponseDTO>> addOwner(@RequestBody Owner owner) {
        Owner addedOwner = ownerService.addOwner(owner);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Owner added successfully", toResponse(addedOwner)));
    }

    @GetMapping("/getall")
    public ResponseEntity<List<OwnerResponseDTO>> getAllOwners() {
        return ResponseEntity.ok(ownerService.getAllOwners().stream().map(this::toResponse).toList());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<OwnerResponseDTO> getOwnerById(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(ownerService.getOwnerById(id)));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<OwnerResponseDTO>> updateOwner(@PathVariable Long id, @RequestBody Owner owner) {
        Owner updatedOwner = ownerService.updateOwner(id, owner);
        return ResponseEntity.ok(new ApiResponse<>("Owner updated successfully", toResponse(updatedOwner)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteOwner(@PathVariable Long id) {
        ownerService.deleteOwner(id);
        return ResponseEntity.ok(new ApiResponse<>("Owner deleted successfully", "Owner with id " + id + " has been deleted"));
    }

    private OwnerResponseDTO toResponse(Owner owner) {
        return new OwnerResponseDTO(owner.getId(), owner.getName(), owner.getEmail(),
                owner.getPhoneNumber(), owner.getAddress());
    }
}
