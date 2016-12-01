/**
 * File:    BasePageableCollection.js
 * Author:  @juancarlosfarah
 * Date:    27/01/2016
 */
define(
    [
        'underscore',
        'backbone',
        'backbone-paginator'
    ],
    function(_, Backbone, PageableCollection) {

        var parentSync = PageableCollection.prototype.sync;

        return PageableCollection.extend({

            
            // Initial pagination states.
            state: {
                firstPage: 0,
                currentPage: 0,
                pageSize: 20,
                sortKey: 'id',
                order: 1,
                query: ''
            },

            // You can remap the query parameters from `state` keys
            // from the default to those your server supports.
            queryParams: {
                totalPages: null,
                totalRecords: null,
                sortKey: 'sort',
                pageSize: 'size',
                currentPage: 'page',
                query: this.getQuery
            },

            getQuery: function() {
                return this.state.query;
            },

            // Set query for search.
            setQuery: function(query) {
                var state = this.state;
                if (query != state.query) {
                    state = _.clone(this._initState)
                }
                this.state = this._checkState(_.extend({}, state, {
                    query: query
                }));
            },

            // Get the state from API result.
            parseState: function(resp, queryParams, state, options) {
                return {
                    totalRecords: resp.totalElements,
                    totalPages: resp.totalPages
                };
            },

            // Get the actual records.
            parseRecords: function(resp, options) {
                return resp.content;
            },

            /**
             * Function: save
             * --------------
             * Allow collection to be saved in batch.
             * @param options
             */
            save: function (options) {
                Backbone.sync("create", this, options);
            },

            /**
             * @override sync
             * Add some code to support parsing of sorting
             * parameters by SortHandlerMethodArgumentResolver.
             */
            sync: function(method, model, options) {
                // options.type = 'POST' // this help with unicode on tomcat and can be removed..

                if (!options.data) {
                    return parentSync.apply(this, arguments)
                }
                var sortKey = _.result(this.queryParams,"sortKey");
                if (!sortKey in options.data) {
                    return parentSync.apply(this, arguments)
                }
                if (method == 'read'){
                    options.data["sort"] = options.data[sortKey];
                    var orderKey = _.result(this.queryParams, "order");
                    var STR_SPRING_SORT = "sort";
                    var STR_SEPERATOR = ",";
                    if (orderKey in options.data) {
                        options.data[STR_SPRING_SORT] =
                            options.data[STR_SPRING_SORT] +
                            STR_SEPERATOR +
                            options.data[orderKey];
                        /*if (sortKey !== STR_SPRING_SORT) {
                         delete options.data[sortKey];
                         }
                         delete options.data["order"];*/
                    }
                }
                return parentSync.apply(this, arguments);
            }

        });

    }
);