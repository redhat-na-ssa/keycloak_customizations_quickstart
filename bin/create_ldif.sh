#!/bin/bash

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
LDIF_USERS_TEMPLATE=$SCRIPT_DIR/ldap-template-users.ldif
LDIF_ROLES_TEMPLATE=$SCRIPT_DIR/ldap-template-roles.ldif
LDIF_DIR=$SCRIPT_DIR/../etc/openldap
LDIF_GENERATED=$LDIF_DIR/ldap-generated.ldif

usersToAdd=2

for var in $@
do
    case $var in
        -usersToAdd=*)
            usersToAdd=`echo $var | cut -f2 -d\=`
            ;;
    esac
done

newUser='dn:\u020uid=bwilson{id},ou=People,dc=example,dc=org\nobjectclass:\u020top\nobjectclass:\u020person\nobjectclass:\u020organizationalPerson\nobjectclass:\u020inetOrgPerson\nuid:\u020\u062wilson{id}\ncn:\u020\u042rian\nsn:\u020Wilson\nmail:\u020\u062wilson{id}@keycloak.org\npostalCode:\u020z88441\nstreet:\u020HWY285\nuserPassword:\u020password\n\n'

newRoleMember='member:\u020uid=bwilson{id},ou=People,dc=example,dc=org\n'

start() {
    echo -en "\nUsers to add to ldif: $usersToAdd\n"
}
addUsers() {
    cat $LDIF_USERS_TEMPLATE > $LDIF_GENERATED
    startT=$( date '+%s' )
    for (( i=1; i<=$usersToAdd; i++ ))
    do
        userdif=$( echo $newUser | sed "s/{id}/$i/g" );
        echo -en $userdif >> $LDIF_GENERATED

        modulus=$(($i%500))
        if [ $modulus == 0 ]; then
            now=$(date '+%s');
            deltaT=$(($now - $startT))
            echo -en "\n$i  Added 500 more users in $deltaT seconds";
            startT=$( date '+%s' )
        fi
    done
}
addRoleMembers() {
    cat $LDIF_ROLES_TEMPLATE >> $LDIF_GENERATED
    for (( i=1; i<=$usersToAdd; i++ ))
    do
        memberdif=$( echo $newRoleMember | sed "s/{id}/$i/g" );
        echo -en $memberdif >> $LDIF_GENERATED
        modulus=$(($i%500))
        if [ $modulus == 0 ]; then
            now=$(date '+%s');
            deltaT=$(($now - $startT))
            echo -en "\n$i  Added 500 more roleMembers in $deltaT seconds";
            startT=$( date '+%s' )
        fi
    done
}
finish() {
    #echo -en $( cat $LDIF_GENERATED ) > $LDIF_GENERATED
    sed -i 's/^ //g' $LDIF_GENERATED
    echo -en "\nJust generated an ldif file located at $LDIF_GENERATED with $usersToAdd additional users.\n"
}

start
addUsers
addRoleMembers
finish
