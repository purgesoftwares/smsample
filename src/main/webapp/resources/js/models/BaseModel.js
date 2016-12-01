/**
 * File:    BaseModel.js
 * Author:  @juancarlosfarah
 * Date:    27/01/2016
 */
define(
    [
        'backbone',
        'underscore',
        'backbone-validation'
    ],
    function(Backbone, _) {

        _.extend(Backbone.Model.prototype, Backbone.Validation.mixin);

        var CURRENT_API = '/v1';

        return Backbone.Model.extend({
            api: CURRENT_API
        });
    }
);