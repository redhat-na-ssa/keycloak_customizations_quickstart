# podman build --rm -f etc/sso/Dockerfile -t quay.io/jbride/keycloak:18.0.0-5-psql .

# https://www.keycloak.org/server/containers
FROM quay.io/keycloak/keycloak:18.0.0

EXPOSE 8080

ENV KC_HEALTH_ENABLED=true
ENV KC_METRICS_ENABLED=true
ENV KC_FEATURES=token-exchange
ENV KC_DB=postgres

RUN curl -sL https://github.com/aerogear/keycloak-metrics-spi/releases/download/2.5.3/keycloak-metrics-spi-2.5.3.jar -o /opt/keycloak/providers/keycloak-metrics-spi-2.5.3.jar

# Used to demonstrate custom protocol mapper
COPY etc/sso/providers/org.acme-lucky-number-mapper-0.0.1.jar /opt/keycloak/providers/org.acme-lucky-number-mapper-0.0.1.jar

RUN /opt/keycloak/bin/kc.sh build
