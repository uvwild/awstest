package org.funtime.config;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertNotSame;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by uv on 10/12/2015 for configserver
 */
@ActiveProfiles("default") // this is redundant but marked for clarity
//@Configuration
@EnableConfigurationProperties
//@ConfigurationProperties(locations = "classpath:/bootstrap.yml", ignoreInvalidFields = true, ignoreUnknownFields = true)
public class ConfigServerDefaultITest extends AbstractConfigServerIntegrationTest {


    @Value("${dummy}")
    String dummy;

    @Value("${info.component}")
    String name;

    @Value("${server.port}")
    int port;

    @Value("${management.context-path}")
    String adminPath;

//    @Value("${management}")         //was local.management
//    private BootstrapProperties.Management management;

    private BootstrapProperties.Server server;

//    @Value("${local.server.contextPath}")
//    private String contextPath;

//    @Before
//    // a complicated way to access the bootstrap.yml file
//    // TODO However, this is using the local profile for no understood reason!!!
//    public void readBootstrapYaml() {
//        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
//        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
//        yaml.setResources(new ClassPathResource("bootstrap.yml"));
//        propertySourcesPlaceholderConfigurer.setProperties(yaml.getObject());
//        propertySourcesPlaceholderConfigurer.postProcessBeanFactory(beanFactory);
//        properties = propertySourcesPlaceholderConfigurer.getAppliedPropertySources().get(PropertySourcesPlaceholderConfigurer.LOCAL_PROPERTIES_PROPERTY_SOURCE_NAME);
//    }


    @Test
    public void testAdminEnvironment() {
        // as properties always read local props they have to be different
        String adminPath = (String) properties.getProperty("management.context-path");
        assertNotSame(adminPath, this.adminPath);

        // the configserver starts with path from default profile (super.adminPath )
        LinkedHashMap result = getEntity(this.adminPath + "/env", LinkedHashMap.class);
        assertTrue(result != null);

        Object object = result.get("profiles");
        assertTrue(object instanceof ArrayList);
        ArrayList profiles = (ArrayList) object;
        assertTrue(profiles.size() == 1);
        assertTrue("default".equalsIgnoreCase(String.valueOf(profiles.get(0))));
        Object objIT = result.get("integrationTest");
        assertTrue(objIT instanceof LinkedHashMap);

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
                                         StringUtils.collectionToDelimitedString(Arrays.asList(getEnv().getActiveProfiles()), ","),
                                         port,
                                         getLocalPort()));
        assertNotSame(serverPort, getLocalPort());
        assertNotSame(serverPort, port);
//        assertNotSame(getLocalPort(), port);
    }

    @Test
    public void testManagement() {
        String adminPath = (String) properties.getProperty("management.context-path");
//        assertEquals(adminPath, management.getContextPath());
    }

}
