package by.itacademy.pinchuk.cms.util;

import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertTrue;

public class ConnectionManagerTest {

    private Connection connect = ConnectionManager.get();

    @Test
    public void CheckGet() throws SQLException {
        assertTrue(connect.isValid(0));
    }
}