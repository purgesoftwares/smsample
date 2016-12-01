/**
 * File:    SecurityProfileCollection.js
 * Author:  @Bharat
 * Date:    02/05/16
 */
define(
    [
        'SecurityProfileModel',
        'BaseCollection'
    ],
    function(
        SecurityProfileModel,
        BaseCollection
    ) {

        return BaseCollection.extend({
            url: '/security-profile',
            model: SecurityProfileModel
        });

    }
);
