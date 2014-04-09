var exec = require('cordova/exec');

module.exports = {
    getExpansionPaths: function(success, error) {
        exec(success, error, 'Expansion', 'getExpansionPaths');
    },
    getFile: function(filename, success, error) {
        exec(success, error, 'Expansion', 'getFile', [filename]);
    }
};