package com.zhugalcf.pingPongBot.repository;

import com.zhugalcf.pingPongBot.entity.UserMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<UserMessage,Long> {
}
