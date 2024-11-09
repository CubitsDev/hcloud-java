package dev.tomr.hcloud.http.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Error {
        private String code;
        private String message;

        public Error(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public Error() {}

        public String getCode() {
            return code;
        }
        public String getMessage() {
            return message;
        }

        public void setCode(String code) {
            this.code = code;
        }
        public void setMessage(String message) {
            this.message = message;
        }
    }
}
