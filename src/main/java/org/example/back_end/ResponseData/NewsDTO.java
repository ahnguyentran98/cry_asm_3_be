package org.example.back_end.ResponseData;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.back_end.Entity.News;
import org.example.back_end.Entity.User;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class NewsDTO {
    private Long id;

    private User user;

    private String content;

    private LocalDateTime date;

    private String label;

    public NewsDTO() {
    }

    public NewsDTO(News news){
        this.id = news.getId();
        this.user = news.getUser();
        this.content = news.getContent();
        this.date = news.getDate();
        this.label = news.getLabel();
    }
}
