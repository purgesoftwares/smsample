package com.allowify;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.oauth2.common.ExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

/**
 * OAuth2 provider of tokens. Made for MongoDB
 */
final public class MongoTokenStore implements TokenStore {

    private static final String DATABASE = "new_app";
    private static final String OAUTH_ACCESS_TOKEN = "oauth_access_token";
    private static final String OAUTH_REFRESH_TOKEN = "oauth_refresh_token";
    private final ConcurrentHashMap<String, OAuth2AccessToken> accessTokenStore = new ConcurrentHashMap<String, OAuth2AccessToken>();
    private final ConcurrentHashMap<String, OAuth2Authentication> authenticationTokenStore = new ConcurrentHashMap<String, OAuth2Authentication>();
    private final ConcurrentHashMap<String, Long> expirationTokenStore = new ConcurrentHashMap<String, Long>();
    private long sliderExpiration = 30000; // Thirty seconds
    private String database;
    private MongoClient mongo;
    
    private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();

    /**
     * selectKeys
     * <p/>
     * returns all keys that belong to a principal
     *
     * @param username The identifier of the principal
     * @return The OAuth2AccessToken associated with this principal
     */
    public OAuth2AccessToken selectKeys(String username) {
        final BasicDBObject query = new BasicDBObject("name", username);
        final DBCollection collection = getCollection(OAUTH_ACCESS_TOKEN);
        DBObject document = collection.findOne(query);
        return (document == null)
                ? null
                : (OAuth2AccessToken) deserialize((byte[]) document.get("token"));
    }

    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        // insert into oauth_access_token (token_id, token, authentication_id, authentication, refresh_token) values (?, ?, ?, ?, ?)
        String refreshToken = null;
        if (token.getRefreshToken() != null) {
            refreshToken = token.getRefreshToken().getValue();
        }
        final String name = (authentication.getUserAuthentication() == null) ? null : authentication.getUserAuthentication().getName();
        final BasicDBObject document = new BasicDBObject();
        document.put("token_id", token.getValue());
        document.put("token", serialize(token));
        document.put("authentication_id", authenticationKeyGenerator.extractKey(authentication));
        document.put("authentication", serialize(authentication));        
        document.put("refresh_token", refreshToken);
        document.put("name", name);
        final DBCollection collection = getCollection(OAUTH_ACCESS_TOKEN);
        
        BasicDBObject storedUser = new BasicDBObject("name", document.getString("name"));
        DBCursor cursor = collection.find(storedUser);
       
