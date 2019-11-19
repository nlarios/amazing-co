package com.tradeshift.amazing.persistence.repository;

import com.tradeshift.amazing.domain.entity.Hierarchy;
import com.tradeshift.amazing.domain.entity.HierarchyKey;
import com.tradeshift.amazing.domain.entity.Node;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HierarchyRepository extends CrudRepository<Hierarchy, HierarchyKey> {
    @Query("SELECT h FROM Hierarchy h WHERE h.hierarchyKey.descendant.id = ?1")
    List<Hierarchy> findAllAncestorsById(UUID uuid);

    @Query("SELECT h FROM Hierarchy h WHERE h.hierarchyKey.ancestor.id = ?1 and h.hierarchyKey.descendant.id <> ?1")
    List<Hierarchy> findAllDescendantsById(UUID uuid);

    @Query( "DELETE FROM Hierarchy h WHERE (descendantId IN (?1) AND h.hierarchyKey.ancestor.id <> ?2 AND depth > 0) " +
            "OR (h.hierarchyKey.descendant.id = ?2 AND h.hierarchyKey.ancestor.id <> ?2)")
    void deleteOldHierarchies(List<UUID> ancestors, UUID uuid);

}
