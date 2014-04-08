var exec = require('cordova/exec');

module.exports = {
    get: function(filename, success, error) {
        exec(success, success, 'Expansion', 'get', [filename]);
    }
};