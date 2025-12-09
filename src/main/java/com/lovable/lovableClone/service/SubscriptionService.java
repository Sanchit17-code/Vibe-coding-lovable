package com.lovable.lovableClone.service;

import com.lovable.lovableClone.dto.subscription.CheckoutRequest;
import com.lovable.lovableClone.dto.subscription.CheckoutResponse;
import com.lovable.lovableClone.dto.subscription.PortalResponse;
import org.jspecify.annotations.Nullable;

public interface SubscriptionService {
    CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request, Long userId);

    PortalResponse openCustomerPortal(Long userId);
}
