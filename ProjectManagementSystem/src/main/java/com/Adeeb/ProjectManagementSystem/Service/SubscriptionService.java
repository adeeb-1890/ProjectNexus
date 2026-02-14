package com.Adeeb.ProjectManagementSystem.Service;

import com.Adeeb.ProjectManagementSystem.Model.Subscription;
import com.Adeeb.ProjectManagementSystem.Model.User;
import com.Adeeb.ProjectManagementSystem.Model.PlanType;



public interface SubscriptionService {

    Subscription createSubscription(User user) throws Exception;

    Subscription gerUserSubscription(Long userId) throws Exception;

    Subscription upgradeSubscription(Long userId , PlanType planType) throws Exception;

    boolean isValid(Subscription subscription);
}
