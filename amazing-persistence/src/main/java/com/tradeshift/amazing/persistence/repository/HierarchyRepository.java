package com.tradeshift.amazing.persistence.repository;

import com.tradeshift.amazing.domain.entity.Hierarchy;
import com.tradeshift.amazing.domain.entity.HierarchyKey;
import com.tradeshift.amazing.domain.entity.Node;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;


import java.util.List;

@Repository
public interface HierarchyRepository extends CrudRepository<Hierarchy, HierarchyKey> {
    @Query(value = "SELECT h FROM Hierarchy h WHERE h.hierarchyKey.descendant.id = ?1"  )
    List<Hierarchy> findAllAncestorsById(Long uuid);

    @Query(value = "SELECT h FROM Hierarchy h WHERE h.hierarchyKey.ancestor.id = ?1 and h.hierarchyKey.descendant.id <> ?1")
    List<Hierarchy> findAllDescendantsById(Long uuid);

    @Modifying(clearAutomatically = true)
    @Query( "DELETE FROM Hierarchy h WHERE (h.hierarchyKey.descendant.id IN ?1  AND h.hierarchyKey.ancestor.id <> ?2 AND h.depth > 0 ) " +
            "OR (h.hierarchyKey.descendant.id = ?2 AND h.hierarchyKey.ancestor.id <> ?2)")
    void deleteOldHierarchies(List<Long> descendants, Long uuid);

    @Query("SELECT h FROM Hierarchy h WHERE h.hierarchyKey.ancestor.id = ?1 or h.hierarchyKey.descendant.id = ?1")
    List<Hierarchy> findAllHierarchiesById(Long uuid);

}
