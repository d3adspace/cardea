package de.d3adspace.cardea.config;

import de.d3adspace.cardea.backend.Backend;
import de.d3adspace.cardea.backend.BackendBalancingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Felix Klauke <info@felix-klauke.de>
 */
class CardeaConfigTest {

    private static final int TEST_PORT = -1;
    private static final BackendBalancingType TEST_POLICY = BackendBalancingType.ROUND_ROBIN;
    private static final List<Backend> TEST_BACKENDS = new ArrayList<>();
    private CardeaConfig cardeaConfig;

    @BeforeEach
    void setUp() {
        cardeaConfig = new CardeaConfig(TEST_PORT, TEST_BACKENDS, TEST_POLICY);
    }

    @Test
    void getBackends() {
        assertEquals(TEST_BACKENDS, cardeaConfig.getBackends());
    }

    @Test
    void getServerPort() {
        assertEquals(TEST_PORT, cardeaConfig.getServerPort());
    }

    @Test
    void getBalancingPolicy() {
        assertEquals(TEST_POLICY, cardeaConfig.getBalancingPolicy());
    }
}