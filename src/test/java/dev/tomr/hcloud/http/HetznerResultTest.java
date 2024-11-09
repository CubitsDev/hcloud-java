package dev.tomr.hcloud.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HetznerResultTest {

    @Test
    @DisplayName("HetznerResult will disallow access to the failure object, when a success object exists")
    void hetznerResultWillDisallowAccessToFailureWhenSuccessExists() {
        HetznerResult<String, String> hetznerResult = new HetznerResult<>("success", "failure");
        assertThrows(IllegalAccessException.class, hetznerResult::getFailureObject);
    }

    @Test
    @DisplayName("Returns given success object")
    void returnsGivenSuccessObject() throws IllegalAccessException {
        HetznerResult<String, String> hetznerResult = new HetznerResult<>("success", null);
        assertEquals(hetznerResult.getSuccessObject(), "success");
    }

    @Test
    @DisplayName("Returns given failure object")
    void returnsGivenFailureObject() throws IllegalAccessException {
        HetznerResult<String, String> hetznerResult = new HetznerResult<>(null, "failure");
        assertEquals(hetznerResult.getFailureObject(), "failure");
    }

    @Test
    @DisplayName("Returns whether it has a success object correctly")
    void returnsWhetherItHasASuccessObject() {
        HetznerResult<String, String> hetznerResult = new HetznerResult<>("success", null);
        assertTrue(hetznerResult.isSuccess());

        HetznerResult<String, String> hetznerResult2 = new HetznerResult<>(null, "failure");
        assertFalse(hetznerResult2.isSuccess());
    }

    @Test
    @DisplayName("Throws IllegalAccessException when trying to access a non existent success object")
    void throwsIllegalAccessExceptionWhenTryingToAccessNonExistentSuccessObject() {
        HetznerResult<Object, Object> hetznerResult = new HetznerResult<>(null, new Object());

        assertThrows(IllegalAccessException.class, hetznerResult::getSuccessObject);
    }

    @Test
    @DisplayName("Throws IllegalAccessException when trying to access a non existent failure object")
    void throwsIllegalAccessExceptionWhenTryingToAccessNonExistentFailureObject() {
        HetznerResult<Object, Object> hetznerResult = new HetznerResult<>(new Object(), null);

        assertThrows(IllegalAccessException.class, hetznerResult::getFailureObject);
    }
}
