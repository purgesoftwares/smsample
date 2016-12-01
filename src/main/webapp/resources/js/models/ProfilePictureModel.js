/**
 * File:    ProfilePictureModel.js
 * Author:  @bharat
 * Date:    02/05/16
 */
define(
    [
        'BaseModel'
    ],

    function(BaseModel) {

            return BaseModel.extend({

            urlRoot: '/profile-picture',

            defaults: {
                id: null,
                profilePictureId: null,
                xposition: 0,
                yposition: 0,
                width: 300,
                height: 300
            },

            validation: {
            	profilePictureId: {
                    required: true
                }
            },

            labels: {
                id: 'ID',
                profilePictureId: 'Profile Picture',
                xposition: 'X-Position',
                yposition: 'Y-Position',
                width: 'Width',
                height: 'Height'
            }

        });

    }

);