var exec = require('cordova/exec');

module.exports = {
    getExpansionPaths: function(callback) {
        exec(callback, null, 'Expansion', 'getExpansionPaths');
    },
    getFile: function(filename, callback) {
        exec(callback, null, 'Expansion', 'getFile', [filename]);
    },
    isPlaying: function(callback) {
        exec(callback, null, 'Expansion', 'isPlaying');
    },
    pauseMedia: function() {
        exec(null, null, 'Expansion', 'pauseMedia');
    },
    setMedia: function(filename, callback) {
        exec(callback, null, 'Expansion', 'setMedia', [filename]);
    },
    startMedia: function() {
        exec(null, null, 'Expansion', 'startMedia');
    },
    stopMedia: function() {
        exec(null, null, 'Expansion', 'stopMedia');
    }
};