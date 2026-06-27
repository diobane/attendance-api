package com.attendance_api.api.controller;

import com.attendance_api.api.dto.ChildDetailsResponseDTO;
import com.attendance_api.api.dto.ChildFilterDTO;
import com.attendance_api.api.dto.ChildResponseDTO;
import com.attendance_api.api.swagger.ChildControllerSwagger;
import com.attendance_api.core.dto.RoleOption;
import com.attendance_api.domain.mapper.ChildMapper;
import com.attendance_api.domain.service.AttendanceService;
import com.attendance_api.domain.service.ChildService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/child")
@RequiredArgsConstructor
public class ChildController implements ChildControllerSwagger {
    private final ChildService childService;
    private final ChildMapper childMapper;
    private final AttendanceService attendanceService;

    @Override
    @GetMapping("/registered-count")
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
        Page<ChildResponseDTO> page = childService.searchChildren(filter, pageable).map(childMapper::toDTO);

        // Anexa o horário de entrada/saída de hoje a cada criança.
        Map<Long, AttendanceService.TodayStatus> status = attendanceService.getTodayStatusByChild();
        page.forEach(dto -> {
            AttendanceService.TodayStatus s = status.get(dto.getChildId());
            if (s != null) {
                dto.setCheckinAt(s.checkinAt());
                dto.setCheckoutAt(s.checkoutAt());
            }
        });

        return ResponseEntity.ok(page);
    }

    @Override
    @GetMapping("/details/{childId}")
    @RolesAllowed({RoleOption.ADMIN, RoleOption.MEMBER})
    public ResponseEntity<ChildDetailsResponseDTO> getChildDetails(@PathVariable Long childId) {
        return ResponseEntity.ok(childMapper.toDetailsDTO(childService.getChildDetailsByChildId(childId)));
    }

    @Override
    @PutMapping("/{childId}/team")
    @RolesAllowed({RoleOption.ADMIN, RoleOption.MEMBER})
    public ResponseEntity<Void> updateChildTeam(@PathVariable Long childId, @RequestParam Long teamId) {
        childService.updateChildTeamByChildIdAndTeamId(childId, teamId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{childId}/checkin")
    @RolesAllowed({RoleOption.ADMIN, RoleOption.MEMBER})
    public ResponseEntity<Void> registerCheckin(@PathVariable Long childId, @RequestParam(required = false) String familyKey) {
        childService.registerCheckin(childId, familyKey);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{childId}/checkout")
    @RolesAllowed({RoleOption.ADMIN, RoleOption.MEMBER})
    public ResponseEntity<Void> registerCheckout(@PathVariable Long childId, @RequestParam String familyKey) {
        childService.registerCheckout(childId, familyKey);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{childId}/checkin")
    @RolesAllowed({RoleOption.ADMIN, RoleOption.MEMBER})
    public ResponseEntity<Void> removeCheckin(@PathVariable Long childId, @RequestParam(required = false) String familyKey) {
        childService.removeCheckin(childId, familyKey);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{childId}/checkout")
    @RolesAllowed({RoleOption.ADMIN, RoleOption.MEMBER})
    public ResponseEntity<Void> removeCheckout(@PathVariable Long childId, @RequestParam String familyKey) {
        childService.removeCheckout(childId, familyKey);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
