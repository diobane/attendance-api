package com.attendance_api.api.controller;

import com.attendance_api.api.dto.TeamDTO;
import com.attendance_api.api.swagger.TeamControllerSwagger;
import com.attendance_api.core.dto.RoleOption;
import com.attendance_api.domain.entity.Team;
import com.attendance_api.domain.mapper.TeamMapper;
import com.attendance_api.domain.service.TeamService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/team")
@RequiredArgsConstructor
public class TeamController implements TeamControllerSwagger {
    private final TeamService teamService;
    private final TeamMapper teamMapper;

    @Override
    @GetMapping("/all")
    @RolesAllowed({RoleOption.ADMIN, RoleOption.MEMBER})
    public ResponseEntity<List<TeamDTO>> getTeams() {
        List<Team> teams = teamService.getTeams();
        return ResponseEntity.ok(teamMapper.toDTOList(teams));
    }
}
