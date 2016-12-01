/**
 * File:    ManagementController.js
 * Author:  @Bharat
 * Date:    28/04/16
 */
define(["jquery",
        "backbone",
        "ManagementModel",
        "ManagementView"],

    function($,
             Backbone,
             ManagementModel,
             ManagementView) {

        function ManagementController() {}

        /**
         * Fetches the given module's management panel.
         * @param module
         */
        ManagementController.prototype.show = function(module) {

            var props = {};
            switch(module) {

                case 'user':
                    props = {
                        title: 'Users',
                        panels: [
                            {
                                title: 'Users',
                                body: 'Manage what users you have.',
                                icon: 'users',
                                path: 'collection/user'
                            }
                        ]
                    };
                    break;
                    
            }

            var mm = new ManagementModel(props);
            return new ManagementView({ 'model': mm });

        };

        return ManagementController;

    }

);
