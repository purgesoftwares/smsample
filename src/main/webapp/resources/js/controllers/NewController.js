/**
 * File:    NewController.js
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

        function NewController() {}

        /**
         * Fetches the given model's new form view.
         * @param model
         */
        NewController.prototype.show = function(model) {

            switch(model) {

                case 'user':
                    return new UserController().fetchForm();
            }

        };

        return NewController;

    }

);