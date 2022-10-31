# podman build --rm -f etc/sso/Dockerfile -t quay.io/jbride/keycloak:19.0.1.4-psql .

# https://www.keycloak.org/2022/07/keycloak-1900-released
FROM quay.io/keycloak/keycloak:19.0.1 as builder

EXPOSE 8080

ENV KC_HEALTH_ENABLED=true
ENV KC_METRICS_ENABLED=true

# https://www.keycloak.org/server/features
#  NOTE:  Can be over-written at boot with '--features="x,y,z" argument
ENV KC_FEATURES="token-exchange,scripts"

# https://www.keycloak.org/server/caching#_specify_your_own_cache_configuration_file
ENV KC_CACHE_CONFIG_FILE=cache-ispn-jdbc-ping.xml
COPY etc/sso/cache-ispn-jdbc-ping.xml /opt/keycloak/conf/cache-ispn-jdbc-ping.xml

RUN curl -sL https://github.com/aerogear/keycloak-metrics-spi/releases/download/2.5.3/keycloak-metrics-spi-2.5.3.jar -o /opt/keycloak/providers/keycloak-metrics-spi-2.5.3.jar

# Used to demonstrate custom protocol mapper
COPY etc/sso/providers/org.acme-lucky-number-mapper-0.0.1.jar /opt/keycloak/providers/org.acme-lucky-number-mapper-0.0.1.jar

# Used to demonstrate javascript based UMA policies
# https://github.com/keycloak/keycloak-quickstarts/blob/latest/app-authz-photoz/README.md
# https://www.keycloak.org/docs/latest/server_development/index.html#_script_providers
COPY etc/sso/providers/photoz-js-policies.jar /opt/keycloak/providers/photoz-js-policies.jar

# Used to demonstrate implementation of a transient authenticator
# https://www.janua.fr/transient-sessions-in-keycloak/
COPY etc/sso/providers/org.acme-transient-authn-0.0.1.jar /opt/keycloak/providers/org.acme-transient-authn-0.0.1.jar

# Execute a build after adding customer providers
RUN /opt/keycloak/bin/kc.sh build

# for demonstration purposes only, please make sure to use proper certificates in production instead
# RUN keytool -genkeypair -storepass password -storetype PKCS12 -keyalg RSA -keysize 2048 -dname "CN=server" -alias server ext "SAN:c=DNS:localhost,IP:127.0.0.1" -keystore conf/server.keystore

# With "start-dev" mode, keycloak uses only local caches
CMD ["sh", "/opt/keycloak/bin/kc.sh", "start-dev"]
