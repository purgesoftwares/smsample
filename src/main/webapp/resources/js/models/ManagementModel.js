/**
 * File:    ManagementModel.js
 * Author:  @Bharat
 * Date:    28/04/16
 */
define(
    [
        'BaseModel'
    ],
    function(BaseModel) {

        return BaseModel.extend({
            defaults: {
                title: '',
                panels: []
            }
        });

    }

);