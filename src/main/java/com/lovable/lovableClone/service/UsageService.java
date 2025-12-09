package com.lovable.lovableClone.service;

import com.lovable.lovableClone.dto.subscription.PlanLimitsResponse;
import com.lovable.lovableClone.dto.subscription.UsageTodayResponse;
import org.jspecify.annotations.Nullable;

public interface UsageService {
    UsageTodayResponse getTodayUsage(Long userId);

    PlanLimitsResponse getCurrentSubscriptionLimitsOfUser(Long userId);
}
