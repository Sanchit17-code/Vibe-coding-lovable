package com.lovable.lovableClone.service.impl;

import com.lovable.lovableClone.dto.subscription.CheckoutRequest;
import com.lovable.lovableClone.dto.subscription.CheckoutResponse;
import com.lovable.lovableClone.dto.subscription.PortalResponse;
import com.lovable.lovableClone.dto.subscription.SubscriptionResponse;
import com.lovable.lovableClone.service.SubscriptionService;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    @Override
    public SubscriptionResponse getCurrentSubscription(Long userId) {
        return null;
    }

}
