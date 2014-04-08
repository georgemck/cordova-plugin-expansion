var exec = require('cordova/exec');

module.exports = {
    getFile: function(filename, success, error) {
        exec(success, success, 'Expansion', 'getFile', [filename]);
    }
};