/**
 * File:    UserCollectionView.js
 * Author:  @Bharat
 * Date:    28/04/16
 */
define(
    [
        "jquery",
        "backbone",
        "text!templates/CollectionView.html",
        "text!templates/Breadcrumbs.html",
        "async",
        "backgrid",
        "backgrid-paginator"
    ],
    function(
        $,
        Backbone,
        template,
        breadcrumbsTemplate,
        async
    ) {

        return Backbone.View.extend({

            // The DOM Element associated with this view.
            el: $("#app_container"),

            initialize: function() {
            	preloader.start();
            	this.render();
            },

            // Clean the template from the container.
            cleanup: function() {
                this.undelegateEvents();
                $(this.el).empty();
            },

            // Event Handlers.
            events: {
                'click .nav-cleanup': 'cleanup',
                'click .search-button': 'search'
            },
            
            // Update with filtered result
            search: function(event) {
            	event.preventDefault();
            	q = $("#srch-term").val();
            	$(this.el).empty();
            	this.model.setQuery(q);
            	this.model.fetch();
            	this.render();
            	$("#srch-term").val(q)
            },

            // Renders the view's template to the UI.
            render: function() {
            	this.undelegateEvents();
            	     
                // Setting the view's template property
                // using the Underscore template method.
                this.template = _.template(template, {});

                var breadcrumbs = [
                    {
                        title: 'home',
                        path: '#',
                        active: false
                    },
                    {
                        title: 'User Management',
                        path: '#management/user',
                        active: false
                    },
                    {
                        title: 'User Collection',
                        path: '#collection/user',
                        active: true
                    }
                ];

                // Dynamically updates the UI with the view's template.
                this.$el.html(
                    this.template({
                        collection: this.model.toJSON(),
                        title: 'User',
                        path: 'user',
                        breadcrumbs: breadcrumbs,
                        renderBreadcrumbs: _.template(breadcrumbsTemplate, {})
                    })
                );
                
                var UserUriCell = Backgrid.UriCell.extend({
                	render: function () {
                	    this.$el.empty();
                	    var rawId = this.model.get('id');
                	    var rawValue = this.model.get(this.column.get("name"));
                	    var formattedValue = this.formatter.fromRaw(rawValue, this.model);
                	    this.$el.append($("<a>", {
                	      tabIndex: -1,
                	      href: "#user/"+rawId,
                	      title: this.title || formattedValue,
                	      target: "_self"
                	    }).text(formattedValue));
                	    this.delegateEvents();
                	    return this;
                	  }
                	});
                
                var firstName = Backgrid.UriCell.extend({
                	render: function () {
                	    this.$el.empty();
                	    var rawValue = this.model.get(this.column.get("name"));
                	    var formattedValue = this.formatter.fromRaw(rawValue, this.model);
                	    this.$el.text(formattedValue);
                	    this.delegateEvents();
                	    return this;
                	  }
                	});
        
		        var lastName = Backgrid.UriCell.extend({
		        	render: function () {
		        	    this.$el.empty();
		        	    var rawValue = this.model.get(this.column.get("name"));
		        	    var formattedValue = this.formatter.fromRaw(rawValue, this.model);
		        	    this.$el.text(formattedValue);
		        	    this.delegateEvents();
		        	    return this;
		        	  }
		        	});
        	
                
                var grid = new Backgrid.Grid({
                    columns: [{
                      name: "id",
                      label: 'ID',
                      cell: Backgrid.IntegerCell.extend({ orderSeparator: '' }),
                      sortable: true,
                      editable: false
                    }, {
                	  name: "firstName",
                	  label: 'First Name',
                      cell: firstName,
                      sortable: true,
                      editable: false
                    }, {
	                   name: "lastName",
	                   label: 'Last Name',
	                   cell: lastName,
	                   sortable: true,
	                   editable: false
	                }, {
                       name: "username",
                       label: 'Username',
                       cell: UserUriCell,
                       sortable: true,
                       editable: false
	                }, {
	                   name: "userRolesString",
                       label: 'Role',
                       cell: "string",
                       sortable: false,
                       editable: false
                    }, {
                       name: "lastUpdate",
                       label: 'Last Update',
                       cell: 'date',
                       sortable: true,
                       editable: false
                    }, {
                       name: "enabled",
                       label: 'Enabled',
                       cell: "boolean",
                       sortable: true,
                	   editable: false
                     }],

                    collection: this.model
                  });

                var paginator = new Backgrid.Extension.Paginator({
                    collection: this.model
                });
                
                async.retry({times: 5, interval: 100} , function(callback, results) {
                	if(document.getElementById("#grid-table")){
                		callback(null, true)
                	}else{ 
                		callback(true, false)
                	}
                }, function(err, result) {
                    var $paginator = $("#paginator");
                	$("#grid-table").html(grid.render().$el);
                    $paginator.html("");
                    $paginator.append(paginator.render().$el);
                	preloader.stop();
                });
                if($('body').hasClass('modal-open')){
                	$(".imgareaselect-selection").parent().remove();
                	$(".imgareaselect-outer").remove();
                	$(".modal-backdrop").remove();
                	$("body.modal-open").removeClass('modal-open');
                }
                
                // Maintains chainability.
                return this;

            }

        });

    }
);