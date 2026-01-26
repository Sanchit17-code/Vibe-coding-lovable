package com.lovable.lovableClone.service;

import com.lovable.lovableClone.dto.subscription.CheckoutRequest;
import com.lovable.lovableClone.dto.subscription.CheckoutResponse;
import com.lovable.lovableClone.dto.subscription.PortalResponse;

public interface PaymentProcessor {

    CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request);

    PortalResponse openCustomerPortal(Long userId);
}
