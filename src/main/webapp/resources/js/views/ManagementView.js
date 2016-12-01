/**
 * File:    ManagementView.js
 * Author:  @Bharat
 * Date:    28/04/16
 */
define(
    [
        "jquery",
        "backbone",
        "text!templates/ManagementView.html",
        "text!templates/Breadcrumbs.html"
    ],
    function(
        $,
        Backbone,
        template,
        breadcrumbsTemplate
    ) {

        return Backbone.View.extend({

            // The DOM Element associated with this view.
            el: $("#app_container"),

            initialize: function() {
                this.render();
            },

            // Clean the template from the container.
            cleanup: function() {
                this.undelegateEvents();
                $(this.el).empty();
            },

            // Event Handlers.
            events: {
                'click .nav-cleanup': 'cleanup'
            },

            // Renders the view's template to the UI.
            render: function() {

                // Set the view's template property.
                this.template = _.template(template, {});

                var breadcrumbs = [
                    {
                        title: 'home',
                        path: '#',
                        active: false
                    },
                    {
                        title: this.model.get('title'),
                        path: '#management/' + this.model.get('title'),
                        active: true
                    }
                ];

                // Dynamically update the UI with the view's template.
                this.$el.html(
                    this.template({
                        model: this.model.toJSON(),
                        breadcrumbs: breadcrumbs,
                        renderBreadcrumbs: _.template(breadcrumbsTemplate, {})
                    })
                );

                // Maintains chainability.
                return this;

            }

        });

    }
);
