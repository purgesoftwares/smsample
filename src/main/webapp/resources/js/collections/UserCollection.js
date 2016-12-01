/**
 * File:    UserCollection.js
 * Author:  @Bharat
 * Date:    28/04/16
 */
define(
    [
        "UserModel",
        "BasePageableCollection"
    ],
    function(
        UserModel,
        BasePageableCollection
    ) {

        return BasePageableCollection.extend({
            url: '/user',
            model: UserModel
        });

    }
);