/**
 * File:    MainView.js
 * Author:  @Bharat
 * Date:    28/04/16
 */
 
define([
        "jquery",
        "backbone",
        "text!templates/MainView.html",
        "text!templates/Breadcrumbs.html",
        "toastr"
    ],
    function(
        $,
        Backbone,
        template,
        breadcrumbsTemplate,
        toastr
    ) {

        return Backbone.View.extend({

            // The DOM Element associated with this view
            el: $("#app_container"),

            // MainView constructor
            initialize: function() {
				// Calls the view's render method
                this.render();
               
            },

            // MainView Event Handlers
            events: {

            },

            // Renders the view's template to the UI
            render: function() {

                // Setting the view's template property using the Underscore template method
                this.template = _.template(template, {});

                var breadcrumbs = [
                    {
                        title: 'home',
                        path: '#',
                        active: true
                    }
                ];

                // Dynamically updates the UI with the view's template
                this.$el.html(
                    this.template({
                        breadcrumbs: breadcrumbs,
                        renderBreadcrumbs: _.template(breadcrumbsTemplate, {})
                    })
                );

                // TODO: Where do these variables come from?
                // They look like path variables, but we need to comment
                // to make sure that we know where they are being set.
               /* if (notVerified != false){
                	toastr.options.timeOut = 0;
                	toastr.options.extendedTimeOut = 0;
                	toastr.options.closeButton = true;
                    toastr.error(polyglot.t('verifyEmailAddress')+" (<a href=" +
                                 resendVerificationLink +
                                 ">"+polyglot.t('resendVerificationEmail')+"</a>).");
                }
*/
                toastr.options.timeOut = 5e3;
            	toastr.options.extendedTimeOut = 1e3;
            	
                /*if (message != null) {
                	toastr.options.closeButton = true;
                    toastr.success(message);                	
                }*/

            }

        });

    }
);