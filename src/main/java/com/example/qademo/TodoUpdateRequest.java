package com.example.qademo;

import jakarta.validation.constraints.NotBlank;

public record TodoUpdateRequest(@NotBlank String title, Boolean done) {}
