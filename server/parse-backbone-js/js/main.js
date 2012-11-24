// Author: Chris Arriola <arriolac279@gmail.com>
// Filename: main.js

require.config({
    paths: {
        jquery: 'libs/jquery-min',
        underscore: 'libs/underscore-min',
        parse: 'libs/parse-1.0.18-min',
        templates: '../templates'
    }
});

require([
    // Load application module and pass it to definition function
    'parse',
    'application',
], function(Parse, Application) {
    console.log(Parse);
    console.log(Application.random);
    Application.initialize();
});
