/** Copyright © 2015 by 1986 Tech, LLC. All Rights Reserved. **/

package com.allowify.user.service;

import java.util.Locale;

import com.allowify.model.User;


/**
 * 
 * @author Bharat
 *
 */
public interface UserDao {

	User findByUserName(String username);

	//User registerNewUserAccount(User user, Locale locale) throws EmailExistsException;
	
    //void createPasswordResetTokenForUser(User user, String token);

    //PasswordResetToken getPasswordResetToken(String token);

    //User getUserByPasswordResetToken(String token);

    User getUserByID(long id);

    void changeUserPassword(User user, String password);

    boolean checkIfValidOldPassword(User user, String password);

	void createVerificationTokenForUser(User user, String token);

	//VerificationToken getVerificationToken(String token);

	void saveRegisteredUser(User user);

	//VerificationToken generateNewVerificationToken(String existingToken);

	User getUser(String token);

}
