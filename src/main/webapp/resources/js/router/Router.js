/**
 * File:    Router.js
 * Author:  @bharat
 * Date:    29/04/16
 */

define(
    [
        'underscore',
        'backbone',
        'MainView',
        'UserController',
        'ManagementController',
        'CollectionController',
        'NewController'
        
    ],
    function(
        _,
        Backbone,
        MainView,
        UserController,
        ManagementController,
        CollectionController,
        NewController


    ) {

        return Backbone.Router.extend({

            initialize: function() {

                // Tells Backbone to start watching for hashchange events.
                if (!Backbone.History.started) {
                    Backbone.history.start();
                }
            },

            navigate: function(fragment, options) {
                Backbone.history.navigate(fragment, options);
                return this;
            },

            routes: {
                '': 'home',
                'management/:module': 'management',
                'collection/:model': 'collection',
                'new/:model': 'new',
                'user/:id': 'user',
               
            },
            home: function() {
                new MainView();
            },
            "management": function(module) {
                new ManagementController().show(module);
            },
             "collection": function(model) {
                new CollectionController().show(model);
            },
            "new": function(model) {
                new NewController().show(model);
            },
            "user": function(id) {
                new UserController().fetchOne(id);
            },
            
            /**
             * Function: parseQueryString
             * ==========================
             * This function takes a query string and returns an
             * object with the key value pairs present in the string.
             * @param queryString
             * @returns {{}}
             */
            parseQueryString: function(queryString) {
                var params = {};
                if (queryString) {
                    _.each(
                        _.map(decodeURI(queryString).split(/&/g),
                              function(el, i) {
                            var aux = el.split('='), o = {};
                            if (aux.length >= 1){
                                var val = undefined;
                                if (aux.length == 2) {
                                    val = aux[1];
                                }
                                o[aux[0]] = val;
                            }
                            return o;
                        }),
                        function(o) {
                            _.extend(params,o);
                        }
                    );
                }
                return params;
            }
        }); 

    }
);