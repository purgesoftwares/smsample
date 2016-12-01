/**
 * File:    UserView.js
 * Author:  @Bharat
 * Date:    30/04/16
 */
define(
    [
        'jquery',
        'backbone',
        'text!templates/UserView.html',
        'text!templates/Breadcrumbs.html',
        'UserModel',
        'SecurityProfileCollection',
        'ProfilePictureModel',
        'imgareaselect',
        'utils',
        'toastr',
        'async',
        'bootbox',
        'ModelView',
        'select2',
        'bootstrapToggle'],

    function($,
             Backbone,
             template,
             breadcrumbsTemplate,
             UserModel,
             SecurityProfileCollection,
             ProfilePictureModel,
             imgareaselect,
             utils,
             toastr,
             async,
             bootbox,
             ModelView,
             select2,
             bootstrapToggle) {

        return ModelView.extend({

            // The DOM Element associated with this view.
            el: $("#app_container"),

            initialize: function() {
                var spc = new SecurityProfileCollection();
                var uv = this;
                async.series([ 
                    function(callback){
                         spc.fetch().success(function() {
                         uv.spc = spc;
                         callback(null, spc);
                        });
                    },
                    function(callback){
                        if(uv.model.get('profilePictureId') != null){
                                var ppm = new ProfilePictureModel({id: uv.model.get('profilePictureId')});
                                ppm.fetch().success(function() {
                                uv.ppm = ppm;
                                callback(null, ppm);
                            });
                        }else{
                            uv.ppm = undefined;
                            callback(null, {});
                            }
                     },

                ],
                // Callback.
                function (err, result) {
                        uv.render();
                        var $ppm = $('#profilePictureModel');
                            $ppm.on('shown.bs.modal', function() {
                                if(typeof uv.ppm != 'undefined'){
                                    selection = $('#pictureToCrop').imgAreaSelect({
                                        x1: uv.ppm.get('xposition'),
                                        y1: uv.ppm.get('yposition'),
                                        x2: (uv.ppm.get('xposition') + uv.ppm.get('width')),
                                        y2: (uv.ppm.get('yposition') + uv.ppm.get('height')),
                                        aspectRatio: '1:1',
                                        minHeight: 200,
                                        minWidth: 200,
                                        maxHeight: 400,
                                        maxWidth: 400,
                                        resizable: true,
                                        show:false,
                                        instance:true 
                                    });
                                }else{
                                    selection = $('#pictureToCrop').imgAreaSelect({
                                        x1: 0, y1: 0, x2: 300, y2: 300,
                                        aspectRatio: '1:1',
                                        minHeight: 200,
                                        minWidth: 200,
                                        maxHeight: 400,
                                        maxWidth: 400,
                                        resizable: true,
                                        show:false,
                                         instance:true 
                                    });
                                }
                                    
                                });
                                $ppm.on('hidden.bs.modal', function () {
                                    $('#pictureToCrop').imgAreaSelect({
                                        remove:true
                                    });
                                });
                }

                );    
                                   
            },


            // Event Handlers.
            events: ModelView.prototype.mainEvents.call(
        			this,
        			{
                        'click #change-picture': 'changePicture',
                        'click .remove-picture': 'removePicture',
                        'click #saveProfilePicture': 'saveProfilePicture',
                        'click .closeProfilePictureModal': 'closeProfilePictureModal',
                        'change #imgInp': 'updateProfilePictureURl'
                    }
            ),

            // To change the picture
            changePicture: function(event) {
                $("#imgInp").trigger("click");
            },

            closeProfilePictureModal: function(event){
                $('#pictureToCrop').imgAreaSelect({
                    remove:true
                });
                $('#profilePictureModel').modal("hide");
            },

            updateProfilePictureURl: function(event){
                var file = $('#imgInp')[0].files[0];
                var maxFileSize = 3;
                
                 if (typeof file !== "undefined") {
                     if(validateImage(file.name)) {
                        if(!checkFileSize(maxFileSize)){
                            toastr.options.closeButton = true;
                            toastr.error("This image is too large. Maximum file size is 3 MB.");
                        }else{
                            var image_input = $("#imgInp")[0];
                            updateProfilePictureURl(image_input, toastr);
                        }   
                    }else{
                        // Show a error messages.
                        toastr.options.closeButton = true;
                        toastr.error("Sorry, The Image type is invalid, allowed extensions are: %{validImageExtensions}");
                    }    
                }       
            },

             // To remove the picture
            removePicture: function(event) {
                event.stopImmediatePropagation();
                var uv = this;
                var file = $('#imgInp')[0].files[0];
                if (typeof file !== "undefined" || $("#profilePictureId").val()) {
                    bootbox.confirm("Are you sure to remove the picture?", function(result) {
                        if(result){
                            if(uv.model.get('profilePictureId') != null){
                                uv.model.validation.newPassword.required = false;
                                //uv.model.validation.gender.required = false;
                                if(uv.model.get('locale') == null)
                                    uv.model.set({ locale: rbv.model.defaults.locale });
                                uv.model.save({ profilePictureId: null}).success(function() {
                                    if ($("#profilePictureId").val()) {
                                        ppm = new ProfilePictureModel({
                                            id: $("#profilePictureId").val()
                                        });
                                        ppm.destroy();
                                    }
                                    toastr.options.closeButton = true;
                                    toastr.success("Profile Picture Removed.");
                                    //uv.model.validation.gender.required = true;
                                })
                                .error(function(){
                                    //uv.model.validation.gender.required = true;
                                });
                            }else{
                                if ($("#profilePictureId").val()) {
                                    ppm = new ProfilePictureModel({
                                        id: $("#profilePictureId").val()
                                    });
                                    ppm.destroy();
                                }
                                toastr.options.closeButton = true;
                                toastr.success("Profile Picture Removed.");
                            }
                            $('#imgInp').val("");
                            $('#cropedProfilePicture, #pictureToCrop').attr("src","../resources/images/Default-Profile-Picture.jpg");

                        }
                    }); 
                }else{
                    toastr.options.closeButton = true;
                    toastr.error("Profile Picture not available to Remove.");
                }
            },
              uploadFile: function(file, ppm) {
                var rbv = this;
                
                if (typeof file !== "undefined") {
                    var data = new FormData();
                    data.append('file', file);
                    data.append('sub-directory', "profile_pictures");
                    $.ajax({
                        url: '/files/uploadFile',
                        data: data,
                        cache: false,
                        contentType: false,
                        processData: false,
                        type: 'POST'
                    }).done(function(data) {
                        
                        if (typeof data.id !== "undefined"){
                            ppm.set({ profilePictureId : data.id });
                            ppm.validate();

                            // Save if valid.
                            if (ppm.isValid()) {
                                ppm.save().success(function() {
                                    rbv.ppm = ppm;
                                    rbv.uf  = data;
                                    rbv.updatePictureURL();
                                    if (!rbv.model.isNew()) {
                                        rbv.model.validation.newPassword.required = false;
                                                                          
                                        rbv.model.save({ profilePictureId : ppm.get("id")})
                                        .success(function(){
                                            $('#profilePictureModel').modal("hide");
                                            toastr.options.closeButton = true;
                                            toastr.success("Profile Picture Updated");
                                            preloader.stop();
                                        })
                                        .error(function(){
                                            toastr.options.closeButton = true;
                                            toastr.error("Profile Picture Not Updated.");
                                            preloader.stop();
                                        });
                                    }else{
                                        $('#profilePictureModel').modal("hide");
                                        toastr.options.closeButton = true;
                                        toastr.success("Profile Picture Updated.");
                                        preloader.stop();
                                    }
                                    
                                });

                            } else {

                                // Show any error messages.
                                toastr.options.closeButton = true;
                                toastr.options.newestOnTop = false;
                                toastr.error("Error in saving Profile Picture.");
                                preloader.stop();
                            }

                        } else {
                            toastr.error("Error uploading file.");
                            preloader.stop();
                        }
                        
                    }).fail(function() {
                        toastr.error("Error uploading file.");
                        preloader.stop();
                        return false;
                    });
                } else {
                    toastr.error("No file selected.");
                    preloader.stop();
                    return false;
                }

            },

             // To save the picture
            saveProfilePicture: function(event) {
                event.preventDefault();
                toastr.remove();
                rbv = this;
                var file = $('#imgInp')[0].files[0];
                var maxFileSize = 3;
                 
                if (typeof file !== "undefined") {
                     if(validateImage(file.name)){                  
                        if(!checkFileSize(maxFileSize)){
                            toastr.options.closeButton = true;
                            toastr.error("This image is too large. Maximum file size is 3 MB.");
                        }else{
                            preloader.start();
                            var props = {
                                    xposition: selection.getSelection().x1,
                                    yposition: selection.getSelection().y1,
                                    width: selection.getSelection().width,
                                    height: selection.getSelection().height
                            };
                            var ppm = new ProfilePictureModel();
                            ppm.set(props);
                            rbv.uploadFile(file, ppm);
                        }
                     } else {
                        // Show a error messages.
                         toastr.options.closeButton = true;
                         toastr.error("Sorry, The Image type is invalid, allowed extensions are: %{validImageExtensions}");
                    }
                     
                } else if(typeof rbv.ppm != "undefined") {
                    preloader.start();

                    var props = {
                            xposition: selection.getSelection().x1,
                            yposition: selection.getSelection().y1,
                            width: selection.getSelection().width,
                            height: selection.getSelection().height
                    };
                    rbv.ppm.set(props);
                    rbv.ppm.save().success(function(){
                        
                        rbv.uf  = rbv.ppm.get('profilePicture');
                        rbv.updatePictureURL();
                        $('#profilePictureModel').modal("hide");
                        toastr.options.closeButton = true;
                        toastr.success("Profile Picture Updated.");
                        
                        preloader.stop();
                    }).error(function(model, error) {
                        console.log(model);
                        console.error(error);
                        preloader.stop();
                        toastr.options.closeButton = true;
                        toastr.error("Error in saving Profile Picture.");
                    });
                                        
                } else {
                     toastr.options.closeButton = true;
                     toastr.error("Please change the image to update.");
                 }
                 
                 return this;
            },
             // To update the picture url
            updatePictureURL: function() {
                $("#cropedProfilePicture").attr('src',
                                                "../files/getProfilePicture/" +
                                                this.uf.id + "/" +
                                                this.uf.filename + "?" +
                                                new Date().getTime());
                $("#profilePictureId").val(this.ppm.id);
            },
          
            save: function(event){

                event.preventDefault();

                // Remove any previous validation errors.
                $('.form-group').removeClass('has-error');
                toastr.remove();

                // Fetch roles.
                var roles = [];
                $('#roles').find('input:checked').each(function() {
                    roles.push($(this).val());
                });

                var props = {
                    "id": $('#id').val(),
                    "username": $('#username').val(),
                    "firstName": $('#firstName').val(),
                    "lastName": $('#lastName').val(),
                    "gender": $('#gender').val(),
                    "newPassword": ($("#newPassword").val() ? $("#newPassword").val() : null),
                    "userRolesString": roles,
                    "enabled": $('#enabled').prop('checked'),
                    "oldPassword": ($("#oldPassword").val() ? $("#oldPassword").val() : null),
                    "confirmNewPassword": ($("#confirmNewPassword").val() ? $("#confirmNewPassword").val() : null),
                    "verified": $('#verified').prop('checked'),
                    "blocked": $('#blocked').prop('checked')
                };

                //var um = new UserModel(props);
                this.model.set(props);
                
                if (!this.model.isNew()) {
                	this.model.validation.newPassword.required = false;
                }
                          
                // Assign this user view to a variable.
                var uv = this;
               
                // Validate and generate error messages.
                var validateObj = this.model.validate();
                
                // Save only if valid and if address and contact
                // information are not needed or needed and valid.

            	if (this.model.isValid() ) {

                    preloader.start();
                    // Save.
                    this.model.save(props, {
                        success: function(model, response) {
                        uv.model.set(response);
                        uv.finish('collection/user');
                        toastr.options.closeButton = true;
                        toastr.success("Saved");
                        preloader.stop();
                    },
                        error: function(model, error) {
                        preloader.stop();
                        utils.showErrorToastr(error);
                        }
                    });
                   
                } else {

                    // Show any error messages.
                    toastr.options.closeButton = true;
                    toastr.options.newestOnTop = false;
                    for (var key in validateObj) {
                        $('#' + key).closest('.form-group')
                                    .addClass('has-error');
                        toastr.error(validateObj[key] + '.');
                    }
                }

            },

            // Renders the view's template to the UI.
            render: function() {

                // Handle to model.
                var m = this.model;

                // Set the view's template property.
                this.template = _.template(template, {});

                var id = m.get('id'),
                    name = m.get('userName');
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
                        active: false
                    },
                    {
                        title: name,
                        path: '#user/' + id,
                        active: true
                    }
                ];
              
                // Dynamically update the UI with the view's template.
                this.$el.html(
                    this.template({
                        model: this.model.toJSON(),
                        spc: this.spc.toJSON(),
                        ppm: (m.get('profilePictureId') != null)?this.ppm.toJSON():{},
                        breadcrumbs: breadcrumbs,
                        renderBreadcrumbs: _.template(breadcrumbsTemplate, {})
                    })
                );
                var $form = $('#form');

                // Discern between displaying the form to create a
                // new object or the one to edit an existing object.
                if (m.isNew()) {
                    $form.find('.editable').prop('disabled', false);
                    
                } else {
                    $form.find('.btn-edit').addClass('hidden');
                }
                
                $('#user_password_form').hide();

                if($('body').hasClass('modal-open')){
                    $(".imgareaselect-selection").parent().remove();
                    $(".imgareaselect-outer").remove();
                    $(".modal-backdrop").remove();
                    $("body.modal-open").removeClass('modal-open');
                }
                                                
                $(".select2-dropdown").select2();
                
                this.afterRender();
                
                // Maintains chainability.
                return this;

            }

        });

    }
);