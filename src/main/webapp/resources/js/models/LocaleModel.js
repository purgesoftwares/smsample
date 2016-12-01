/**
 * File:    LocaleModel.js
 * Author:  @bharat
 * Date:    07/12/15
 */

define(
    [
        BaseModel   
    ],
    function(BaseModel) {
       
        return BaseModel.extend({

            urlRoot:'/locale',

            defaults: {
            	locale: 'en',
            	name: 'English'
            },

            validation: {
            	locale: {
                    required: true
                },
                name: {
                    required: true
                }
            },
            
            labels: {
            	locale: 'Locale',
            	name: 'Name'
            }

        });
        
    }

);
