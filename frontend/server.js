const path = require('path');
const favicon = require('serve-favicon');
// const app = express();
var express = require('express'); // Get the module
var app = express();
const forceSSL = function () {
  return function (req, res, next) {
    if (req.headers['x-forwarded-proto'] !== 'https') {
      return res.redirect(
        ['https://', req.get('Host'), req.url].join('')
      );
    }
    next();
  }
};
app.use(express.static('./dist/{{lieblings-master}}'));
 
app.get('/*', function (req, res) {
  res.sendFile(path.join(__dirname,'/dist/{{lieblings-master}}/index.html'));
});
 
app.use(forceSSL());
 
app.listen(process.env.PORT || 8080);