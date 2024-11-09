package dev.tomr.hcloud.http.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class HetznerErrorResponseTest {

    @Test
    @DisplayName("Constructor creates a HetznerErrorResponse")
    public void constructorWorksCorrectly() {
        HetznerErrorResponse.Error error = new HetznerErrorResponse.Error("code", "message");
        assertInstanceOf(HetznerErrorResponse.class, new HetznerErrorResponse(error));
    }

    @Test
    @DisplayName("Setters and Getters work on sub Error class")
    void errorSettersGettersWorkCorrectly() {
        HetznerErrorResponse.Error error = new HetznerErrorResponse.Error("code", "message");
        error.setCode("newCode");
        assertEquals("newCode", error.getCode());
        error.setMessage("newMessage");
        assertEquals("newMessage", error.getMessage());
    }

    @Test
    @DisplayName("getError returns as expected")
    void getErrorReturnsExpected() {
        HetznerErrorResponse.Error error = new HetznerErrorResponse.Error("code", "message");
        HetznerErrorResponse errorResponse = new HetznerErrorResponse(error);
        assertEquals(error, errorResponse.getError());
    }

    @Test
    @DisplayName("toString returns an expected string")
    void toStringWorksCorrectly() {
        HetznerErrorResponse.Error error = new HetznerErrorResponse.Error("code", "message");
        HetznerErrorResponse errorResponse = new HetznerErrorResponse(error);
        assertEquals("HetznerErrorResponse [code=code, message=message]", errorResponse.toString());
    }
}