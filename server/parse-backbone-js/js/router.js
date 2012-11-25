// Author: Chris Arriola <arriolac279@gmail.com>
// Filename: router.js
// Defines router for routing requests to the appropriate views.

define([
    'jquery',
    'underscore',
    'parse',
    'views/HomeView',
], function($, _, Parse, HomeView) {
    var AppRouter = Parse.Router.extend({
        routes: {
            "search/:query": "search",      // search
            "add-new-item": "addNewItem",   // #add-new-item
            "" : "main",                    // index
        },

        main: function() {
            new HomeView();
        },

        search: function(query) {
            // TODO: do parse query
            alert("Query is: " + query);
        },

        addNewItem: function() {
            new AddNewItemView();
        }
    });

    var initialize = function() {
        var router = new AppRouter();
        Parse.history.start();
    };

    return {
        initialize: initialize
    };
});
