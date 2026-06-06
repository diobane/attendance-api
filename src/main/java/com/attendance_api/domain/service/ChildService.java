package com.attendance_api.domain.service;

import com.attendance_api.domain.repository.ChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChildService {
    private final ChildRepository childRepository;

    public Long getRegisteredChildren() {
        return childRepository.count();
    }
}
