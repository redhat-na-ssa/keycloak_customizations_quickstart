var express = require('express');
var app = express();
var stringReplace = require('string-replace-middleware');

var KC_URL = process.env.KC_URL || "http://localhost:4080/";
var SERVICE_URL = process.env.SERVICE_URL || "http://localhost:3000/secured";
var KC_REALM_ID = process.env.KC_REALM_ID || "kc-demo";
var KC_CLIENT_ID = process.env.KC_CLIENT_ID || "t-inspect";

app.use(stringReplace({
   'SERVICE_URL': SERVICE_URL,
   'KC_URL': KC_URL,
   'KC_REALM_ID': KC_REALM_ID,
   'KC_CLIENT_ID': KC_CLIENT_ID
}));
app.use(express.static('.'))

app.get('/', function(req, res) {
    res.render('index.html');
});

app.get('/client.js', function(req, res) {
    res.render('client.js');
});

app.listen(8000);
