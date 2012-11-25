// Author: Chris Arriola <arriolac279@gmail.com>
// Filename: main.js
// Description: Sets up application.

require.config({
    paths: {
        jquery: 'libs/jquery-min',
        underscore: 'libs/underscore-min',
        parse: 'libs/parse-min',
        templates: '../templates'
    },

    shim: {
        'parse': {
            deps: ['jquery', 'underscore'],
            exports: 'Parse'
        }
    }
});

require([
    // Load application module and pass it to definition function
    'application',
], function(Application) {
    Application.initialize();
});
