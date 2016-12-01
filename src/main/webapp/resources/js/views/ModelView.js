/**
 * File:    ModelView.js
 * Author:  @bharat
 * Date:    30/04/16
 */

define(
    [
	    'jquery',
	    'backbone',
        'toastr',
        'bootbox',
        'preloader'
    ],
    function(
        $,
        Backbone,
        toastr,
        bootbox,
        preloader
    ) {

        return Backbone.View.extend({

            initialize: function(options) {
            	
            },
            
            popstate: function (event) {
            	bootbox.hideAll();
            	this.undelegateEvents();
            },
            
            // Clean the template from the container.
            cleanup: function() {
                toastr.remove();
                this.undelegateEvents();
                $(this.el).empty();
            },
            

            edit: function(event) {
                event.preventDefault();
                $('#form').find('.editable')
                          .prop('disabled', false)
                          .attr('data-disabled', false);
                $('#edit').addClass('disabled').blur();
                $('.btn-edit').removeClass('hidden');
                $('label').removeClass('no-click');
            },

            reset: function(event) {
                event.preventDefault();
                $('#form').find('.editable')
                          .prop('disabled', true)
                          .trigger("reset");
                $('#edit').removeClass('disabled');
                $('.btn-edit').addClass('hidden');
            },
            
            // Clean the template from the container.
            mainEvents: function(events) {
            	var mainEvents = {
                        'click .nav-cleanup': 'cleanup',
                        'click #save': 'save',
                        'click #edit': 'edit',
                        'click #delete': 'deleteAction',
                        'click #reset': 'reset',
                        'click #cancel': 'cancel'
                    };
            	
            	return merge_options(mainEvents, events);
            },
            
            finish: function(route) {
                this.cleanup();
                if (route) {
                    Backbone.history.navigate(
                        route,
                        {
                            trigger: true,
                            replace: true
                        }
                    );
                } else {
                    Backbone.history.history.back();
                }
            },
            
            cancel: function(event) {
            	event.preventDefault();
                this.cleanup();
                Backbone.history.history.back();
            },

            deleteAction: function() {
                var view = this;
                
                bootbox.confirm(
                		"Are you sure to delete? This action cannot be undone.", 
                		function(result) {
            		if (result) {
		            	preloader.start();
		            	view.model.destroy()
		            	.success(function(record, response) {
		            		preloader.stop();
		            		view.finish();
	            			toastr.options.closeButton = true;
                            toastr.success("Deleted Successfully.");
                        })
                        .error(function(response, error) {
                            preloader.stop();
                            if(response.hasOwnProperty('responseJSON')){
                            	var responseJSON = response.responseJSON;
                            	if(responseJSON.hasOwnProperty('message')){
                            		toastr.options.closeButton = true;
                                    toastr.error(response.responseJSON.message);
                            	}else{
                            		toastr.options.closeButton = true;
                                    toastr.error("Error in deleting.");
                            	}
                            }else{
                            	toastr.options.closeButton = true;
                                toastr.error("Error in deleting.");
                            }
                        });
            		}
            	});
            },

            addRow: function(event, modal, initialParameters) {
            	
            	event.preventDefault();
            	$(event.target).removeClass('addRow btn-primary fa fa-plus');
            	$(event.target).addClass('deleteRow btn-danger fa fa-times');
            	modal.add(initialParameters);
            },

            validateSubCollection: function(grid,
                                            isExpandable,
                                            noValidateParameters) {

                // Get the size of the grid.
                var gridSize = grid.collection.size();

                // Expandable collections have an extra row to add input.
                var size = isExpandable ? gridSize - 1 : gridSize;

                // Validate collection only if grid is not empty.
                if (size) {
            		var errorFlag = false;

                    grid.collection.forEach(function(model, key) {

                        if (key < (size)) {

                            // Temporarily set required validation
                            // to false for parameters that are not
                            // being validated by this method.
                            for (var param in noValidateParameters) {
            					model.validation[param].required = false;
            				}

                            // Validate.
                			var validateObj = model.validate();
                            if (!model.isValid()){
                				toastr.options.closeButton = true;
                				toastr.options.newestOnTop = false;
                				for (var key in validateObj) {
                					toastr.error(validateObj[key] + '.');
            					}
                				errorFlag = true;
            				}

                            // Set back required validation to true.
                			for (var param in noValidateParameters) {
                				model.validation[param].required = true;
                			}
                			
            			}

        			});

                    return errorFlag;
        		}
            },

            saveSubCollection: function(grid, setParameters, unsetParameters) {
            	if (grid.collection.size()) {
                    grid.collection.forEach(function(model){
                        model.set(setParameters);
                        for (var attrname in unsetParameters) {
                        	model.unset(attrname, { silent: true });
        				}
                        model.save();
                    });
                }
            },

            handleUrlParams: function(urlParams) {
                // Go directly into edit mode.
                if (urlParams['edit']) {
                    this.edit();
                }
            },

            handleErrors: function(validateObject) {
                // Show any error messages.
                toastr.options.closeButton = true;
                toastr.options.newestOnTop = false;
                for (var key in validateObject) {
                    $('#' + key).closest('.form-group')
                        .addClass('has-error');
                    toastr.error(validateObject[key] + '.');
                }
            },

			afterRender: function(){
				// Define what happens when the user presses ENTER
				$('input, select, textarea').on('keydown', function(e) { // For all the form fields
				    if (e.which == 13) { // If pressed ENTER
				    	
				        if (e.ctrlKey) {
				        	
				        	// submitting form using "ctrl + enter"
				        	$( "#save" ).trigger( "click" );
				        } else {
				        	
				        	// Create a list of the form fields
				            var fields = $(this).closest('form').find('input, select, textarea');
				            
				            // Number of fields 
				            var total = fields.length;
				            
				            // Current field index
				            var index = fields.index(this);
				            
				            // Find current position to focus field
				            // Reverse process for "shift + enter"
				            fields.eq(index + (e.shiftKey ? (index > 0 ? -1 :0 ) : (index < total ? +1: total))).focus();
				        }
				        return false;
				    }
				});
				
				this.listenTo(Backbone, 'popstate', this.popstate);
			}
        });
        
	}
);