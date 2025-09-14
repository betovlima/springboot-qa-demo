package com.example.qademo;

import jakarta.validation.constraints.NotBlank;

public record TodoCreateRequest(@NotBlank String title) {}
