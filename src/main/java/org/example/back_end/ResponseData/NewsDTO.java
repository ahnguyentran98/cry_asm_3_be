package org.example.back_end.ResponseData;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.back_end.Entity.News;
import org.example.back_end.Entity.User;
import org.springframework.data.annotation.Transient;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class NewsDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

//    @Transient
//    @JsonIgnore
//    private User user;

    private String userName;

    private String content;

    private LocalDateTime date;

    private String label;

    public NewsDTO() {
    }

    public NewsDTO(News news){
        this.id = news.getId();
//        this.user = news.getUser();
        this.userName = news.getUser().getUserName();
        this.content = news.getContent();
        this.date = news.getDate();
        this.label = news.getLabel();
    }
}
