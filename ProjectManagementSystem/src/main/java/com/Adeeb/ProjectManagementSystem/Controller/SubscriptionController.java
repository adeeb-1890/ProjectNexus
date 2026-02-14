package com.Adeeb.ProjectManagementSystem.Controller;

import com.Adeeb.ProjectManagementSystem.Model.PlanType;
import com.Adeeb.ProjectManagementSystem.Model.Subscription;
import com.Adeeb.ProjectManagementSystem.Model.User;
import com.Adeeb.ProjectManagementSystem.Service.SubscriptionService;
import com.Adeeb.ProjectManagementSystem.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/subscription")

public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public ResponseEntity<Subscription> getUserSubsription(@RequestHeader("Authorizatio")String jwtToken) throws Exception {
        User user = userService.findUserProfileByJwt(jwtToken);

        Subscription subscription = subscriptionService.gerUserSubscription(user.getId());
        return ResponseEntity.ok(subscription);
    }

    @PatchMapping("/upgrade")
    public ResponseEntity<Subscription> upgradeSubscription(@RequestHeader("Authorization")String jwtToken ,
                                                            @RequestParam PlanType planType) throws Exception {
        User user = userService.findUserProfileByJwt(jwtToken);

         Subscription subscription = subscriptionService.upgradeSubscription(user.getId() , planType);
         return ResponseEntity.ok(subscription);
    }
}
