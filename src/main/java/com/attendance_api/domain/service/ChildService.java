package com.attendance_api.domain.service;

import com.attendance_api.api.dto.ChildFilterDTO;
import com.attendance_api.api.dto.ChildResponseDTO;
import com.attendance_api.domain.entity.Child;
import com.attendance_api.domain.mapper.ChildMapper;
import com.attendance_api.domain.repository.ChildRepository;
import com.attendance_api.domain.specification.ChildSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChildService {
    private final ChildRepository childRepository;
    private final ChildMapper childMapper;

    public Long getRegisteredChildren() {
        return childRepository.count();
    }

    @Transactional(readOnly = true)
    public List<ChildResponseDTO> searchChildren(ChildFilterDTO filter) {
        List<Child> children = childRepository.findAll(
                ChildSpecification.withFilter(filter)
        );
        return childMapper.toDTOList(children);
    }
}
