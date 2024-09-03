package com.haleema.trial_task_application.service;

import com.haleema.trial_task_application.dto.ProfileLikeDto;
import com.haleema.trial_task_application.dto.ProfileVisitDto;
import com.haleema.trial_task_application.dto.UserDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<UserDto> login(UserDto userDto);

    ResponseEntity<String> addProfileVisit(ProfileVisitDto profileVisitDto);

    ResponseEntity<String> addProfileLike(ProfileLikeDto profileLikeDto);

    ResponseEntity<Void> bulkInsertUsers();

    ResponseEntity<Void> bulkInsertUsersWithJpa();

    ResponseEntity<UserDto> signup(UserDto userDto);
}
