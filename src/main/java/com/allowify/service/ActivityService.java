package com.allowify.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.stereotype.Service;

import com.allowify.model.Code;
import com.allowify.model.MainAcitivity;
import com.allowify.model.StatusConfiguration;
import com.allowify.model.User;
import com.allowify.model.UserActivity;
import com.allowify.repository.CodeRepositoryCustom;
import com.allowify.repository.MainAcitivityCustomRepository;
import com.allowify.repository.UserRepositoryCustom;
import com.zippi.events.code.OnCreateCheckInEvent;
import com.zippi.events.code.OnCreateCodeEvent;
import com.zippi.events.code.OnCreateEventInterstedEvent;
import com.zippi.events.code.OnCreateEventOnCode;
import com.zippi.events.code.OnCreateFriendEvent;
import com.zippi.events.code.OnCreateLikeEvent;
import com.zippi.events.code.OnCreateReviewEvent;
import com.zippi.events.code.OnCreateShareEvent;
import com.zippi.events.code.OnUploadPhotoEvent;

@Service
public class ActivityService {
		
	@Autowired
    @Qualifier("mongoTemplate")
    public MongoOperations mongoOperations;
	
	@Autowired
	private ConcurrentTaskExecutor task;
	
	@Autowired
	private UserRepositoryCustom userRepositoryCustom;
	
	@Autowired
	private MainAcitivityCustomRepository repo;
	
	@Autowired
	private CodeRepositoryCustom codeRepositoryCustom;
	
	public ActivityService() { }
	
	public void requestUserAcivity(final Object obj) {
		
		if(obj instanceof OnCreateFriendEvent ) {
			task.execute(new Runnable() {
				@Override
				public void run() {
					createUserActivity((OnCreateFriendEvent) obj);
					
				}
			});
		} else if(obj instanceof OnUploadPhotoEvent) {
			task.execute(new Runnable() {
				@Override
				public void run() {
					createUserActivity((OnUploadPhotoEvent) obj);
					
				}
			});
		} else if(obj instanceof OnCreateReviewEvent) {
			task.execute(new Runnable() {
				@Override
				public void run() {
					createUserActivity((OnCreateReviewEvent) obj);
					
				}
			});
		} else if(obj instanceof OnCreateEventOnCode) {
			task.execute(new Runnable() {
				@Override
				public void run() {
					createUserActivity((OnCreateEventOnCode) obj);
					
				}
			});
		} else if(obj instanceof OnCreateCheckInEvent) {
			task.execute(new Runnable() {
				@Override
				public void run() {
					createUserActivity((OnCreateCheckInEvent) obj);
					
				}
			});
		} else if(obj instanceof OnCreateLikeEvent){
			task.execute(new Runnable() {
				@Override
				public void run() {
					createUserActivity((OnCreateLikeEvent) obj);
					
				}
			});
		} else if(obj instanceof OnCreateCodeEvent) {
			task.execute(new Runnable() {
				@Override
				public void run() {
					createUserActivity((OnCreateCodeEvent) obj);
					
				}
			});
		} else if(obj instanceof OnCreateEventInterstedEvent){
			task.execute(new Runnable() {
				@Override
				public void run() {
					createUserActivity((OnCreateEventInterstedEvent) obj);
					
				}
			});
		} else if(obj instanceof OnCreateShareEvent){
			task.execute(new Runnable() {
				@Override
				public void run() {
					createUserActivity((OnCreateShareEvent) obj);
					
				}
			});
		}
		
	}
	
    private void createUserActivity(OnCreateFriendEvent onCreateFollowEvent) {
    	final UserActivity userActivity = new UserActivity();
    
    	userActivity.setFriend(onCreateFollowEvent.getFriend());
		userActivity.setUserId(onCreateFollowEvent.getFriend().getUserId());
		
		User user = userRepositoryCustom.findOne(onCreateFollowEvent.getFriend().getAnotherUserId());
		userActivity.setTitle(onCreateFollowEvent.getUser().getFirstName()+ " "
				+ onCreateFollowEvent.getUser().getLastName() + " is Following " +
				user.getFirstName() +" "+ user.getLastName() + " on Zippi");
		userActivity.setUser(onCreateFollowEvent.getUser());
        userActivity.setDescription("Followed on zippi ");
        userActivity.setCreateDate(new Date());
        userActivity.setUpdateDate(new Date());
        
        mongoOperations.save(userActivity);
  	}
    
	
    private void createUserActivity(OnUploadPhotoEvent onUploadPhotoEvent) {
		final UserActivity userActivity = new UserActivity();
		
		userActivity.setUploadedFiles(onUploadPhotoEvent.getUploadedFile());
		User user = userRepositoryCustom.findOne(onUploadPhotoEvent.getCodeEvent().getUserId());
		userActivity.setTitle(user.getFirstName()+" "+ user.getLastName() +" uploaded a photo of "
				+ onUploadPhotoEvent.getCodeEvent().getDescription());
        userActivity.setDescription("photo uploaded ");
        userActivity.setCreateDate(new Date());
        userActivity.setUpdateDate(new Date());
        
        mongoOperations.save(userActivity);
        
	}
    

