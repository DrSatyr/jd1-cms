package by.itacademy.pinchuk.cms.util;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class AppConfigTest {

    @Test
    public void CheckGet() {
        String value = AppConfig.get(TestData.VALID_DB_URL_KEY);
        assertNotNull(value);
    }
}