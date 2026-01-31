package com.lovable.lovableClone.repository;

import com.lovable.lovableClone.entity.Subscription;
import com.lovable.lovableClone.enums.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

//    get the current active subscription
    Optional<Subscription> findByUserIdAndStatusIn(Long userId, Set<SubscriptionStatus> trialing);

    boolean existsByStripeSubscriptionId(String subscriptionId);

    Optional<Subscription> findByStripeSubscriptionId(String gatewaySubscriptionId);
}
