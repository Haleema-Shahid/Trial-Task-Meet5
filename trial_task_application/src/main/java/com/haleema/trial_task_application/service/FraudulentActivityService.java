package com.haleema.trial_task_application.service;

import com.haleema.trial_task_application.entity.User;
import com.haleema.trial_task_application.repository.ProfileLikeRepository;
import com.haleema.trial_task_application.repository.ProfileVisitRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class FraudulentActivityService {
    private final ProfileLikeRepository profileLikeRepository;
    private final ProfileVisitRepository profileVisitRepository;
    private final Logger logger = LoggerFactory.getLogger(FraudulentActivityService.class);
    public Boolean checkFraud(Long suspectId){
        logger.info("Fraud detection started...");
        try {
            Instant tenMinutesBefore = Instant.now().minus(10, ChronoUnit.MINUTES);
            Date timeFrameForFraudCheck = Date.from(tenMinutesBefore);
            Long visitCount = profileVisitRepository.countByVisitorIdAndVisitedAtAfter(suspectId, timeFrameForFraudCheck);
            Long likeCount = profileLikeRepository.countByLikerIdAndLikedAtAfter(suspectId, timeFrameForFraudCheck);
            return (visitCount + likeCount) >= 100;
        }catch(Exception e){
            logger.error("Exception occurred: "+ e.getLocalizedMessage());
            return false;
        }
        finally {
            logger.info("Fraud detection complete!");
        }
    }
}
