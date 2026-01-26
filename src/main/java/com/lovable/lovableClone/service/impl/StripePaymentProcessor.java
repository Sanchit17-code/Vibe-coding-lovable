package com.lovable.lovableClone.service.impl;

import com.lovable.lovableClone.dto.subscription.CheckoutRequest;
import com.lovable.lovableClone.dto.subscription.CheckoutResponse;
import com.lovable.lovableClone.dto.subscription.PortalResponse;
import com.lovable.lovableClone.entity.Plan;
import com.lovable.lovableClone.error.ResourceNotFoundException;
import com.lovable.lovableClone.repository.PlanRepository;
import com.lovable.lovableClone.security.AuthUtil;
import com.lovable.lovableClone.service.PaymentProcessor;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StripePaymentProcessor implements PaymentProcessor {

    private final AuthUtil authUtil;
    private final PlanRepository planRepository;

    @Value("${client.url}")
    private String frontendUrl;


    @Override
    public CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request) {

        Plan plan = planRepository.findById(request.planId()).orElseThrow(()->
                new ResourceNotFoundException("Plan", request.planId().toString()));

        Long userId = authUtil.getCurrentUserId();
        SessionCreateParams params = SessionCreateParams.builder()
                .addLineItem(
                        SessionCreateParams.LineItem.builder().setPrice(plan.getStripePriceId()).setQuantity(1L).build())
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                .setSubscriptionData(
                        new SessionCreateParams.SubscriptionData.Builder()
                                .setBillingMode(SessionCreateParams.SubscriptionData.BillingMode.builder()
                                        .setType(SessionCreateParams.SubscriptionData.BillingMode.Type.FLEXIBLE)
                                        .build())
                                .build()
                )
//                .setSuccessUrl(frontendUrl + "/success.html?session_id={CHECKOUT_SESSION_ID}")
                .setSuccessUrl("https://www.google.com" + "/success.html?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl("https://www.google.com" + "/cancel.html")
                .putMetadata("user_id",userId.toString())
                .putMetadata("plan_id",plan.getId().toString())
                .build();
        try {
            Session session = Session.create(params);  // this is where SDK is making the API call to the stripe server
            return new CheckoutResponse(session.getUrl());

        } catch (StripeException e) {
            e.printStackTrace();
            throw new RuntimeException("Stripe error: " + e.getMessage());
//            throw new RuntimeException(e);
        }
    }

    @Override
    public PortalResponse openCustomerPortal(Long userId) {
        return null;
    }
}
