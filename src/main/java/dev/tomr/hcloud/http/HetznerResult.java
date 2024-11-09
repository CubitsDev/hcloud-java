package dev.tomr.hcloud.http;

public class HetznerResult<T, U> {
    private final T successObject;
    private final U failureObject;

    public HetznerResult(T successObject, U failureObject) {
        this.successObject = successObject;
        this.failureObject = failureObject;
    }

    public boolean isSuccess() {
        return successObject != null;
    }

    public T getSuccessObject() throws IllegalAccessException {
        if (isSuccess()) {
            return successObject;
        } else {
            throw new IllegalAccessException("No success object!");
        }
    }

    public U getFailureObject() throws IllegalAccessException {
        if (!isSuccess()) {
            return failureObject;
        } else {
            throw new IllegalAccessException("No failure object!");
        }
    }
}
