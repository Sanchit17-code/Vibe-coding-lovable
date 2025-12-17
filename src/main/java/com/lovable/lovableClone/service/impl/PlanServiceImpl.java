package com.lovable.lovableClone.service.impl;

import com.lovable.lovableClone.dto.subscription.PlanResponse;
import com.lovable.lovableClone.dto.subscription.SubscriptionResponse;
import com.lovable.lovableClone.service.PlanService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PlanServiceImpl implements PlanService {
    @Override
    public List<PlanResponse> getAllActivePlans() {
        return List.of();
    }

    @Override
    public SubscriptionResponse getMySubscription(Long userId) {
        return null;
    }
}
