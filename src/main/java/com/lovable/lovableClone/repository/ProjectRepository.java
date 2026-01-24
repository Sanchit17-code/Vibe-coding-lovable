package com.lovable.lovableClone.repository;

import com.lovable.lovableClone.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("""
            SELECT p from Project p
            WHERE p.deletedAt IS NULL
            AND EXISTS(
                SELECT pm.id FROM ProjectMember pm
                WHERE pm.id.userId = :userId
                AND pm.id.projectId = p.id
            )
            ORDER BY p.updatedAt DESC
            """)
    List<Project> findAllAccessibleByUser(@Param("userId") Long userId);

    // here fetching the owner info because this is required anyway. we need to show the owner's info
    // like this -
//    {
//        "id": 4,
//            "name": "Project 4 with time stamp",
//            "createdAt": "2025-12-17T16:23:54.750576Z",
//            "updatedAt": "2025-12-17T16:23:54.750576Z",
//            "owner": {
//        "id": 1,
//                "email": "sanchit@gmail.com",
//                "name": "Sanchit",
//                "avatarUrl": null
//    }
//    }

    //uses 1db query
    @Query("""
            SELECT p from Project p
            WHERE p.id = :projectId
            AND p.deletedAt IS NULL
            AND EXISTS(
                SELECT pm.id FROM ProjectMember pm
                WHERE pm.id.userId = :userId
                AND pm.id.projectId = :projectId
            )
            """)
    Optional<Project> findAccessibleProjectById(@Param("projectId") Long projectId,
                                                @Param("userId") Long userId);

    // this approach is using 2 db queries as it needs to fetch user table
    //    Optional<Project> findByIdAndOwnerIdAndDeletedAtIsNull(Long projectId, Long ownerId);


}
