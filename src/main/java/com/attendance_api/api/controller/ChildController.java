package com.attendance_api.api.controller;

import com.attendance_api.api.dto.ChildDetailsResponseDTO;
import com.attendance_api.api.dto.ChildFilterDTO;
import com.attendance_api.api.dto.ChildResponseDTO;
import com.attendance_api.api.swagger.ChildControllerSwagger;
import com.attendance_api.core.dto.RoleOption;
import com.attendance_api.domain.mapper.ChildMapper;
import com.attendance_api.domain.service.ChildService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/child")
@RequiredArgsConstructor
public class ChildController implements ChildControllerSwagger {
    private final ChildService childService;
    private final ChildMapper childMapper;

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
    public ResponseEntity<Page<ChildResponseDTO>> searchChildren(
            @ModelAttribute ChildFilterDTO filter,
            @ParameterObject @PageableDefault(size = 20, sort = "fullName", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(
                childService.searchChildren(filter, pageable).map(childMapper::toDTO)
        );
    }

    @Override
    @GetMapping("/details/{childId}")
    @RolesAllowed({RoleOption.ADMIN, RoleOption.MEMBER})
    public ResponseEntity<ChildDetailsResponseDTO> getChildDetails(@PathVariable Long childId) {
        return ResponseEntity.ok(childMapper.toDetailsDTO(childService.getChildDetailsByChildId(childId)));
    }
}
