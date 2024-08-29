package org.example.back_end.Services;

import org.example.back_end.Entity.News;
import org.example.back_end.Entity.User;
import org.example.back_end.ResponseData.NewsDTO;
import org.example.back_end.repositories.NewsRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SecurityLevelService securityLevelService;

    @Autowired
    private NewsRepo newsRepo;

    @Autowired
    private UserService userService;

    public List<NewsDTO> getNews(Long userId){
        LOGGER.info("Get news for user {}", userId);
        User user = userService.getUserById(userId);
        if (user == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid userID");
        }

        List<String> securityLevels = securityLevelService.checkAndGetLevelForReadMessage(user.getLabel());
        List<News> news = newsRepo.getNewsByLabels(securityLevels);
        if (news.isEmpty()){
            return new ArrayList<>();
        }

        List<NewsDTO> result = new ArrayList<>();
        news.forEach(message -> result.add(new NewsDTO(message)));
        return result;
    }

    public void writeNews(Long userId, NewsDTO newsDTO){
        LOGGER.info("Write news {} for user {}", newsDTO ,userId);
        User user = userService.getUserById(userId);
        if (user == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid userID");
        }

        if(!securityLevelService.checkLevelForWriteMessage(newsDTO.getLabel(), user.getLabel())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User now authorize to write news for this level");
        }

        News news = new News();
        news.setContent(newsDTO.getContent());
        news.setUser(user);
        news.setLabel(newsDTO.getLabel());
        news.setDate(LocalDateTime.now());

        newsRepo.saveAndFlush(news);
    }

}
