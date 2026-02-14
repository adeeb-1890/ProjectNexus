package com.Adeeb.ProjectManagementSystem.Service;

import com.Adeeb.ProjectManagementSystem.Model.Subscription;
import com.Adeeb.ProjectManagementSystem.Model.User;
import com.Adeeb.ProjectManagementSystem.Model.PlanType;
import com.Adeeb.ProjectManagementSystem.Repository.SubscriptionRepo;
import org.hibernate.sql.exec.spi.LoadedValuesCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SubscriptionServiceImplementation implements SubscriptionService {

    @Autowired
    private UserService userService;

    @Autowired
    private SubscriptionRepo subscriptionRepo;


    @Override
    public Subscription createSubscription(User user) throws Exception {
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusMonths(12) );
        subscription.setValid(true);
        subscription.setPlanType(PlanType.FREE);

        return subscriptionRepo.save(subscription);
    }

    @Override
    public Subscription gerUserSubscription(Long userId) throws Exception {
        Subscription subscription =  subscriptionRepo.findByUserId(userId);
        if(!isValid(subscription)){
            subscription.setPlanType(PlanType.FREE);
            subscription.setEndDate(LocalDate.now().plusMonths(12));
            subscription.setStartDate(LocalDate.now());
        }
        Subscription savedSubscription = subscriptionRepo.save(subscription);
        return savedSubscription;
    }

    @Override
    public Subscription upgradeSubscription(Long userId, PlanType planType) throws Exception {
        Subscription subscription = subscriptionRepo.findByUserId(userId);

        subscription.setPlanType(planType);
        subscription.setStartDate(LocalDate.now());

        if(planType.equals(PlanType.ANNUALY)){
            subscription.setEndDate(LocalDate.now().plusMonths(12));

        }else{
            subscription.setEndDate(LocalDate.now().plusMonths(6));
        }
        return subscription;
    }

    @Override
    public boolean isValid(Subscription subscription) {
        if(subscription.getPlanType().equals(PlanType.FREE))
            return true;

        LocalDate endDate = subscription.getEndDate();
        LocalDate currentDate = LocalDate.now();

        return endDate.isAfter(currentDate) || endDate.isEqual(currentDate);
    }
}
