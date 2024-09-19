package com.projects.spring.projectmanager.repository;


import com.projects.spring.projectmanager.Model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepo extends JpaRepository<Subscription,Long> {

    Subscription findByUserId(Long userId);
}
