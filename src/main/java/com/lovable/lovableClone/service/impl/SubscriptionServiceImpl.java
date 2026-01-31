package com.lovable.lovableClone.service.impl;

import com.lovable.lovableClone.dto.subscription.CheckoutRequest;
import com.lovable.lovableClone.dto.subscription.CheckoutResponse;
import com.lovable.lovableClone.dto.subscription.PortalResponse;
import com.lovable.lovableClone.dto.subscription.SubscriptionResponse;
import com.lovable.lovableClone.entity.Plan;
import com.lovable.lovableClone.entity.Subscription;
import com.lovable.lovableClone.entity.User;
import com.lovable.lovableClone.enums.SubscriptionStatus;
import com.lovable.lovableClone.error.ResourceNotFoundException;
import com.lovable.lovableClone.mapper.SubscriptionMapper;
import com.lovable.lovableClone.repository.PlanRepository;
import com.lovable.lovableClone.repository.SubscriptionRepository;
import com.lovable.lovableClone.repository.UserRepository;
import com.lovable.lovableClone.security.AuthUtil;
import com.lovable.lovableClone.service.SubscriptionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final AuthUtil authUtil;
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;

    @Override
    public SubscriptionResponse getCurrentSubscription() {
        Long userId = authUtil.getCurrentUserId();
        var currentSubscription = subscriptionRepository.findByUserIdAndStatusIn(userId, Set.of(
                SubscriptionStatus.TRIALING, SubscriptionStatus.ACTIVE, SubscriptionStatus.PAST_DUE
        )).orElse(
                new Subscription()
        );
        return subscriptionMapper.toSubscriptionResponse(currentSubscription);
    }

    // here we will be creating the subscription object if not already present because here we are coming from the checkout page
    // but if it already exists then no need to do anything
    // this will hit when the checkout  will be completed by the user
    @Override
    public void activateSubscription(Long userId, Long planId, String subscriptionId, String customerId) {
        boolean exists = subscriptionRepository.existsByStripeSubscriptionId(subscriptionId);
        if (exists) return;
        User user = getUser(userId);
        Plan plan = getPlan(planId);

        Subscription subscription = Subscription.builder()
                .user(user)
                .plan(plan)
                .stripeSubscriptionId(subscriptionId)
                .status(SubscriptionStatus.INCOMPLETE)   // here incomplete because later when paid invoice event will be triggered then we will make it active
                .build();

        subscriptionRepository.save(subscription);
    }

    @Override
    @Transactional
    //In the update subscription, basically we get subscription from the Stripe and using its ID,
    // we get subscription object from database and compare each of the fields which
    // can change possibly and change that in the DB.
    public void updateSubscription(String gatewaySubscriptionId, SubscriptionStatus status, Instant periodStart, Instant periodEnd, Boolean cancelAtPeriodEnd, Long planId) {
        Subscription subscription = getSubscription(gatewaySubscriptionId);

        boolean hasSubscriptionUpdated = false;

        if(status != null && status != subscription.getStatus()) {
            subscription.setStatus(status);
            hasSubscriptionUpdated = true;
        }

        if(periodStart != null && !periodStart.equals(subscription.getCurrentPeriodStart())) {
            subscription.setCurrentPeriodStart(periodStart);
            hasSubscriptionUpdated = true;
        }

        if(periodEnd != null && !periodEnd.equals(subscription.getCurrentPeriodEnd())) {
            subscription.setCurrentPeriodEnd(periodEnd);
            hasSubscriptionUpdated = true;
        }

        if(cancelAtPeriodEnd != null && cancelAtPeriodEnd != subscription.getCancelAtPeriodEnd()) {
            subscription.setCancelAtPeriodEnd(cancelAtPeriodEnd);
            hasSubscriptionUpdated = true;
        }

        if(planId != null && !planId.equals(subscription.getPlan().getId())) {
            Plan newPlan = getPlan(planId);
            subscription.setPlan(newPlan);
            hasSubscriptionUpdated = true;
        }

        if(hasSubscriptionUpdated) {
            log.debug("Subscription has been updated: {}", gatewaySubscriptionId);
            subscriptionRepository.save(subscription);
        }
    }

    @Override
    public void cancelSubscription(String gatewaySubscriptionId) {
        Subscription subscription = getSubscription(gatewaySubscriptionId);
        subscription.setStatus(SubscriptionStatus.CANCELLED);
        subscriptionRepository.save(subscription);
    }

    @Override
    public void renewSubscriptionPeriod(String gatewaySubscriptionId, Instant periodStart, Instant periodEnd) {
        Subscription subscription = getSubscription(gatewaySubscriptionId);
        Instant newStart = periodStart!=null ? periodStart : subscription.getCurrentPeriodEnd();
        subscription.setCurrentPeriodStart(newStart);
        subscription.setCurrentPeriodEnd(periodEnd);

        if(subscription.getStatus() == SubscriptionStatus.PAST_DUE ||  subscription.getStatus() == SubscriptionStatus.INCOMPLETE ){
            subscription.setStatus(SubscriptionStatus.ACTIVE);
        }
        subscriptionRepository.save(subscription);
    }

    @Override
    public void markSubscriptionPastDue(String gatewaySubscriptionId) {
        Subscription subscription = getSubscription(gatewaySubscriptionId);
        if(subscription.getStatus() == SubscriptionStatus.PAST_DUE){
            log.debug("Subscription is already pastdue, gatewaySubscriptionId - {}", gatewaySubscriptionId);
            return;
        }
        subscription.setStatus(SubscriptionStatus.PAST_DUE);
        subscriptionRepository.save(subscription);
    }

    /// utility methods
    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId.toString()));
    }

    private Plan getPlan(Long planId) {
        return planRepository.findById(planId)
                .orElseThrow(() -> new ResourceNotFoundException("Plan", planId.toString()));

    }

    private Subscription getSubscription(String gatewaySubscriptionId) {
        return subscriptionRepository.findByStripeSubscriptionId(gatewaySubscriptionId).orElseThrow(() ->
                new ResourceNotFoundException("Subscription", gatewaySubscriptionId));
    }

}
