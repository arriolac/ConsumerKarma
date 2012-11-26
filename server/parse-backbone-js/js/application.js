// Author: Chris Arriola <arriolac279@gmail.com>
// Filename: application.js

define([
   'jquery',
   'underscore',
   'parse',
   'router'
], function($, _, Parse, AppRouter) {
    var initialize = function() {
        Parse.$ = jQuery;
        Parse.initialize("xXnJr64tDsIdqugTpfJxg9nKaUE6oEH6tg3M7j74",
            "0AdYLo6RCJ6PpPkxoMWV37w4L3HCV7GBj9Ay4IEZ");
        AppRouter.initialize();
    };

    return {
        initialize: initialize,
    };
});
