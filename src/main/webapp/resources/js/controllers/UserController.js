/**
 * File:    UserController.js
 * Author:  @Bharat
 * Date:    28/04/16
 */

define(
    [
        "jquery",
        "backbone",
        "ModelController",
        "UserModel",
        "UserCollection",
        "UserView",
        "UserCollectionView"
    ],
    function(
        $,
        Backbone,
        ModelController,
        UserModel,
        UserCollection,
        UserView,
        UserCollectionView
    ) {

        function UserController() {}

        // Inherit methods from Controller.
        UserController.prototype = Object.create(ModelController.prototype);

        // Define specific properties.
        UserController.prototype.model = UserModel;
        UserController.prototype.collection = UserCollection;
        UserController.prototype.modelView = UserView;
        UserController.prototype.collectionView = UserCollectionView;

        return UserController;

    }

);
