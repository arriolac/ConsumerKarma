// Author: Chris Arriola <arriolac279@gmail.com>
// Filename: main.js

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
    'jquery',
    'underscore',
    'parse',
    'application',
], function($, _, Parse, Application) {
    console.log($);
    console.log(_);
    console.log(Parse);
    console.log(Application.random);
    //Application.initialize();
});
