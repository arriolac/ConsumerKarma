// Author: Chris Arriola <arriolac279@gmail.com>
// Filename: AddNewItemView.js
// Description: View for adding a product in the CK db.

define([
    'jquery',
    'underscore',
    'parse',
    'text!templates/addNewItemTemplate.html'
], function($, _, Parse, addNewItemTemplate) {
    // Add New Item View
    var AddNewItemView = Parse.View.extend({

        events: {
        },

        el: ".content",

        initialize: function() {

            // Highlight page selection
            $('.navbar li').removeClass('active');
            $('.navbar li a[href="#/add-new-item"]').parent().addClass('active');

            _.bindAll(this, 'render', 'addItem');
            this.render();
        },

        render: function() {
            this.$el.html(addNewItemTemplate);
        },

        addItem: function() {
            // TODO
        }
    });

    return AddNewItemView;
});
