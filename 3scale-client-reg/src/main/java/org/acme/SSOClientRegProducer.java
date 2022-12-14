package org.acme;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

import org.acme.keycloak.KeycloakClient;
import org.acme.okta.OktaClient;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Dependent
public class SSOClientRegProducer {

    public final String KEYCLOAK = "keycloak";
    public final String OKTA = "okta";

    @ConfigProperty(name = "org.acme.ssoclientreg.service.impl", defaultValue = KEYCLOAK)
    private String ssoClientRegImpl;

    @Produces
    @Singleton
    private ISSOClientRegistration getSSOClientRegImpl() {
        if(ssoClientRegImpl.equals(KEYCLOAK))
            return new KeycloakClient();
        else if(ssoClientRegImpl.equals(OKTA))
            return new OktaClient();
        else
            throw new RuntimeException("Invalid SSO Client Impl: "+ssoClientRegImpl);
    }
    
}
