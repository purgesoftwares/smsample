/**
 * File:    validator.js
 * Author:  @juancarlosfarah
 * Date:    06/03/2016
 */

// Initialise exports.
exports = module.exports = {};

// Constants
// =========

// Define and export the minimum characters accepted in a password.
exports.PASSWORD_MIN_LENGTH = 8;


/**
 * Validates that the password and the password confirmation
 * fields contain the same character sequence.
 * @param password
 * @param confirmPassword
 */
const passwordsMatch = function (password, confirmPassword) {
    const undefined = typeof password === 'undefined' ||
                      typeof confirmPassword === 'undefined';
    if (undefined) {
        return false;
    }
    return password === confirmPassword;
};

/**
 * Validates that the password is long enough.
 * @param password
 */
const isLongEnough = function (password) {

    // Do not accept null or undefined arguments.
    if (!password) {
        return false;
    }
    return password.length >= exports.PASSWORD_MIN_LENGTH;

};

/**
 * Returns true if the password contains the username.
 * @param password
 * @param $username
 */
function containsUsername(password, $username) {
    // Extract username part of email.
    var username = $username.val().split("@", 1)[0];
    return containsText(password, username);
}

/**
 * Returns true if the password contains the value of a field.
 * @param password
 * @param $field
 */
function containsField(password, $field) {
    var field = $field.val();
    return containsText(password, field);
}

/**
 * Returns true if the password contains some text.
 * If the text is the empty string, it returns false.
 * @param password
 * @param text
 * @returns {boolean}
 */
const containsText = function (password, text) {
    if (!password || !text) {
        return false;
    }
    var pw = password.toLowerCase();
    return pw.indexOf(text.toLowerCase()) !== -1;
};

/**
 * Returns true if the password contains a lower case character.
 * @param password
 * @returns {boolean}
 */
function containsLowerCaseChar(password) {
    return password.match(/[a-z]/g) !== null;
}

/**
 * Returns true if the password contains an uppercase character.
 * @param password
 * @returns {boolean}
 */
containsUpperCaseChar = function (password) {
    if (!password) {
        return false;
    }
    return password.match(/[A-Z]/g) !== null;
};

/**
 * Returns true if the password contains a digit.
 * @param password
 * @returns {boolean}
 */
function containsDigit(password) {
    return password.match(/[0-9]/g) !== null;
}

/**
 * Returns true if the password contains a special character.
 * @param password
 * @returns {boolean}
 */
function containsSpecialChar(password) {
    return password.match(/[!@#\$%\^&*\)\(+=._-]/g) !== null;
}

/**
 * Validates the password given the rules above.
 * @param $firstName
 * @param $lastName
 * @param $username
 * @param password
 * @returns {boolean}
 */
function validatePassword($firstName, $lastName, $username, password) {
    const specialChars = '(!@#$%^&*+=._-)';
    var isValid = true;

    if (!isLongEnough(password)) {
        toastr.error('Password must be at least 8 characters long!');
        isValid = false;
    }

    if (containsUsername(password, $username)) {
        toastr.error('Password cannot contain username or email!');
        isValid = false;
    }

    if (containsField(password, $firstName)) {
        toastr.error('Password cannot contain first name!');
        isValid = false;
    }

    // Check if password contains last name.
    if (containsField(password, $lastName)) {
        toastr.error('Password cannot contain last name!');
        isValid = false;
    }

    // Check if password contains a lowercase character.
    if (!containsLowerCaseChar(password)) {
        toastr.error('Password must contain a lowercase character!');
        isValid = false;
    }

    // Check if password contains a lowercase character.
    if (!containsUpperCaseChar(password)) {
        toastr.error('Password must contain an uppercase character!');
        isValid = false;
    }

    // Check if password contains a digit.
    if (!containsDigit(password)) {
        toastr.error('Password must contain digit!');
        isValid = false;
    }

    // Check if password contains a digit.
    if (!containsSpecialChar(password)) {
        toastr.error('Password must contain a special character ' +
                     specialChars + '!');
        isValid = false;
    }
    return isValid;
}

/**
 * Enforces the following policy.
 * - Password and confirm password must match.
 * - Passwords can’t contain the username.
 * - Password can't contain parts of the user’s full name.
 * - Passwords must have a length of at least 8 characters.
 * - Passwords must use a lowercase letter, an uppercase letter,
 *   a number and a special character.
 * @param $form
 * @param $firstName
 * @param $lastName
 * @param $username
 * @param $password
 * @param $confirmPassword
 */
function enforcePolicy($form,
                       $firstName,
                       $lastName,
                       $username,
                       $password,
                       $confirmPassword) {

    // Check if passwords match when confirm password is blurred.
    $confirmPassword.blur(function() {
        var password = $password.val(),
            confirmPassword = $confirmPassword.val();
        if (!passwordsMatch(password, confirmPassword)) {
            toastr.error('Passwords must match!');
        }
    });

    // Validate password when password is blurred.
    $password.blur(function() {

        // Reset value of confirm password.
        $confirmPassword.val('');

        // Extract and validate password.
        var password = $password.val();
        validatePassword($firstName, $lastName, $username, password);
    });

    // Validate the password on submit.
    $form.submit(function(event) {

        // Clear error messages.
        toastr.clear();

        // Extract and validate password.
        var password = $password.val();
        const isValid = validatePassword($firstName,
                                         $lastName,
                                         $username,
                                         password);

        // If it's value, submit, otherwise prevent submission.
        if (isValid) {
            return;
        }
        event.preventDefault();
    })

}

// Module Exports
// ==============
exports.passwordsMatch = passwordsMatch;
exports.isLongEnough = isLongEnough;
exports.containsText = containsText;
exports.containsUpperCaseChar = containsUpperCaseChar;