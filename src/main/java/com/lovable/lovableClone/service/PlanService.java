package com.lovable.lovableClone.service;

import com.lovable.lovableClone.dto.subscription.PlanResponse;
import com.lovable.lovableClone.dto.subscription.SubscriptionResponse;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface PlanService {
    List<PlanResponse> getAllActivePlans();

    SubscriptionResponse getMySubscription(Long userId);
}
