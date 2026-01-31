package com.lovable.lovableClone.mapper;


import com.lovable.lovableClone.dto.subscription.PlanResponse;
import com.lovable.lovableClone.dto.subscription.SubscriptionResponse;
import com.lovable.lovableClone.entity.Plan;
import com.lovable.lovableClone.entity.Subscription;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    SubscriptionResponse toSubscriptionResponse (Subscription subscription);

    PlanResponse toPlanResponse(Plan plan);

}
