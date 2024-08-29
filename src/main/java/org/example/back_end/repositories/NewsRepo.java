package org.example.back_end.repositories;

import org.example.back_end.Entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NewsRepo extends JpaRepository<News, Long> {
    @Query(value = "select * from news where label in (?1)",nativeQuery = true)
    public List<News> getNewsByLabels(List<String> labels);
}
