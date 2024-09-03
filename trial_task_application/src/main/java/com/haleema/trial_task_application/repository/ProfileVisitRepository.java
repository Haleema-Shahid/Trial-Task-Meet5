package com.haleema.trial_task_application.repository;

import com.haleema.trial_task_application.entity.ProfileVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ProfileVisitRepository extends JpaRepository<ProfileVisit, Long> {
    @Query("SELECT COUNT(pv) FROM ProfileVisit pv WHERE pv.visitor.id = :visitorId AND pv.visitedAt > :timestamp")
    Long countByVisitorIdAndVisitedAtAfter(@Param("visitorId") Long visitorId, @Param("timestamp") Date timestamp);
}
