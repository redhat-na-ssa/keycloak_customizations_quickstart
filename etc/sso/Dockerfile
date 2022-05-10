# podman build --rm -f etc/sso/Dockerfile -t quay.io/jbride/keycloak:17.0.0-4-psql .

FROM quay.io/keycloak/keycloak:17.0.0

EXPOSE 8080

ENV KC_METRICS_ENABLED=true
ENV KC_FEATURES=token-exchange
ENV KC_DB=postgres

COPY etc/sso/providers/org.acme-lucky-number-mapper-0.0.1.jar /opt/keycloak/providers/org.acme-lucky-number-mapper-0.0.1.jar

RUN /opt/keycloak/bin/kc.sh build
