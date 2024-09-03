package com.haleema.trial_task_application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.haleema.trial_task_application.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.haleema.trial_task_application.util.Constants.VISITED_ID;
import static com.haleema.trial_task_application.util.Constants.VISITOR_ID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileVisitDto {
    @JsonProperty(VISITOR_ID)
    private Long visitorId;
    @JsonProperty(VISITED_ID)
    private Long visited_id;
}
