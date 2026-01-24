package com.lovable.lovableClone.dto.project;

import jakarta.validation.constraints.NotBlank;

public record ProjectRequest(
       @NotBlank String name
) {
}
