/**
 * File:    SecurityProfileModel.js
 * Author:  @Bharat
 * Date:    02/05/16
 */
define(
    [
        'BaseModel'
    ],
    function(BaseModel) {

       return BaseModel.extend({

            urlRoot: '/securityProfile',

            defaults: {
                id: null,
                securityProfileName: ''
            }

        });

    }

);