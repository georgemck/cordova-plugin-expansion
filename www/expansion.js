var exec = require('cordova/exec');

module.exports = {
    getPaths: function(callback) {
        exec(callback, null, 'ExpansionPlugin', 'getPaths', []);
    },
    getFile: function(filename, callback) {
        exec(callback, null, 'ExpansionPlugin', 'getFile', [filename]);
    },
    load: function(mainVersion, patchVersion) {
        exec(null, null, 'ExpansionPlugin', 'load', [mainVersion, patchVersion]);
    },
    media: {
        isPlaying: function(callback) {
            exec(callback, null, 'ExpansionPlugin', 'isPlaying', []);
        },
        pause: function() {
            exec(null, null, 'ExpansionPlugin', 'pauseMedia', []);
        },
        play: function(filename) {
            exec(null, null, 'ExpansionPlugin', 'playMedia', [filename]);
        },
        stop: function() {
            exec(null, null, 'ExpansionPlugin', 'stopMedia', []);
        }
    }
};