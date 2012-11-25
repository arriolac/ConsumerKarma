// Author: Chris Arriola <arriolac279@gmail.com>
// Filename: main.js

require.config({
    paths: {
        jquery: 'libs/jquery-min',
        underscore: 'libs/underscore-min',
        parse: 'libs/parse-min',
        templates: '../templates'
    }
});

require([
    // Load application module and pass it to definition function
    'application',
], function(Application) {
    //console.log(Application.random);
    Application.initialize();
});
