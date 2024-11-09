package dev.tomr.hcloud.http.exception;

import dev.tomr.hcloud.http.model.HetznerErrorResponse;

public class HetznerApiException extends RuntimeException {
    public HetznerApiException(HetznerErrorResponse errorResponse) {
        super(errorResponse.toString());
    }
}
