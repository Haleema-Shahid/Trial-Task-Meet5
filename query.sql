SELECT
    user_profile.*,
    COUNT(profile_visit.visitor_id) AS visit_count
FROM
    user_profile
        INNER JOIN
    profile_visit
    ON
                user_profile.id = profile_visit.visitor_id
            AND profile_visit.visited_id = 1
GROUP BY
    user_profile.id
ORDER BY
    visit_count desc;
