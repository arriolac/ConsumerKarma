// Author: Chris Arriola <arriolac279@gmail.com>
// Filename: main.js

require.config({
    paths: {
        jquery: 'libs/jquery-min',
        underscore: 'libs/underscore-1.1.6',
        parse: 'libs/parse-1.0.18-min',
        templates: '../templates'
    }
});

require([
    // Load application module and pass it to definition function
    'application',
], function($) {
});
