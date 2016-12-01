/**
 * File:    ProfilePictureCollection.js
 * Author:  @bharat
 * Date:    02/02/16
 */
define(
    [
        'ProfilePictureModel',
        'BaseCollection'
    ],
    function(
        ProfilePictureModel,
        BaseCollection
    ) {

        return BaseCollection.extend({
            url: '/profile-picture',
            model: ProfilePictureModel
        });

    }
);
