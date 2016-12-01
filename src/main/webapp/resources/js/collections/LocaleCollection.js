/**
 * File:    LocaleCollection.js
 * Author:  @bharat
 * Date:    07/12/15
 */
define(
    [
        'LocaleModel',
        'BaseCollection'
    ],
    function(
        LocaleModel,
        BaseCollection
    ) {

        return BaseCollection.extend({
            url: '/locale',
            model: LocaleModel
        });

    }
);
