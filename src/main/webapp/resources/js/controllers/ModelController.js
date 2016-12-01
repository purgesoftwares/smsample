/**
 * File:    ModelController.js
 * Author:  @juancarlosfarah
 * Date:    28/11/15
 */

define(
    [],
    function() {

        function ModelController() {

            // These properties need to be
            // defined by all children classes.
            this.model = null;
            this.collection = null;
            this.modelView = null;
            this.collectionView = null;
        }

        /**
         * Fetches the given object and returns the model view.
         * @param id
         * @param params
         */
        ModelController.prototype.fetchOne = function(id, params) {
            var controller = this,
                model = new controller.model({ id: id });
            model.fetch().success(function() {
                return new controller.modelView({
                    model: model,
                    attributes: {
                        urlParams: params
                    }
                });
            });
        };

        /**
         * Fetches all objects and returns the collection view.
         */
        ModelController.prototype.fetchAll = function() {
            var controller = this,
                collection = new controller.collection();
            collection.fetch().success(function() {
                return new controller.collectionView({ model: collection });
            });
        };
        
        /**
         * Fetches nothing and returns the collection view.
         */
        ModelController.prototype.fetchViewOnly = function() {
            var controller = this,
                collection = new controller.collection();
            return new controller.collectionView({ model: collection });
        };

        /**
         * Returns the form to create a new object.
         */
        ModelController.prototype.fetchForm = function() {
            var controller = this,
                model = new controller.model();
            return new controller.modelView({ model: model });
        };

        return ModelController;

    }

);
