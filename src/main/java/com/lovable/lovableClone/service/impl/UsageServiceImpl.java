package com.lovable.lovableClone.service.impl;

import com.lovable.lovableClone.dto.subscription.PlanLimitsResponse;
import com.lovable.lovableClone.dto.subscription.UsageTodayResponse;
import com.lovable.lovableClone.service.UsageService;
import org.springframework.stereotype.Service;

@Service
public class UsageServiceImpl implements UsageService {
    @Override
    public UsageTodayResponse getTodayUsage(Long userId) {
        return null;
    }

    @Override
    public PlanLimitsResponse getCurrentSubscriptionLimitsOfUser(Long userId) {
        return null;
    }
}
