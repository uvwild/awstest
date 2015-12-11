package org.funtime.config;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

import static junit.framework.TestCase.assertNotSame;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by uv on 10/12/2015 for configserver
 */
// @ActiveProfiles("default") this is redundant
@ActiveProfiles("local") // with this the local profile is used for @Value
@Configuration
@EnableConfigurationProperties
public class ConfigServerLocalITest extends AbstractIntegrationTest {

    @Value("${dummy}")
    String dummy;

    @Value("${info.component}")
    String name;

    @Value("${management.context-path}")
    String adminPath;

    @Value("${server.port}")
    int port;


    //    @Value("${local.management}")
    private BootstrapProperties.Management management;

    private BootstrapProperties.Server server;

//    @Value("${local.server.contextPath}")
//    private String contextPath;


    @Autowired
    private ConfigurableListableBeanFactory beanFactory;


    @Test
    public void testAdminEnvironment() {
        String adminPath = (String) properties.getProperty("management.context-path");
        assertTrue(adminPath.equalsIgnoreCase(this.adminPath));
        assertTrue(adminPath.equalsIgnoreCase(this.parentAdminPath));

        LinkedHashMap result = getEntity(adminPath + "/env", LinkedHashMap.class);
        assertTrue(result != null);

        ArrayList profiles = (ArrayList) result.get("profiles");
        assertTrue(profiles instanceof ArrayList);
        assertTrue(profiles.size() > 0);
        assertTrue("default".equalsIgnoreCase(String.valueOf(profiles.get(0))) || "local".equalsIgnoreCase(String.valueOf(profiles.get(0))));

        LinkedHashMap integrationTest = (LinkedHashMap) result.get("integrationTest");
        assertTrue(integrationTest instanceof LinkedHashMap);
        assertTrue(integrationTest.size() > 0);
        assertTrue("true".equalsIgnoreCase(String.valueOf(integrationTest.get("org.springframework.boot.test.IntegrationTest"))));
    }

    @Test
    public void testDummy() {
        String dummyp = (String) properties.getProperty("dummy");
        assertTrue(dummyp.length() > 3);
        System.out.println(String.format("Local: prop: %s val: %s", dummyp, dummy));
        assertTrue("first Loaded from file, second from value", dummyp.contentEquals(dummy));
    }

    // this compares the randomport from the integration test with the one from the configuration file.
    @Test
    public void testServerPort() {
        int serverPort = (Integer) properties.getProperty("server.port");
        System.out.println(String.format("Local: prop: %d %s.server.port: %d local.server.port: %d",
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
