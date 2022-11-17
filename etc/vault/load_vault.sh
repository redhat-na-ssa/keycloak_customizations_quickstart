#!/bin/sh

echo -en "OS user is: $( id ) \n"

VAULT_RETRIES=5
until vault status > /dev/null 2>&1 || [ "$VAULT_RETRIES" -eq 0 ]; do
        echo "Waiting for vault to start...: $((VAULT_RETRIES--))"
        sleep 2
done
echo "... vault started at:  $VAULT_ADDR"

echo -en "\nAdding entries to vault\n"

vault login token=vault-plaintext-root-token
vault secrets enable -version=2 -path=rhsso kv

vault kv put -mount=rhsso kc-demo/boidc client_name=boidc client_password=password

echo -en "\nComplete..."

tail -f /dev/null
