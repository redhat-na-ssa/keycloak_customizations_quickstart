package org.acme.okta;

import org.jboss.logging.Logger;

import org.acme.ISSOClientRegistration;
import org.acme.model.Client;
import org.acme.model.OIDC;

public class OktaClient implements ISSOClientRegistration {

    private static Logger log = Logger.getLogger(OktaClient.class);

    @Override
    public Client deleteClient(String clientId) {
        log.infov("deleteClient() clientId = {0}", clientId);
        return null;
    }

    @Override
    public Client readClient(String clientId) {
        log.infov("readClient() clientId = {0}", clientId);
        return null;
    }

    @Override
    public Client saveClient(Client client) {
        log.infov("saveClient() clientId = {0}", client.getClientId());
        return null;
    }

    @Override
    public OIDC getWellKnownOpenidConfiguration() {
        log.info("getWellKnownOpenidConfiguration");
        return null;
    }
    
}
