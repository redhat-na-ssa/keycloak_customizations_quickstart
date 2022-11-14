import Keycloak from 'keycloak-js';

console.log("NODE_ENV = "+process.env.NODE_ENV);
console.log("RHSSO_URL= "+process.env.RHSSO_URL);
console.log("RHSSO_REALM= "+process.env.RHSSO_REALM);
console.log("RHSSO_CLIENT_ID= "+process.env.RHSSO_CLIENT_ID);
console.log("SERVICE_URL="+process.env.SERVICE_URL);

// https://www.keycloak.org/docs/latest/securing_apps/#_javascript_adapter
export const kc = new Keycloak({
    url: process.env.RHSSO_URL,
    realm: process.env.RHSSO_REALM,
    clientId: process.env.RHSSO_CLIENT_ID

});
kc.init( {
        onLoad: 'login-required'
    }
).then(
    function(authenticated) {
        profile();
    }
).catch(
    function(error) {
        alert('failed to initialize');
    }
);

function output(content) {
    if (typeof content === 'object') {
        content = JSON.stringify(content, null, 2)
    }
    document.getElementById('output').textContent = content;
}

export function sendRequest() {
    var req = new XMLHttpRequest();
    req.onreadystatechange = function() {
        if (req.readyState === 4) {
            output(req.status + '\n\n' + req.responseText);
        }
    }
    req.open('GET', process.env.SERVICE_URL, true);
    req.setRequestHeader('Authorization', 'Bearer ' + kc.token);
    req.send();
}

function profile() {
    if (kc.idTokenParsed.name) {
        document.getElementById('name').textContent = 'Hello ' + kc.idTokenParsed.name;
    } else {
        document.getElementById('name').textContent = 'Hello ' + kc.idTokenParsed.preferred_username;
    }
    if (kc.idTokenParsed.avatar_url) {
        document.getElementById('avatar_url').src = kc.idTokenParsed.avatar_url;
    }
    document.getElementById('logout').onclick = function(){ kc.logout()};
    document.getElementById('showId').onclick = function() { output(kc.idTokenParsed)};
    document.getElementById('showAccess').onclick = function() { output(kc.tokenParsed)};
    document.getElementById('refresh').onclick = function(){ kc.updateToken(-1).then(function() { output(kc.idTokenParsed); profile() })};
    document.getElementById('invokeService').onclick = function(){ sendRequest()};
}
