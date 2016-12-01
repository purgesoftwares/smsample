package com.allowify;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchProviderException;

import javax.annotation.Resource;
import javax.security.auth.login.LoginException;

import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.util.StringUtils;

import com.allowify.model.User;
import com.allowify.repository.UserRepositoryCustom;


public class CustomEnocdePassword implements PasswordEncoder {

    @Resource(name="user")
    private UserRepositoryCustom userService;

    @Override
    public String encodePassword(String password, Object username)
            throws DataAccessException {
        if (StringUtils.isEmpty(password)) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        if(username.equals(null)){
            throw new IllegalArgumentException("Username cannot be null");
        }

        String saltPassword = null;
		try {
			saltPassword = getSaltPassword(username);
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


        String enteredPassword = null;
		try {
			enteredPassword = getEnteredPassword(password, saltPassword);
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return enteredPassword;
    }


    @Override
    public boolean isPasswordValid(String encPassword, String password, Object username)
            throws DataAccessException {
        if (StringUtils.isEmpty(password)) {
            return false;
        }

        String saltPassword = null;
        String databasePassword = null;

        User domainUser = userService.findByUserName(username.toString());
		 saltPassword = domainUser.getPassword();
		 databasePassword = domainUser.getPassword();


        String enteredPassword = null;
		try {
			enteredPassword = getEnteredPassword(password, saltPassword);
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


         return databasePassword.equals(enteredPassword);
        //return StringUtils.equals(databasePassword, password);
    }

    /**
     * @param password
     * @param saltPassword
     * @return
     */
    public static String getEnteredPassword(String password, String saltPassword) throws NoSuchProviderException, UnsupportedEncodingException {
        String enteredPassword = null;

            enteredPassword = password + saltPassword;
        return enteredPassword;
    }

    /**
     * @param username
     * @return
     */
    private String getSaltPassword(Object username) throws LoginException {
        String saltPassword = null;
        User domainUser = userService.findByUserName(username.toString());
		 saltPassword = domainUser.getPassword();
        return saltPassword;
    }

}
