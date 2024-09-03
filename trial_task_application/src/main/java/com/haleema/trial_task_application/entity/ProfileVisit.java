package com.haleema.trial_task_application.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Table(name = "profile_visit")
@Getter
@Setter
public class ProfileVisit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visitor_id", nullable = false)
    private User visitor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visited_id", nullable = false)
    private User visited;

    @Column(name = "visited_at", nullable = false)
    private Date visitedAt = new Date();
}
