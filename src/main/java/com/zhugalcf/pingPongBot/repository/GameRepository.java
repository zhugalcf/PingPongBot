package com.zhugalcf.pingPongBot.repository;

import com.zhugalcf.pingPongBot.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game,Long> {

    @Query("SELECT g FROM Game g WHERE g.userId = :userId")
    List<Game> findByUserId(long userId);
}
