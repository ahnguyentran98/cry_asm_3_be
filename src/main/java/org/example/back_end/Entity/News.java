package org.example.back_end.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.back_end.ResponseData.NewsDTO;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private User user;

    private String content;

    private LocalDateTime date;

    private String label;
}
