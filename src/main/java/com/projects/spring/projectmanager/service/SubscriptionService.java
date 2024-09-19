package com.projects.spring.projectmanager.service;

import com.projects.spring.projectmanager.Model.PlanType;
import com.projects.spring.projectmanager.Model.Subscription;
import com.projects.spring.projectmanager.Model.User;

public interface SubscriptionService {

    Subscription createSubscription(User user);

    Subscription getUserSubscription(Long userId) throws Exception;

    Subscription upgradeSubscription(Long userId, PlanType planType) throws Exception;

    boolean isValid(Subscription subscription);
}
