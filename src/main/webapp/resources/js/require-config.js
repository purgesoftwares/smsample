/**
 * File:    require-config.js
 * Author:  @bharat
 * Date:    04/02/16
 */
// Require.js Configuration
// ------------------------
require.config({

    "waitSeconds": 0,

    // Sets the JS folder as the base directory for relative paths.
    baseUrl: "/resources/js/",

    // Third-party script alias names (easier to type "jquery"
    // than "libs/jquery") probably a good idea to keep version
    // numbers in the file names for update checking.
    paths: {

        // Core Libraries
        "jquery": "vendor/jquery-1.11.2.min",
        "jqueryui": "vendor/jquery-ui.min",
        "underscore": "vendor/underscore-min",
        "backbone": "vendor/backbone-min",
        "backbone-autocomplete": "vendor/backbone.autocomplete-min",
        "moment": "vendor/moment.min",
        "highcharts": "vendor/highcharts",
        "bootstrap": "vendor/bootstrap.min",
        "async": "vendor/async",
        "backbone-validation": "vendor/backbone-validation-min",
        "toastr": "vendor/toastr.min",
        "preloader": "vendor/preloader",
        "backbone-paginator": "vendor/backbone.paginator",
        "backgrid": "vendor/backgrid",
        "backgrid-paginator":
            "vendor/extensions/paginator/backgrid-paginator",
        "backgrid-text-cell":
            "vendor/extensions/text-cell/backgrid-text-cell",
        "backgrid-moment-cell":
            "vendor/extensions/moment-cell/backgrid-moment-cell",
        "imgareaselect": "vendor/jquery.imgareaselect.min",
        "bootbox": "vendor/bootbox.min",
        "utils": "vendor/utils",
        "bootstrapToggle":
            "vendor/bootstrap-toggle/js/bootstrap-toggle.min",
        "backbone-nested": "vendor/backbone-nested",
        "polyglot": "vendor/polyglot",
        "select2": "vendor/select2/select2",
        "moment-duration-format":
            "vendor/extensions/moment-duration-format",
        // Need to use the full version as it contains a hotfix as
        // explained here: jsfiddle.net/juancarlosfarah/kqgoegpw/6/
        "bootstrap-datetime-picker":
            "vendor/bootstrap-datetimepicker",
        "moment-timezone": "vendor/moment-timezone-with-data.min",
        "backgrid-select2-cell":
            "vendor/extensions/select2-cell/backgrid-select2-cell.min",
        
        
        // Jasmine libraries
        jasmine: 'vendor/jasmine/lib/jasmine',
        "jasmine-html": 'vendor/jasmine/lib/jasmine-html',
        spec: 'views/specs/',
        "jasmine-jquery": 'vendor/jasmine/lib/jasmine-jquery',


        // Router
        router: "router/Router",

        // Views
        MainView: "views/MainView",
        ModelView: "views/ModelView",
        ManagementView: "views/ManagementView",
        UserView: "views/UserView",
        UserCollectionView: "views/UserCollectionView",
        
        
        // Controllers
        ModelController: 'controllers/ModelController',
        UserController: 'controllers/UserController',
        ManagementController: 'controllers/ManagementController',
        CollectionController: 'controllers/CollectionController',
        NewController: 'controllers/NewController',
        

        // Models
        BaseModel: "models/BaseModel",
        UserModel: "models/UserModel",
        ManagementModel: "models/ManagementModel",
        LocaleModel: "models/LocaleModel",
        SecurityProfileModel: "models/SecurityProfileModel",
        ProfilePictureModel: "models/ProfilePictureModel",
       

        // Collections
        BaseCollection: "collections/BaseCollection",
        BasePageableCollection: "collections/BasePageableCollection",
        UserCollection: "collections/UserCollection",
        LocaleCollection: "collections/LocaleCollection",
        SecurityProfileCollection: "collections/SecurityProfileCollection",
        ProfilePictureCollection: "collections/ProfilePictureCollection"
    },

    // Sets the configuration for third-party
    // scripts that are not AMD compatible.
    shim: {

        'bootstrap': ["jquery"],
        'jqueryui': ["jquery"],
        'highcharts': ["jquery"],
        'backbone-validation': [
            'jquery',
            'underscore',
            'backbone'
        ],
        'backgrid': {
            deps: [
                'jquery',
                'underscore',
                'backbone'
            ],
            exports: 'Backgrid'
        },
        'backbone-nested': [
           'jquery',
           'underscore',
           'backbone'
        ],
        'backgrid-paginator': {
            deps: [
                'jquery',
                'underscore',
                'backbone',
                'backbone-paginator',
                'backgrid'
            ]
        },
        'backgrid-moment-cell': [
            'backgrid',
            'moment',
            'jquery',
            'underscore',
            'backbone'
        ],
        'backgrid-select2-cell': [
             'backgrid',
             'moment',
             'jquery',
             'underscore',
             'backbone'
         ],
        'moment-duration-format': [
            'moment'
        ],
        'bootstrap-datetime-picker': {
            deps: [
                'moment',
                'moment-timezone',
                'jquery'
            ]
        },
        "moment-timezone": [
            'moment'
        ],
        jasmine: {
            exports: 'jasmine'
        },
        'jasmine-html': {
            deps: ['jasmine'],
            exports: 'jasmine'
        },
        'jasmine-jquery': {
            deps: ['jasmine'],
            exports: 'jasmine'
        }
    }

});