	private void createUserActivity(OnCreateReviewEvent onCreateReviewEvent) {
		final UserActivity userActivity = new UserActivity();
	
		userActivity.setReview(onCreateReviewEvent.getReview());
		userActivity.setUserId(onCreateReviewEvent.getReview().getUserId());
		userActivity.setTitle(onCreateReviewEvent.getUser().getFirstName() +" "
				+ onCreateReviewEvent.getUser().getLastName() +" reviewed a "
				+ onCreateReviewEvent.getReview().getCode());
        userActivity.setDescription("Review on " + onCreateReviewEvent.getReview().getDescription());
        userActivity.setUser(onCreateReviewEvent.getUser());
        userActivity.setCreateDate(new Date());
        userActivity.setUpdateDate(new Date());
        
        mongoOperations.save(userActivity);
   	
		
	}
	

	private void createUserActivity(OnCreateEventOnCode onCreateEventOnCode) {
		
		final UserActivity userActivity = new UserActivity();
		MainAcitivity mainActivity = new MainAcitivity();
	    User user = userRepositoryCustom.findOne(onCreateEventOnCode.getCodeEvent().getUserId());
		
	    userActivity.setCodeEvent(onCreateEventOnCode.getCodeEvent());
		userActivity.setUserId(onCreateEventOnCode.getCodeEvent().getUserId());
		userActivity.setTitle(onCreateEventOnCode.getUser().getFirstName() +" " 
				+ onCreateEventOnCode.getUser().getLastName() 
				+ " created an  Event " );
		userActivity.setUser(onCreateEventOnCode.getUser());
        userActivity.setDescription("Event on " + onCreateEventOnCode.getCodeEvent().getDescription());
        userActivity.setCreateDate(new Date());
        userActivity.setUpdateDate(new Date());
        userActivity.setType(StatusConfiguration.ACTIVITY_TYPE_EVENT_CREATED);
       
        List<Code> code = codeRepositoryCustom.findByCode(onCreateEventOnCode.getCodeEvent().getCode());
       
        mainActivity.setCodeEvent(onCreateEventOnCode.getCodeEvent());
        mainActivity.setLatLong(code.get(0).getLatLong());
        mainActivity.setIsPrivate(code.get(0).getIsPrivate());
        mainActivity.setCreateDate(onCreateEventOnCode.getCodeEvent().getCreateDate());
        mainActivity.setUser(user);
        mainActivity.setCode(code.get(0));
        mainActivity.setType(StatusConfiguration.ACTIVITY_TYPE_EVENT_CREATED);
        
        mongoOperations.save(mainActivity);
        mongoOperations.save(userActivity);
     		
	}
	

	private void createUserActivity(OnCreateCheckInEvent onCreateCheckInEvent) {
		
		final UserActivity userActivity = new UserActivity();
	
		userActivity.setCheckIn(onCreateCheckInEvent.getCheckIn());
		userActivity.setUserId(onCreateCheckInEvent.getCheckIn().getUserId());
		userActivity.setUser(onCreateCheckInEvent.getUser());
		userActivity.setCode(onCreateCheckInEvent.getCode());
		userActivity.setCodeEvent(onCreateCheckInEvent.getCodeEvent());
		if(onCreateCheckInEvent.getCode() != null) {
			userActivity.setTitle(onCreateCheckInEvent.getUser().getFirstName() +" "
					+ onCreateCheckInEvent.getUser().getLastName() 
					+ " has checked in at "
					+ onCreateCheckInEvent.getCode().getCode());
		} else {
			userActivity.setTitle(onCreateCheckInEvent.getUser().getFirstName() +" "
					+ onCreateCheckInEvent.getUser().getLastName()  +" has checked in at Event");
		}
	
        userActivity.setDescription("check in");
        userActivity.setCreateDate(new Date());
        userActivity.setUpdateDate(new Date());
        
        mongoOperations.save(userActivity);

		
	}
	
	
	private void createUserActivity(OnCreateLikeEvent createLikeEvent) {
		
		final UserActivity userActivity = new UserActivity();

		userActivity.setLike(createLikeEvent.getLike());
		userActivity.setUserId(createLikeEvent.getLike().getUserId());
		userActivity.setTitle(createLikeEvent.getUser().getFirstName() +" "
				+ createLikeEvent.getUser().getLastName()+" liked " );
        userActivity.setDescription("Liked ");
        userActivity.setUser(createLikeEvent.getUser());
        userActivity.setCodeEvent(createLikeEvent.getCodeEvent());
        userActivity.setCode(createLikeEvent.getCode());
        userActivity.setCreateDate(new Date());
        userActivity.setUpdateDate(new Date());
        
        mongoOperations.save(userActivity);
        
	}
	

