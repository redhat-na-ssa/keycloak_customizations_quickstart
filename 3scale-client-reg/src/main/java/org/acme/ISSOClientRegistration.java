package org.acme;

import org.acme.model.Client;
import org.acme.model.OIDC;

public interface ISSOClientRegistration {

    public Client deleteClient( String clientId );
    public Client readClient(String clientId);
    public Client saveClient( Client client );
    public OIDC getWellKnownOpenidConfiguration();
    
}
