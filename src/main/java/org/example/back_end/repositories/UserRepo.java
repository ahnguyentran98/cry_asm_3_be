package org.example.back_end.repositories;

import org.example.back_end.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {
    @Query(value = "select * from user where account_name= ?1", nativeQuery = true)
    public List<User> getUserByAccountName(String accountName);

    @Query(value = "select * from user where id= ?1", nativeQuery = true)
    public User getUserById(Long userId);
}
