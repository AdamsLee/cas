package org.apereo.cas.authentication;

import org.apereo.cas.config.CasCoreAuthenticationConfiguration;
import org.apereo.cas.config.CasCoreServicesConfiguration;
import org.apereo.cas.config.CasCoreUtilConfiguration;
import org.apereo.cas.config.CasPersonDirectoryAttributeRepositoryConfiguration;
import org.apereo.cas.config.StormpathAuthenticationConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;


/**
 * This is {@link StormpathAuthenticationHandlerTests}.
 *
 * @author Misagh Moayyed
 * @since 4.2.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(
        classes = {StormpathAuthenticationConfiguration.class,
                CasCoreAuthenticationConfiguration.class,
                CasCoreUtilConfiguration.class,
                CasPersonDirectoryAttributeRepositoryConfiguration.class,
                CasCoreServicesConfiguration.class,
                RefreshAutoConfiguration.class},
        initializers = ConfigFileApplicationContextInitializer.class)
public class StormpathAuthenticationHandlerTests {
    @Autowired
    @Qualifier("stormpathAuthenticationHandler")
    private AuthenticationHandler authenticationHandler;

    @Test
    public void verifyAuthentication() throws Exception {
        final HandlerResult result = this.authenticationHandler.authenticate(TestUtils
                .getCredentialsWithDifferentUsernameAndPassword("casuser", "12345678mM"));
        assertEquals(result.getPrincipal().getId(), "casuser");
        assertTrue(result.getPrincipal().getAttributes().containsKey("fullName"));
        assertTrue(result.getPrincipal().getAttributes().containsKey("email"));
    }
}
