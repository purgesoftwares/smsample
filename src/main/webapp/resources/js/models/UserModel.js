/**
 * File:    UserModel.js
 * Author:  @Bharat
 * Date:    28/04/16
 */
define(
    [
        'BaseModel'
    ],

    function(BaseModel) {

        return BaseModel.extend({

            urlRoot: '/user',

            defaults: {
                id: null,
                username: '',
                firstName: '',
                lastName: '',
                profilePictureId: null,
                gender:'',
                newPassword: '',
                userRolesString: [],
                enabled: true,
                isSelf: false,
                verified: true,
                blocked: false,
                lastLogin: ''
            },

            validation: {
                username: {
                    required: true,
                    pattern: 'email'
                },
                firstName:{
                    required: true
                },
                newPassword: {
                    required: true
                },
                userRolesString: {
                    required: false
                },
                enabled: {
                    required: false
                }
            },

            labels: {
                id: 'ID',
                username: 'Username',
                firstName: 'First Name',
                lastName: 'Last Name',
                profilePictureId: 'Profile Picture',
                newPassword: 'Password',
                userRolesString: 'User Roles',
                enabled: 'Enabled',
                isSelf: 'Is Self User',
                verified: 'Verified',
                blocked: 'Blocked',
                lastLogin: 'Last Login',
                lastEdit: 'Last Edit'
            }

        });

    }

);