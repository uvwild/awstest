package org.funtime.config;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertNotSame;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by uv on 10/12/2015 for configserver
 */
@ActiveProfiles("default") // this is redundant but marked for clarity
@Configuration
@EnableConfigurationProperties
//@ConfigurationProperties(locations = "classpath:/bootstrap.yml", ignoreInvalidFields = true, ignoreUnknownFields = true)
public class ConfigServerDefaultITest extends AbstractIntegrationTest {


    @Value("${dummy}")
    String dummy;

    @Value("${info.component}")
    String name;

    @Value("${server.port}")
    int port;

    @Value("${management.context-path}")
    String adminPath;

    //    @Value("${local.management}")
    private BootstrapProperties.Management management;

    private BootstrapProperties.Server server;

//    @Value("${local.server.contextPath}")
//    private String contextPath;


    @Test
    public void testAdminEnvironment() {
        // as properties always read local props they have to be different
        String adminPath = (String) properties.getProperty("management.context-path");
        assertNotSame(adminPath, this.adminPath);
        assertNotSame(adminPath, this.parentAdminPath);

        // we also do not get the right env
        LinkedHashMap result = getEntity(adminPath + "/env", LinkedHashMap.class);
        assertTrue(result != null);

        ArrayList profiles = (ArrayList) result.get("profiles");
        assertTrue(profiles instanceof ArrayList);
        assertTrue(profiles.size() == 1);
        assertTrue("env".equalsIgnoreCase(String.valueOf(profiles.get(0))));
        assertNull(result.get("integrationTest"));

        // NOW use the right path
        result = getEntity(this.adminPath + "/env", LinkedHashMap.class);
        LinkedHashMap integrationTest = (LinkedHashMap) result.get("integrationTest");
        assertTrue(integrationTest instanceof LinkedHashMap);
        assertTrue(integrationTest.size() > 0);
        assertTrue("true".equalsIgnoreCase(String.valueOf(integrationTest.get("org.springframework.boot.test.IntegrationTest"))));
    }

    @Test
    public void testDummy() {
        String dummyp = (String) properties.getProperty("dummy");
        assertTrue(dummyp.length() > 3);
        System.out.println(String.format("Default: prop: %s val: %s", dummyp, dummy));
        assertFalse("first Loaded from file, second from value", dummyp.contentEquals(dummy));
    }

    // this compares the randomport from the integration test with the one from the configuration file.
    @Test
    public void testServerPort() {
        int serverPort = (Integer) properties.getProperty("server.port");
        System.out.println(String.format("Default: prop: %d %s.server.port: %d local.server.port: %d",
                                         serverPort,
                                         StringUtils.collectionToDelimitedString(Arrays.asList(env.getActiveProfiles()), ","),
                                         port,
                                         getLocalPort()));
        assertNotSame(serverPort, getLocalPort());
        assertNotSame(serverPort, port);
        assertNotSame(getLocalPort(), port);
    }

    @Test
    public void testManagement() {
        String adminPath = (String) properties.getProperty("management.context-path");
//        assertEquals(adminPath, management.getContextPath());
    }

}
