var exec = require('cordova/exec');

module.exports = {
    getExpansionPaths: function(success, error) {
        exec(success, success, 'Expansion', 'getExpansionPaths');
    },
    getFile: function(filename, success, error) {
        exec(success, success, 'Expansion', 'getFile', [filename]);
    }
};