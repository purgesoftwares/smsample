/**
 * File:    App.js
 * Author:  @juancarlosfarah
 * Date:    07/12/15
 */
if (document.readyState === "interactive") {
	
	// Trigger popstate as a Backbone event
	window.onpopstate = function (event) {
	  Backbone.trigger('popstate', event);
	};

    // Include desktop-specific JavaScript files
    // here (or inside of desktop router).
    require(
        [
            "jquery",
            "backbone",
            "router",
            "polyglot",
            //"LocaleCollection",
            "async",
            "jqueryui",
            "bootstrap",
            "moment"
        ],
        function(
            $,
            Backbone,
            AppRouter,
            Polyglot,
           // LocaleCollection,
            async
        ) {
        	
             new AppRouter();
             /*
        	var defaultLocale = 'en';
            var cookie = "org.springframework.web.servlet.i18n." +
                         "CookieLocaleResolver.LOCALE";
        	var localLang = getCookie(cookie);
        	var locale = localLang || defaultLocale;

            async.series(
                [
                    function(callback) {
                        if (localLang) {
                            var localeCollection = new LocaleCollection();
                            localeCollection.fetch().success(function() {

                                var localeModel = localeCollection.where({
                                    locale: localLang
                                });

                                if (!localeModel.length) {

                                    localeModel = localeCollection.where({
                                        locale: localLang.split("_")[0]
                                    });

                                    if (localeModel.length) {
                                        locale = localeModel[0].get("locale");
                                    } else {
                                        locale = defaultLocale;
                                    }

                                } else {

                                    locale = localeModel[0].get("locale");

                                }
                                callback(null, locale);
                            });

                        } else {

                            callback(null, locale);

                        }
                    }
                ],
                function(err, results) {

                    // Gets the language file.
                    var path = '/resources/locales/' + locale + '.json';

                    $.getJSON(path, function(localData) {

                        window.polyglot = new Polyglot({phrases: localData});

                        // Instantiates a new Desktop Router instance.
                        new AppRouter();

                    }).fail(function(d, textStatus, error) {

                        // Gets the default language file.
                        var path = '/resources/locales/' +
                                   defaultLocale + '.json';

                        $.getJSON(path, function(defaultData) {

                            window.polyglot = new Polyglot({
                                phrases: defaultData
                            });

                            // Instantiates a new Desktop Router instance.
                            new AppRouter();
                        });
                    });
                }
            );*/
        }
    );
}