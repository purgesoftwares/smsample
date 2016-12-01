/**
 * File:    BaseCollection.js
 * Author:  @juancarlosfarah
 * Date:    27/01/2016
 */
define(
    [
        'backbone'
    ],
    function(Backbone) {

        var CURRENT_API = '/v1';

        return Backbone.Collection.extend({
            api: CURRENT_API
        });

    }
);