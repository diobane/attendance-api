package com.attendance_api.api.controller;

import com.attendance_api.core.dto.RoleOption;
import com.attendance_api.domain.service.ChildService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/child")
@RequiredArgsConstructor
public class ChildController {
    private final ChildService childService;

    @GetMapping("/registered-count")
    @RolesAllowed({RoleOption.ADMIN, RoleOption.MEMBER})
    public ResponseEntity<Long> getRegisteredChildren() {
        Long count = childService.getRegisteredChildren();
        return ResponseEntity.ok(count);
    }
}
