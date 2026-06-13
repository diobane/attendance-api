package com.attendance_api.domain.service;

import com.attendance_api.domain.entity.Team;
import com.attendance_api.domain.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    public List<Team> getTeams() {
        return teamRepository.findAll();
    }
}
