package com.haleema.trial_task_application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.haleema.trial_task_application.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.haleema.trial_task_application.util.Constants.LIKED_ID;
import static com.haleema.trial_task_application.util.Constants.LIKER_ID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileLikeDto {
    @JsonProperty(LIKER_ID)
    private Long likerId;
    @JsonProperty(LIKED_ID)
    private Long liked_id;
}
