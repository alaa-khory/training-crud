package org.example.models.generic;

public record ApiResponse <T>(boolean success, String message, T data) { }
