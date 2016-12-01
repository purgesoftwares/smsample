/** Copyright © 2015 by 1986 Tech, LLC. All Rights Reserved. **/

package com.allowify.user.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.allowify.exceptions.EmailExistsException;
import com.allowify.model.User;
import com.allowify.repository.UserRepositoryCustom;


/**
 * 
 * @author Bharat
 *
 */
@Repository
public class UserDaoImpl implements UserDao {
	
	
	@Autowired
	private UserRepositoryCustom ur;
    
	/*@Autowired
    private VerificationTokenRepository tokenRepository;
	
    @Autowired
	private PasswordResetTokenRepository passwordTokenRepository;*/

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private MessageSource messageSource;
 
	public User findByUserName(String username) {	
		User user = ur.findByEmail(username);
		return user;
	}

    public User getUserByID(final String id) {
        return ur.findOne(id);
    }

    public boolean checkIfValidOldPassword(final User user, final String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

	public User registerNewUserAccount(User accountDto, Locale locale) throws EmailExistsException {
		 if (emailExist(accountDto.getUserName())) {
	            throw new EmailExistsException(messageSource.getMessage("user.accountWithEmail", null, locale) + accountDto.getUserName());
	            
		 }
		 	
		 	final User user = new User();

	        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
	        user.setUserName(accountDto.getUserName());
	        /*Set<UserRole> userRoleSet = new HashSet<UserRole>();
	        UserRole userRole = new UserRole();
			userRole.setRole("Cliente");
			userRole.setUser(user);
			userRoleSet.add(userRole);
			user.setUserRole(userRoleSet);*/
			//user.setLocaleString(accountDto.getLocale());
			user.setFirstName(accountDto.getFirstName());
			user.setLastName(accountDto.getLastName());
			user.setGender(accountDto.getGender());
			user.setEnabled(true);
			user.setCreated(new Date());
			user.setUpdated(new Date());
						
	        return ur.save(user);
	}
	
	private boolean emailExist(final String email) {
		final User user = ur.findByEmail(email);
	    if (user != null) {
	    	return true;
	    	
	    }
	    return false;
	    
	}
	/*
    public VerificationToken getVerificationToken(final String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }*/

    public void saveRegisteredUser(final User user) {
        ur.save(user);
    }

	@Override
	public User getUserByID(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void changeUserPassword(User user, String password) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createVerificationTokenForUser(User user, String token) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User getUser(String token) {
		// TODO Auto-generated method stub
		return null;
	}
    
  
/*    public void createVerificationTokenForUser(final User user, final String token) {
        final VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    public VerificationToken generateNewVerificationToken(final String existingVerificationToken) {
        VerificationToken vToken = tokenRepository.findByToken(existingVerificationToken);
        vToken.updateToken(UUID.randomUUID().toString());
        vToken = tokenRepository.save(vToken);
        return vToken;
    }
	
	public User getUser(final String verificationToken) {
        final User user = tokenRepository.findByToken(verificationToken).getUser();
        return user;
    }

	 public void changeUserPassword(final User user, final String password) {
	        user.setPassword(passwordEncoder.encode(password));
	        if(user.getCreateDate() == null)
	        user.setCreateDate(new Date());
			user.setLastUpdate(new Date());
	        ur.save(user);
	        
	 }
	 
	 public void createPasswordResetTokenForUser(final User user, final String token) {
			final PasswordResetToken myToken = new PasswordResetToken(token, user);
			passwordTokenRepository.save(myToken);
			
	 }
	  
	 public PasswordResetToken getPasswordResetToken(final String token) {
	        return passwordTokenRepository.findByToken(token);
	        
	 }
*/
	
}
