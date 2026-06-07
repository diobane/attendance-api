package com.attendance_api.api.controller;

import com.attendance_api.api.dto.ChildFilterDTO;
import com.attendance_api.api.dto.ChildResponseDTO;
import com.attendance_api.api.swagger.ChildControllerSwagger;
import com.attendance_api.core.dto.RoleOption;
import com.attendance_api.domain.service.ChildService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/child")
@RequiredArgsConstructor
public class ChildController implements ChildControllerSwagger {
    private final ChildService childService;

    @Override
    @GetMapping("/registered-count")
    @RolesAllowed({RoleOption.ADMIN, RoleOption.MEMBER})
    public ResponseEntity<Long> getRegisteredChildren() {
        Long count = childService.getRegisteredChildren();
        return ResponseEntity.ok(count);
    }

    @Override
    @GetMapping("/search")
    @RolesAllowed({RoleOption.ADMIN, RoleOption.MEMBER})
    public ResponseEntity<List<ChildResponseDTO>> searchChildren(
            @ModelAttribute ChildFilterDTO filter) {
        return ResponseEntity.ok(childService.searchChildren(filter));
    }
}
