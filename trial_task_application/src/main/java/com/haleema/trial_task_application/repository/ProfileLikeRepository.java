package com.haleema.trial_task_application.repository;

import com.haleema.trial_task_application.entity.ProfileLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ProfileLikeRepository extends JpaRepository<ProfileLike, Long> {
    @Query("SELECT COUNT(pl) FROM ProfileLike pl WHERE pl.liker.id = :likerId AND pl.likedAt > :timestamp")
    Long countByLikerIdAndLikedAtAfter(@Param("likerId") Long likerId, @Param("timestamp") Date timestamp);
}
