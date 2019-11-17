package com.tradeshift.amazing.persistence.repository;

import com.tradeshift.amazing.domain.entity.TreeHierarchy;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface HierarchyRepository extends CrudRepository<TreeHierarchy, UUID> {
}
