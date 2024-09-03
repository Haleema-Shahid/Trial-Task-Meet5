package com.haleema.trial_task_application.controller;

import com.haleema.trial_task_application.dto.ProfileLikeDto;
import com.haleema.trial_task_application.dto.ProfileVisitDto;
import com.haleema.trial_task_application.dto.UserDto;
import com.haleema.trial_task_application.service.BulkInsertionService;
import com.haleema.trial_task_application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody UserDto userDto){
        return userService.login(userDto);
    }

    @PostMapping("/visit")
    public ResponseEntity<String> addProfileVisit(@RequestBody ProfileVisitDto profileVisitDto){
        return userService.addProfileVisit(profileVisitDto);
    }

    @PostMapping("/like")
    public ResponseEntity<String> addProfileLike(@RequestBody ProfileLikeDto profileLikeDto){
        return userService.addProfileLike(profileLikeDto);
    }
    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody UserDto userDto){
        return userService.signup(userDto);
    }
    @GetMapping("/bulk-insert")
    public ResponseEntity<Void> bulkInsertUsers(){
        return userService.bulkInsertUsers();
    }

    @GetMapping("/bulk-insert-jpa")
    public ResponseEntity<Void> bulkInsertUsersWithJpa(){
        return userService.bulkInsertUsersWithJpa();
    }
    
}
