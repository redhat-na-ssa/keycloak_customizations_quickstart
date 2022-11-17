# podman build --rm -f etc/vault/Dockerfile -t quay.io/jbride/vault-client:1.12.1-0 .
# podman run --rm --name vault-client --net etc_sso -v ./etc/vault/load_vault.sh:/opt/vault/load_vault.sh quay.io/jbride/vault-client:1.12.1-0

FROM docker.io/library/vault:1.12.1

ENV VAULT_TOKEN=vault
ENV VAULT_ADDR=http://vault-dev:8200

CMD ["sh", "/opt/vault/load_vault.sh"]