	private void createUserActivity(OnCreateCodeEvent event) {
    	
        final UserActivity userActivity = new UserActivity();
        MainAcitivity mainAcitivity = new MainAcitivity();
        User user = userRepositoryCustom.findOne(event.getCode().getUserId());
        userActivity.setCode(event.getCode());
        userActivity.setUser(event.getUser());
        userActivity.setUserId(event.getUser().getId());
        userActivity.setTitle(event.getUser().getFirstName() 
            		+ " Created a new Code " 
            		+ event.getCode().getCode());
            userActivity.setDescription("For Location " 
            		+ event.getCode().getAddress1()
            		+ ", " + event.getCode().getAddress2() + ", "
            		+ event.getCode().getZipCode() + ", " 
            		+ event.getCode().getCity() + ", "
            		+ event.getCode().getState() + ", "
            		+ event.getCode().getCountry());
        
        userActivity.setCreateDate(new Date());
        
        userActivity.setUpdateDate(new Date());
        userActivity.setType(StatusConfiguration.ACTIVITY_TYPE_CODE_CREATED);
        
        mainAcitivity.setCode(event.getCode());
        mainAcitivity.setLatLong(event.getCode().getLatLong());
        mainAcitivity.setIsPrivate(event.getCode().getIsPrivate());
        mainAcitivity.setCreateDate(event.getCode().getCreateDate());
        mainAcitivity.setUser(user);
        mainAcitivity.setType(StatusConfiguration.ACTIVITY_TYPE_CODE_CREATED);
        
        repo.save(mainAcitivity);
        mongoOperations.save(userActivity);
    
    }
	
	private void createUserActivity(final OnCreateEventInterstedEvent onCreateEventInterstedEvent) {
		final UserActivity userActivity = new UserActivity();
		userActivity.setEventInterested(onCreateEventInterstedEvent.getEventInterested());
		userActivity.setUser(onCreateEventInterstedEvent.getUser());
		userActivity.setUserId(onCreateEventInterstedEvent.getEventInterested().getUserId());
		userActivity.setCodeEvent(onCreateEventInterstedEvent.getCodeEvent());
		userActivity.setTitle(onCreateEventInterstedEvent.getUser().getFirstName() +" " 
				+ onCreateEventInterstedEvent.getUser().getLastName()
				+" is going to ");
        userActivity.setDescription("interseted in event ");
        userActivity.setCreateDate(new Date());
        userActivity.setUpdateDate(new Date());
        
        mongoOperations.save(userActivity);
		
	}
	
	private void createUserActivity(final OnCreateShareEvent shareEvent) {
		final UserActivity userActivity = new UserActivity();
		
		userActivity.setUser(shareEvent.getUser());
		userActivity.setUserId(shareEvent.getUser().getId());
		userActivity.setDescription("Creating Share Activity by user");
		userActivity.setCreateDate(new Date());
		userActivity.setUpdateDate(new Date());
		if(shareEvent.getCode() != null) {
			userActivity.setCode(shareEvent.getCode());
			userActivity.setTitle(shareEvent.getUser().getFirstName() + " " 
								+ shareEvent.getUser().getLastName() + " Shared Code "
								+ shareEvent.getCode().getCode());
		
		} else if(shareEvent.getCodeEvent() != null) {
			userActivity.setCodeEvent(shareEvent.getCodeEvent());
			userActivity.setTitle(shareEvent.getUser().getFirstName() + " "
								+ shareEvent.getUser().getLastName() + " Shared Event "
								+ shareEvent.getCodeEvent().getDescription());
			
		} else if(shareEvent.getUserActivity() != null) {
			userActivity.setUserActivity(shareEvent.getUserActivity());
			userActivity.setTitle(shareEvent.getUser().getFirstName() + " "
								+ shareEvent.getUser().getLastName() + " Shared Activity ");
		}
	
        
        mongoOperations.save(userActivity);
		
	}

}
