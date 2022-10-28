package org.acme.example.trans_authn;

import java.util.ArrayList;
import java.util.List;

import org.keycloak.Config.Scope;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel.Requirement;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

public class TransientAuthenticatorFactory  implements AuthenticatorFactory {

    private static AuthenticationExecutionModel.Requirement[] REQUIREMENT_CHOICES = {
        AuthenticationExecutionModel.Requirement.REQUIRED,
        AuthenticationExecutionModel.Requirement.ALTERNATIVE,
        AuthenticationExecutionModel.Requirement.DISABLED
    };

    private static final List<ProviderConfigProperty> configProperties = new ArrayList<ProviderConfigProperty>();

    static{
        //ProviderConfigProperty property = new ProviderConfigProperty();
        //configProperties.add(property);
    }

    @Override
    public Authenticator create(KeycloakSession session) {
        return new TransientAuthenticator(session);
    }

    @Override
    public void init(Scope config) {
        
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
        
    }

    @Override
    public void close() {
        
    }

    @Override
    public String getId() {
        return "transient-authn";
    }

    // Text that will be shown in Admin Console when listing the Authenticator
    @Override
    public String getDisplayType() {
        return "Transient Authenticator";
    }

    @Override
    public String getReferenceCategory() {
        // TODO Auto-generated method stub
        return null;
    }

    // specifies to the Admin Console on whether the Authenticator can be configured within a flow
    @Override
    public boolean isConfigurable() {
        return false;
    }

    @Override
    public Requirement[] getRequirementChoices() {
        return REQUIREMENT_CHOICES;
    }

    // If an Authenticator is not configured for a user, the flow manager checks the value of this field.
    //   false: the flow aborts with an error
    //   true:  flow manager will invoke Authenticator.setRequiredActions()
    @Override
    public boolean isUserSetupAllowed() {
        return true;
    }

    // Tooltip text used in Admin Console when picking an Authenticator to bind to an execution
    @Override
    public String getHelpText() {
        return "Issue a token with no corresponding session (subsequently reducing overhead on RDBMS and cache)";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return configProperties;
    }

}