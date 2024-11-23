package dev.tomr.hcloud.http.exception;

import dev.tomr.hcloud.http.model.Error;
import dev.tomr.hcloud.http.model.HetznerErrorResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HetznerApiExceptionTest {

    @Test
    @DisplayName("Throws and outputs correct string")
    void throwsWithCorrectParams() {
        Error error = new Error("code", "message");
        HetznerErrorResponse errorResponse = new HetznerErrorResponse(error);
        HetznerApiException hetznerApiException = assertThrows(HetznerApiException.class, () -> {
            throw new HetznerApiException(errorResponse);
        });

        assertEquals("HetznerErrorResponse [code=code, message=message]", hetznerApiException.getMessage());
    }
}
