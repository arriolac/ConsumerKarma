$(function() {

    Parse.$ = jQuery;

    Parse.initialize("xXnJr64tDsIdqugTpfJxg9nKaUE6oEH6tg3M7j74",
        "0AdYLo6RCJ6PpPkxoMWV37w4L3HCV7GBj9Ay4IEZ");

    // View for adding a new item
    var AddNewItemView = Parse.View.extend({

        events: {
            "click .log-out": "logOut",
        },

        el: ".content",

        initialize: function() {
            //_.bindAll(this, 'logOut');

            this.$el.html(_.template($("#add-new-item-template").html()));
        },
    });

    // The main view for the app
    var AppView = Parse.View.extend({
        // Instead of generating a new element, bind to the existing skeleton of
        // the App already present in the HTML.
        el: $("#main"),

        initialize: function() {
            this.render();
        },

        render: function() {
            new AddNewItemView();
        }
    });

    new AppView;
});
