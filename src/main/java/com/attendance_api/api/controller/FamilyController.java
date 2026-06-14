package com.attendance_api.api.controller;

import com.attendance_api.api.dto.FamilyDTO;
import com.attendance_api.api.dto.FamilyFilterDTO;
import com.attendance_api.api.dto.FamilySearchResponseDTO;
import com.attendance_api.api.swagger.FamilyControllerSwagger;
import com.attendance_api.core.dto.RoleOption;
import com.attendance_api.domain.entity.Family;
import com.attendance_api.domain.mapper.FamilyMapper;
import com.attendance_api.domain.service.FamilyService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/family")
@RequiredArgsConstructor
public class FamilyController implements FamilyControllerSwagger {
    private final FamilyService familyService;
    private final FamilyMapper familyMapper;

    @Override
    @PostMapping
    public ResponseEntity<FamilyDTO> registerFamily(@Valid @RequestBody FamilyDTO familyDTO) {
        Family familyEntity = familyMapper.toEntity(familyDTO);
        FamilyDTO savedFamily = familyMapper.toDto(familyService.saveFamily(familyEntity));
        return ResponseEntity.ok(savedFamily);
    }

    @Override
    @GetMapping("/{key}")
    @RolesAllowed({RoleOption.ADMIN, RoleOption.MEMBER})
    public ResponseEntity<FamilyDTO> getFamilyByKey(@PathVariable String key) {
        FamilyDTO family = familyMapper.toDto(familyService.getFamilyByKey(key));
        return ResponseEntity.ok(family);
    }

    @GetMapping("/search")
    @RolesAllowed({RoleOption.ADMIN, RoleOption.MEMBER})
    public ResponseEntity<Page<FamilySearchResponseDTO>> getFamilies(
            @ModelAttribute FamilyFilterDTO filter,
            @ParameterObject @PageableDefault(size = 20, sort = "familyId", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(
                familyService.searchFamilies(filter, pageable).map(familyMapper::toDTO)
        );
    }
}
