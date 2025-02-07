package com.example.property.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class BaseController {
    /**
     * Create a success response with data.
     *
     * @param data    The data to be sent in the response.
     * @param message A custom message.
     * @return ResponseEntity with a consistent JSON structure.
     */
    protected ResponseEntity<?> successResponse(Object data, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", true);
        response.put("message", message);
        response.put("data", data);
        return ResponseEntity.ok(response);
    }

    /**
     * Create an error response with an HTTP status and message.
     *
     * @param status  The HTTP status to return.
     * @param message A custom error message.
     * @return ResponseEntity with a consistent JSON structure.
     */
    protected ResponseEntity<?> errorResponse(HttpStatus status, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", false);
        response.put("message", message);
        response.put("data", null);
        return ResponseEntity.status(status).body(response);
    }

    /**
     * Create an error response with an HTTP status, message, and additional data.
     *
     * @param status  The HTTP status to return.
     * @param message A custom error message.
     * @param data    Additional error data (if any).
     * @return ResponseEntity with a consistent JSON structure.
     */
    protected ResponseEntity<?> errorResponse(HttpStatus status, String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", false);
        response.put("message", message);
        response.put("data", data);
        return ResponseEntity.status(status).body(response);
    }
}
