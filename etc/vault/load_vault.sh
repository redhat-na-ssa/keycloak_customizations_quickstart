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
vault kv put -mount=rhsso siteadmin/psql user_name=sso user_password=sso database_name=sso
vault kv put -mount=rhsso kc-demo/client/boidc client_name=boidc client_password=password

# When using a kv secret engine version 2, secrets are written and fetched at path <mount>/data/<secret-path> as opposed to <mount>/<secret-path> in a kv secret engine version 1
# It does not change any of the CLI commands (i.e. you do not specify data in your path). However it does change the policies, since capabilities are applied to the real path.
cat <<EOF | vault policy write sso-site-admin-policy -
path "rhsso/data/siteadmin/*" {
  capabilities = ["read"]
}
EOF
cat <<EOF | vault policy write sso-kc-demo-policy -
path "rhsso/data/kc-demo/*" {
  capabilities = ["read"]
}
EOF

# Test: vault login -method=userpass username=sso-site-admin password=admin
vault auth enable userpass
vault write auth/userpass/users/sso-site-admin password=admin policies="sso-site-admin-policy,sso-kc-demo-policy"

echo -en "\nComplete..."

tail -f /dev/null
