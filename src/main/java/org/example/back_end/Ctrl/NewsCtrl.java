package org.example.back_end.Ctrl;

import org.apache.commons.lang3.StringUtils;
import org.example.back_end.ResponseData.NewsDTO;
import org.example.back_end.Services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsCtrl {

    @Autowired
    private NewsService newsService;

    @GetMapping("/{user-id}")
    public List<NewsDTO> getNews(@PathVariable("user-id") Long userId){
        if (userId == null || userId == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid userId");
        }

        return newsService.getNews(userId);
    }

    @PostMapping("/{user-id}")
    public void postNews(@PathVariable("user-id") Long userId, @RequestBody NewsDTO newsReq){
        if (userId == null || userId == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid userId");
        }

        if (StringUtils.isBlank(newsReq.getContent())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empty content");
        }

        if (StringUtils.isBlank(newsReq.getLabel())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empty label");
        }

        newsService.writeNews(userId, newsReq);
    }
}
