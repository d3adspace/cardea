package de.d3adspace.cardea.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Felix Klauke <info@felix-klauke.de>
 */
class BackendTest {

    private static final String TEST_NAME = "testName";
    private static final String TEST_HOST = "localhost";
    private static final int TEST_PORT = 8081;
    private Backend backend;

    @BeforeEach
    void setUp() {
        backend = new Backend(TEST_NAME, TEST_HOST, TEST_PORT);
    }

    @Test
    void testGetName() {
        assertEquals(TEST_NAME, backend.getName());
    }

    @Test
    void testGetPort() {
        assertEquals(TEST_PORT, backend.getPort());
    }

    @Test
    void testGetHost() {
        assertEquals(TEST_HOST, backend.getHost());
    }
}