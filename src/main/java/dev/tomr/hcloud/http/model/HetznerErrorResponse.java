package dev.tomr.hcloud.http.model;

import dev.tomr.hcloud.http.HetznerJsonObject;

public class HetznerErrorResponse  extends HetznerJsonObject {
    private Error error;

    public HetznerErrorResponse(Error error) {
        this.error = error;
    }

    public HetznerErrorResponse() {}

    public Error getError() {
        return error;
    }

    public String toString() {
        return "HetznerErrorResponse [code=" + error.getCode() + ", message=" + error.getMessage() + "]";
    }

}
