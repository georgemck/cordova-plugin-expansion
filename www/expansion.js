var exec = require('cordova/exec');

module.exports = {
    getExpansionPaths: function(callback) {
        exec(callback, null, 'Expansion', 'getExpansionPaths', []);
    },
    getFile: function(filename, callback) {
        exec(callback, null, 'Expansion', 'getFile', [filename]);
    },
    media: {
        isPlaying: function(callback) {
            exec(callback, null, 'Expansion', 'isPlaying', []);
        },
        pause: function() {
            exec(null, null, 'Expansion', 'pauseMedia', []);
        },
        play: function(filename) {
            exec(null, null, 'Expansion', 'playMedia', [filename]);
        },
        stop: function() {
            exec(null, null, 'Expansion', 'stopMedia', []);
        }
    }
};