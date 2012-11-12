$(function() {

    Parse.$ = jQuery;

    Parse.initialize("xXnJr64tDsIdqugTpfJxg9nKaUE6oEH6tg3M7j74",
        "0AdYLo6RCJ6PpPkxoMWV37w4L3HCV7GBj9Ay4IEZ");

    //-----------
    // Models
    //-----------

    //-----------
    // Views
    //-----------

    // Add New Item View
    var AddNewItemView = Parse.View.extend({

        events: {
        },

        el: ".content",

        initialize: function() {
            _.bindAll(this, 'render', 'addItem');

            this.render();
        },

        render: function() {
            this.$el.html(_.template($("#add-new-item-template").html()));
        },

        addItem: function() {
            // TODO
        }
    });

    // Log-in View 
    var LogInView = Parse.View.extend({
        events: {
            "submit form.login-form": "logIn",
            "submit form.signup-form": "signUp"
        },

        el: ".content",

        initialize: function() {
            _.bindAll(this, "logIn", "signUp");
            this.render();
        },

        logIn: function(e) {
            var self = this;
            var username = this.$("#login-username").val();
            var password = this.$("#login-password").val();

            Parse.User.logIn(username, password, {
                success: function(user) {
                    new SearchForProductView();
                    self.undelegateEvents();
                    delete self;
                },

                error: function(user, error) {
                    self.$(".login-form .error").html("Invalid username or password. Please try again.").show();
                    this.$(".login-form button").removeAttr("disabled");
                }
            });

            this.$(".login-form button").attr("disabled", "disabled");

            return false;
        },

        signUp: function(e) {
            var self = this;
            var username = this.$("#signup-username").val();
            var password = this.$("#signup-password").val();

            Parse.User.signUp(username, password, { ACL: new Parse.ACL() }, {
                success: function(user) {
                    new SearchForProductView();
                    self.undelegateEvents();
                    delete self;
                },

                error: function(user, error) {
                    self.$(".signup-form .error").html(error.message).show();
                    this.$(".signup-form button").removeAttr("disabled");
                }
            });

            this.$(".signup-form button").attr("disabled", "disabled");

            return false;
        },

        render: function() {
            this.$el.html(_.template($("#login-template").html()));
            this.delegateEvents();
        }
    });
    
    // Search for Product View
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

            //this.searchResults = new SearchResultsList();
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

            var Item = Parse.Object.extend("Item");
            var query = new Parse.Query(Item);
            query.matches("title", this.input.val(), "i");
            query.find({
                success: function(result) {
                    $("#search-results-list").html("");
                    var singleItem;
                    for (var i = 0; i < result.length; i++) {
                        singleItem = result[i];
                        $("#search-results-list").append(singleItem.get("title") + "</br>");
                    }
                },
                error: function(error) {
                    console.log("Error: " + error);
                }
            });
        },
    });



    // The main view for the app
    var AppView = Parse.View.extend({
        // Instead of generating a new element, bind to the existing skeleton of
        // the App already present in the HTML.
        el: $("#consumer-karma-app"),

        initialize: function() {
            this.render();
        },

        render: function() {
            if (Parse.User.current()) {
                new SearchForProductView();
            } else {
                new LogInView();
            }
        }
    });

    var AppRouter = Parse.Router.extend({
        routes: {
            "" : "search",                  // index
            "add-new-item": "addNewItem"    // #add-new-item
        },

        search: function() {
            new SearchForProductView();
        },

        addNewItem: function() {
            new AddNewItemView();
        }
    });

    new AppView;
    new AppRouter;

    Parse.history.start();
});
