package com.attendance_api.domain.specification;

import com.attendance_api.api.dto.ChildFilterDTO;
import com.attendance_api.domain.entity.Child;
import org.springframework.data.jpa.domain.Specification;

public class ChildSpecification {

    public static Specification<Child> withFilter(ChildFilterDTO filter) {
        return Specification.where(hasFullName(filter.getFullName()));
    }

    private static Specification<Child> hasFullName(String fullName) {
        return (root, query, cb) -> {
            if (fullName == null || fullName.isBlank()) {
                return cb.conjunction(); // when no filter is applied -> return every record
            }
            return cb.like(
                    cb.lower(root.get("fullName")),
                    "%" + fullName.toLowerCase() + "%"
            );
        };
    }
}
