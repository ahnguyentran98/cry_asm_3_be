package org.example.back_end.Services;

import org.example.back_end.constants.UserSecurityLabel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SecurityLevelService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public UserSecurityLabel getLevel(String levelString){
        UserSecurityLabel userSecurityLabel = UserSecurityLabel.UNCLASSIFIED;
        for(UserSecurityLabel securityLabel : UserSecurityLabel.values()){
            if (Objects.equals(securityLabel.getLabel(), levelString)){
                return securityLabel;
            }
        }

        return userSecurityLabel;
    }

    public List<String> checkAndGetLevelForReadMessage(String levelReq){
        UserSecurityLabel level = this.getLevel(levelReq);
        List<String> levelList = new ArrayList<>();
        for (UserSecurityLabel levelCheck : UserSecurityLabel.values()){
            if (levelCheck.getLevelRank() >= level.getLevelRank()){
                levelList.add(levelCheck.getLabel());
            }
        }

        return levelList;
    }

    public boolean checkLevelForWriteMessage(String newsLevelString, String userLevelString){
        UserSecurityLabel newsLevel = this.getLevel(newsLevelString);
        UserSecurityLabel userLevel = this.getLevel(userLevelString);
        LOGGER.info("check user level {} to post new for level {}", userLevel, newsLevel);
        return newsLevel.getLevelRank() <= userLevel.getLevelRank();
    }
}
