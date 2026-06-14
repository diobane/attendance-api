package com.attendance_api.domain.specification;

import com.attendance_api.api.dto.FamilyFilterDTO;
import com.attendance_api.domain.entity.Family;
import org.springframework.data.jpa.domain.Specification;

public class FamilySpecification {
    public static Specification<Family> withFilter(FamilyFilterDTO filter) {
        return (root, query, cb) -> Specification.where(hasFamilyId(filter.getFamilyId()))
                .toPredicate(root, query, cb);
    }

    private static Specification<Family> hasFamilyId(String familyId) {
        return (root, query, cb) -> {
            if (familyId == null || familyId.isBlank()) {
                return cb.conjunction();
            }
            return cb.equal(root.get("fullName"), familyId);
        };
    }
}