        if(cursor.count() == 0){
        	collection.insert(document);
        }else{
        	System.out.println("User is already logged in.");
        }
    }

    public OAuth2AccessToken readAccessToken(String tokenValue) {

        OAuth2AccessToken accessToken = (isFresh(tokenValue))
                ? this.accessTokenStore.get(tokenValue)
                : null;
                
		if (accessToken == null) {
			// select token_id, token from oauth_access_token where token_id = ?
			final BasicDBObject query = new BasicDBObject("token_id",
					tokenValue);
			final DBCollection collection = getCollection(OAUTH_ACCESS_TOKEN);
			DBObject document = collection.findOne(query);
			
			if(document != null ) {							
				accessToken = deserialize((byte[]) document.get("token"));								
				this.accessTokenStore.put(tokenValue, accessToken);
				expiration(tokenValue);
			}
			
			
		}
        return accessToken;
    }

    public void removeAccessToken(String tokenValue) {

        final BasicDBObject query = new BasicDBObject("token_id", tokenValue);
        final DBCollection collection = getCollection(OAUTH_ACCESS_TOKEN);
        collection.remove(query);
        this.accessTokenStore.remove(tokenValue);
    }

    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {

        final String tokenValue = token.getValue();
        OAuth2Authentication authentication = (isFresh(tokenValue))
                ? this.authenticationTokenStore.get(tokenValue)
                : null;
        if (authentication == null) {
            // select token_id, authentication from oauth_access_token where token_id = ?
            final BasicDBObject query = new BasicDBObject();
            query.put("token_id", token.getValue());
            final DBCollection collection = getCollection(OAUTH_ACCESS_TOKEN);
            final DBObject document = collection.findOne(query);
            
            if(document != null){
                authentication = deserialize((byte[]) document.get("authentication"));
                this.authenticationTokenStore.put(tokenValue, authentication);
                expiration(tokenValue);
            }
        }
        return authentication;
    }

    public void storeRefreshToken(ExpiringOAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {

        // insert into oauth_refresh_token (token_id, token, authentication) values (?, ?, ?)
        final BasicDBObject document = new BasicDBObject();
        document.put("token_id", refreshToken.getValue());
        document.put("token", serialize(refreshToken));
        document.put("authentication", serialize(authentication));
        final DBCollection collection = getCollection(OAUTH_REFRESH_TOKEN);
        collection.insert(document);
    }

    public ExpiringOAuth2RefreshToken readRefreshToken(String token) {

        // select token_id, token from oauth_refresh_token where token_id = ?
        ExpiringOAuth2RefreshToken refreshToken = null;
        final BasicDBObject query = new BasicDBObject("token_id", token);
        final DBCollection collection = getCollection(OAUTH_REFRESH_TOKEN);
        final DBObject document = collection.findOne(query);
        if (document == null) {
        } else {
            refreshToken = deserialize((byte[]) document.get("token"));
        }
        return refreshToken;
    }

    public void removeRefreshToken(String token) {

        // remove from oauth_refresh_token where token_id = ?
        final BasicDBObject query = new BasicDBObject("token_id", token);
        final DBCollection collection = getCollection(OAUTH_REFRESH_TOKEN);
        collection.remove(query);
    }

    public OAuth2Authentication readAuthentication(ExpiringOAuth2RefreshToken token) {

        // select token_id, authentication from oauth_refresh_token where token_id = ?
        OAuth2Authentication authentication = null;
        final BasicDBObject query = new BasicDBObject("token_id", token.getValue());
        final DBCollection collection = getCollection(OAUTH_REFRESH_TOKEN);
        final DBObject document = collection.findOne(query);
        if (document == null) {
        } else {
            authentication = deserialize((byte[]) document.get("authentication"));
        }
        return authentication;
    }

    public void removeAccessTokenUsingRefreshToken(String refreshToken) {

        // remove from oauth_access_token where refresh_token = ?
        final BasicDBObject query = new BasicDBObject("refresh_token", refreshToken);
        final DBCollection collection = getCollection(OAUTH_ACCESS_TOKEN);
        collection.remove(query);
    }

    private void expiration(String tokenValue) {
        this.expirationTokenStore.put(tokenValue, new Date().getTime() + sliderExpiration);
    }

    public void updateAuthentication(OAuth2AccessToken token, OAuth2Authentication authentication ) {

        storeAccessToken(token, authentication);
    }

    /**
     * isFresh
     * <p/>
     * Determines if we can use the cache...
     *
     * @param tokenValue the token to look for
     * @return Fresh when the token is requested within the sliding expiration span
     */
    private boolean isFresh(String tokenValue) {

        long expiration = (expirationTokenStore.containsKey(tokenValue))
                ? expirationTokenStore.get(tokenValue)
                : 0;
        long time = new Date().getTime();
        if (expiration > time) {
            this.expirationTokenStore.put(tokenValue, time + sliderExpiration);
            return true;
        }
        expirationTokenStore.remove(tokenValue);
        accessTokenStore.remove(tokenValue);
        authenticationTokenStore.remove(tokenValue);
        return false;
    }

    public void setSliderExpiration(long sliderExpiration) {
        this.sliderExpiration = sliderExpiration;
    }

    public DBCollection getCollection(String collection) {
    	if(mongo == null){
    		try {
				mongo = new MongoClient();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
        final DB db = mongo.getDB(getDatabase());
        return db.getCollection(collection);
    }

    public String getDatabase() {
        if (database == null)
            database = DATABASE;
        return database;
    }

    public void setMongo(MongoClient mongo) {
        this.mongo = mongo;
    }

    public void setDatabase(String database) {
        this.database = database;
        final DBCollection c = getCollection(OAUTH_ACCESS_TOKEN);
        c.ensureIndex("token_id");
    }

    private static byte[] serialize(Object state) {
        ObjectOutputStream oos = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream(512);
            oos = new ObjectOutputStream(bos);
            oos.writeObject(state);
            oos.flush();
            return bos.toByteArray();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    // eat it
                	e.printStackTrace();
                }
            }
        }
    }

    private static <T> T deserialize(byte[] byteArray) {
        ObjectInputStream oip = null;
        try {
            oip = new ObjectInputStream(new ByteArrayInputStream(byteArray));
            return (T) oip.readObject();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        } finally {
            if (oip != null) {
                try {
                    oip.close();
                } catch (IOException e) {
                    // eat it
                }
            }
        }
    }

	@Override
	public OAuth2Authentication readAuthentication(String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeAccessToken(OAuth2AccessToken token) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void storeRefreshToken(OAuth2RefreshToken refreshToken,
			OAuth2Authentication authentication) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public OAuth2Authentication readAuthenticationForRefreshToken(
			OAuth2RefreshToken token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeRefreshToken(OAuth2RefreshToken token) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAccessTokenUsingRefreshToken(
			OAuth2RefreshToken refreshToken) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
		OAuth2AccessToken token = null;
		String accessToken = authenticationKeyGenerator.extractKey(authentication);
		final DBCollection collection = getCollection(OAUTH_ACCESS_TOKEN);
		
		BasicDBObject storedUser = new BasicDBObject("authentication_id", accessToken);
		DBObject cursor = collection.findOne(storedUser);
		
		if(cursor == null){
			token = null;
		}else{
			token = deserialize((byte[]) cursor.get("token"));
		}
		
		return token;
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(
			String clientId, String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
		// TODO Auto-generated method stub
		return null;
	}
}
