# podman build --rm -f Dockerfile -t quay.io/jbride/keycloak:17.0.0-2-psql .

FROM quay.io/keycloak/keycloak:17.0.0

EXPOSE 8080

# https://github.com/aerogear/keycloak-metrics-spi ????
ENV KC_METRICS_ENABLED=true

# https://www.keycloak.org/docs/latest/securing_apps/#_token-exchange
# The following are required to enable Token Exchange with an external Identity Provider
ENV KC_FEATURES=token-exchange,admin-fine-grained-authz


ENV KC_DB=postgres
RUN /opt/keycloak/bin/kc.sh build
