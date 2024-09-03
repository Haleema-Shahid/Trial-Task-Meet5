package com.haleema.trial_task_application.service;

import com.haleema.trial_task_application.dto.ProfileLikeDto;
import com.haleema.trial_task_application.dto.ProfileVisitDto;
import com.haleema.trial_task_application.dto.UserDto;
import com.haleema.trial_task_application.entity.ProfileLike;
import com.haleema.trial_task_application.entity.ProfileVisit;
import com.haleema.trial_task_application.entity.User;
import com.haleema.trial_task_application.repository.ProfileLikeRepository;
import com.haleema.trial_task_application.repository.ProfileVisitRepository;
import com.haleema.trial_task_application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final ProfileVisitRepository profileVisitRepository;
    private final ProfileLikeRepository profileLikeRepository;
    private final FraudulentActivityService fraudulentActivityService;
    private final BulkInsertionService bulkInsertionService;

    @Override
    public ResponseEntity<UserDto> login(UserDto userDto) {
        logger.info("login started");

        User userEntity = userRepository.findByEmailAndPassword(userDto.getEmail(), userDto.getPassword()).orElseThrow(NoSuchElementException::new);
        BeanUtils.copyProperties(userEntity, userDto);
        userDto.setPassword(null);
        userDto.setAge(calculateAge(userEntity.getDateOfBirth()));
        userEntity.setLastLogin(new Date());
        userRepository.save(userEntity);
        return new ResponseEntity<>(userDto, HttpStatus.OK);

    }

    public int calculateAge(Date dateOfBirth) {
        int age = -1;
        if (dateOfBirth != null) {
            Calendar birthCal = Calendar.getInstance();
            birthCal.setTime(dateOfBirth);

            Calendar nowCal = Calendar.getInstance();

            age = nowCal.get(Calendar.YEAR) - birthCal.get(Calendar.YEAR);

            if (nowCal.get(Calendar.MONTH) < birthCal.get(Calendar.MONTH) ||
                    (nowCal.get(Calendar.MONTH) == birthCal.get(Calendar.MONTH) &&
                            nowCal.get(Calendar.DAY_OF_MONTH) < birthCal.get(Calendar.DAY_OF_MONTH))) {
                age--;
            }
        }
        return age;
    }

    @Override
    public ResponseEntity<String> addProfileVisit(ProfileVisitDto profileVisitDto) {
        String response = "";

        User visitor = userRepository.findById(profileVisitDto.getVisitorId()).orElseThrow(NoSuchElementException::new);
        User visitedUser = userRepository.findById(profileVisitDto.getVisited_id()).orElseThrow(NoSuchElementException::new);

        if (!fraudulentActivityService.checkFraud(visitor.getId())) {
            ProfileVisit profileVisit = new ProfileVisit();
            profileVisit.setVisitedAt(new Date());
            profileVisit.setVisited(visitedUser);
            profileVisit.setVisitor(visitor);

            profileVisitRepository.save(profileVisit);
        } else {
            logger.warn("FRAUD DETECTED!");
            response = "Unusual activity detected!";
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @Override
    public ResponseEntity<String> addProfileLike(ProfileLikeDto profileLikeDto) {
        String response = "";

        User liker = userRepository.findById(profileLikeDto.getLikerId()).orElseThrow(NoSuchElementException::new);
        User likedUser = userRepository.findById(profileLikeDto.getLiked_id()).orElseThrow(NoSuchElementException::new);

        if (!fraudulentActivityService.checkFraud(liker.getId())) {
            ProfileLike profileLike = new ProfileLike();
            profileLike.setLikedAt(new Date());
            profileLike.setLiked(likedUser);
            profileLike.setLiker(liker);
            profileLikeRepository.save(profileLike);
        } else {
            logger.warn("FRAUD DETECTED!");
            response = "Unusual activity detected!";
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);


    }

    @Override
    public ResponseEntity<Void> bulkInsertUsers() {

        bulkInsertionService.bulkInsertUsers();
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Void> bulkInsertUsersWithJpa() {

        bulkInsertionService.bulkInsertUsersWithJpa();
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @Override
    public ResponseEntity<UserDto> signup(UserDto userDto) {
        UserDto response = new UserDto();
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        BeanUtils.copyProperties(userRepository.save(user), response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
