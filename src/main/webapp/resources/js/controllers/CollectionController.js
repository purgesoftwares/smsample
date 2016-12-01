/**
 * File:    CollectionController.js
 * Author:  @Bharat
 * Date:    28/04/16
 */
define(
    [
        "jquery",
        "backbone",
        "UserController"
    ],
    function(
        $,
        Backbone,
        UserController
        
    ) {

        function CollectionController() {}

        /**
         * Fetches the given model's collection view.
         * @param model
         */
        CollectionController.prototype.show = function(model) {

            switch(model) {

                case 'user':
                    return new UserController().fetchAll();
            }

        };

        return CollectionController;

    }

);