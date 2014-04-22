var exec = require('cordova/exec');

module.exports = {
    getPaths: function(callback) {
        exec(callback, null, 'Expansion', 'getPaths', []);
    },
    getFile: function(filename, callback) {
        exec(callback, null, 'Expansion', 'getFile', [filename]);
    },
    load: function(mainVersion, patchVersion) {
        exec(null, null, 'Expansion', 'load', [mainVersion, patchVersion]);
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