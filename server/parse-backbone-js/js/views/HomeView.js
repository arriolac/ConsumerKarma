// Author: Chris Arriola <arriolac279@gmail.com>
// Filename: HomeView.js
// Description: Switch for log-in or search view.

define([
    'jquery',
    'underscore',
    'parse',
    'views/LogInView',
    'views/SearchForProductView',
], function($, _, Parse, LogInView, SearchForProductView) {

    // The main view for the app
    var HomeView = Parse.View.extend({
        // Instead of generating a new element, bind to the existing skeleton of
        // the App already present in the HTML.
        el: $("#consumer-karma-app"),

        initialize: function() {

            // Highlight page selection
            $('.navbar li').removeClass('active');
            $('.navbar li a[href="#"]').parent().addClass('active');

            this.render();
        },

        render: function() {
            new SearchForProductView();
        }
    });
    return HomeView;
});
