// Author: Chris Arriola <arriolac279@gmail.com>
// Filename: SearchForProductView.js
// Description: View for searching a product in the CK db.

define([
    'jquery',
    'underscore',
    'parse',
    //'views/LogInView'
], function($, _, Parse) {//, LogInView) {

    var SearchForProductView = Parse.View.extend({

        // Delegate event for searching
        events: {
            "keypress #search-product": "searchItem",
            "click .log-out": "logOut",
        },

        // Event binding - all rendering will be done on the "el"
        el: ".content",

        // Initialize this view
        initialize: function() {
            _.bindAll(this, 'render', 'logOut', 'searchItem');

            // Set up views
            this.$el.html(_.template($("#search-product-template").html()));

            this.input = this.$("#search-product");
        },

        render: function() {
            this.delegateEvents();
        },

        // logs out the user and shows the login view
        logOut: function(e) {
            Parse.User.logOut();
            new LogInView();
            this.undelegateEvents();
            delete this;
        },

        searchItem: function(e) {

            // Only proceed on ENTER
            if (e.keyCode != 13) return;

            // TODO: pass in router
            router.navigate("search/" + this.input.val(), {trigger: true});
            // var Item = Parse.Object.extend("Item");
            // var query = new Parse.Query(Item);
            // query.matches("title", this.input.val(), "i");
            // query.find({
            //     success: function(result) {
            //         $("#search-results-list").html("");
            //         var singleItem;
            //         for (var i = 0; i < result.length; i++) {
            //             singleItem = result[i];
            //             $("#search-results-list").append(singleItem.get("title") + "</br>");
            //         }
            //     },
            //     error: function(error) {
            //         console.log("Error: " + error);
            //     }
            // });
        },
    });

    return SearchForProductView;
});
