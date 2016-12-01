package com.allowify.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.allowify.model.PromoCode;
import com.allowify.model.StatusConfiguration;
import com.allowify.model.UsedPromos;
import com.allowify.model.User;
import com.allowify.repository.PromoCodeRepositoryCustom;
import com.allowify.repository.UsedPromosRepositoryCustom;
import com.allowify.repository.UserRepositoryCustom;
import com.allowify.response.PromoCodeResponse;

@Controller
@RequestMapping("/promo")
public class PromoCodeController {
	
	@Autowired
	private PromoCodeRepositoryCustom promoCodeRepositoryCustom;
	
	@Autowired
	private UsedPromosRepositoryCustom usedPromosRepositoryCustom;
	
	@Autowired
	private UserRepositoryCustom userRepoCustom;
	
	@ResponseStatus(value = HttpStatus.CREATED)
	@RequestMapping(method=RequestMethod.POST)
	public @ResponseBody PromoCode createPromo(@RequestBody PromoCode promoCode) {
		PromoCode prePromoCode = promoCodeRepositoryCustom.findByPromoCode(promoCode.getPromoCode());
		
		if(prePromoCode == null) {
			promoCode.setPromoCode(promoCode.getPromoCode());
			promoCode.setDiscountType(promoCode.getDiscountType());//1- %(percentage) 2-flat
			if(promoCode.getDiscountType() == 1) {
				promoCode.setDiscount(promoCode.getDiscount() + StatusConfiguration.DISCOUNT_TYPE_PERCENTAGE);
			} else {
				promoCode.setDiscount(promoCode.getDiscount() + StatusConfiguration.DISCOUNT_TYPE_FLAT);
			}
			
			promoCode.setUseLimitType(promoCode.getUseLimitType());//1.count per user 2.total counts 3.unlimited
			promoCode.setUserLimit(promoCode.getUserLimit());
			promoCode.setValidFrom(promoCode.getValidFrom());
			promoCode.setValidTo(promoCode.getValidTo());
			promoCode.setStatus(1);
			promoCode.setCreated(new Date());
			promoCode.setUpdated(new Date());
			
			return promoCodeRepositoryCustom.save(promoCode);
		}
				
		return null;
	}
	
	/*
	 * User Request for promo codes 
	 */
	@RequestMapping(value="promo-used", method=RequestMethod.POST)
	public @ResponseBody PromoCodeResponse usedPromo(@RequestBody UsedPromos usedPromos) {
		PromoCode prePromoCode = promoCodeRepositoryCustom.findByPromoCode(usedPromos.getPromoCode());
		User user = userRepoCustom.findOne(usedPromos.getUserId());
				
		if(user != null  && prePromoCode != null && (prePromoCode.getStatus() == 1)
				&& (new Date().equals(prePromoCode.getValidFrom()) ||
						new Date().before(prePromoCode.getValidTo()) || 
								new Date().equals(prePromoCode.getValidTo()))) {
			
			if(prePromoCode.getUserLimit() >= prePromoCode.getUsedCounts()) {					
				if(prePromoCode.getUseLimitType() == 1){
					List<UsedPromos> listOfpromos = usedPromosRepositoryCustom.findByUserIdAndPromoCode(
							user.getId(), usedPromos.getPromoCode());
					
					if(listOfpromos.size() == prePromoCode.getUserLimit()) {
						prePromoCode.setUsedCounts(0);
						promoCodeRepositoryCustom.save(prePromoCode);
					}
					if(listOfpromos.size() < prePromoCode.getUserLimit()){	
						prePromoCode.setUsedCounts(prePromoCode.getUsedCounts() + 1);
						usedPromos.setCreated(new Date());
						usedPromosRepositoryCustom.save(usedPromos);
						promoCodeRepositoryCustom.save(prePromoCode);
						PromoCodeResponse response = new PromoCodeResponse("Successful", 
								prePromoCode.getValidFrom(),
								prePromoCode.getValidTo());
						
						return response;
					}
					
				} else if(prePromoCode.getUseLimitType() == 2){
					prePromoCode.setUsedCounts(prePromoCode.getUsedCounts() + 1);
					usedPromos.setCreated(new Date());
					usedPromosRepositoryCustom.save(usedPromos);
					if(prePromoCode.getUserLimit() == prePromoCode.getUsedCounts()){
						prePromoCode.setStatus(0);
					}
					promoCodeRepositoryCustom.save(prePromoCode);
					PromoCodeResponse response = new PromoCodeResponse("Successful", 
							prePromoCode.getValidFrom(),
							prePromoCode.getValidTo());
					return response;
					
					
				}  else {
					prePromoCode.setUsedCounts(prePromoCode.getUsedCounts() + 1);
					usedPromos.setCreated(new Date());
					usedPromosRepositoryCustom.save(usedPromos);
					promoCodeRepositoryCustom.save(prePromoCode);
					
					if(prePromoCode.getUserLimit() == prePromoCode.getUsedCounts()){
						prePromoCode.setStatus(0);
					}
					PromoCodeResponse response = new PromoCodeResponse("Successful", 
							prePromoCode.getValidFrom(),
							prePromoCode.getValidTo());
					return response;
					
				}
				
			} else if(prePromoCode.getUseLimitType() == 3) {
				prePromoCode.setUsedCounts(prePromoCode.getUsedCounts() + 1);
				usedPromos.setCreated(new Date());
				usedPromosRepositoryCustom.save(usedPromos);
				promoCodeRepositoryCustom.save(prePromoCode);
				PromoCodeResponse response = new PromoCodeResponse("Successful", 
						prePromoCode.getValidFrom(),
						prePromoCode.getValidTo());
				return response;
			}
			else {
				return new PromoCodeResponse("Limit is Over");
			}
		
		} else if(!(prePromoCode != null) && user != null && !(new Date().equals(prePromoCode.getValidFrom()) ||
						new Date().before(prePromoCode.getValidTo()) || 
								new Date().equals(prePromoCode.getValidTo()))) {
			return new PromoCodeResponse("This promo code date is outdated..");
		
		} else if(user == null) {
			return new PromoCodeResponse("User is not exists.");
		
		} else if(prePromoCode == null) {
			return new PromoCodeResponse("Promo code is not exists");
		}
		
		return new PromoCodeResponse("Unsuccessful");
	}
	
	/*
	 * For Checking promo code is valid or not
	 */
	@RequestMapping(value="/is-valid", method=RequestMethod.POST)
	public @ResponseBody PromoCodeResponse isValid(@RequestBody UsedPromos usedPromos) {
		PromoCode prePromoCode = promoCodeRepositoryCustom.findByPromoCode(usedPromos.getPromoCode());
		User preUser = userRepoCustom.findOne(usedPromos.getUserId());
			
		if(prePromoCode != null && (new Date().equals(prePromoCode.getValidFrom()) ||
						new Date().before(prePromoCode.getValidTo()) || 
								new Date().equals(prePromoCode.getValidTo()))) {
			if(prePromoCode.getUseLimitType() == 1) {
				List<UsedPromos> listOfpromos = usedPromosRepositoryCustom.findByUserIdAndPromoCode(
						preUser.getId(), usedPromos.getPromoCode());
				if(listOfpromos.size() < prePromoCode.getUserLimit()) {
					return new PromoCodeResponse("Valid", prePromoCode.getValidFrom(), prePromoCode.getValidTo());
				}   
			}
			else if(prePromoCode.getUseLimitType() == 2 && prePromoCode.getUserLimit() > prePromoCode.getUsedCounts()) {
				return new PromoCodeResponse("Valid", prePromoCode.getValidFrom(), prePromoCode.getValidTo());
			} else if(prePromoCode.getUseLimitType() == 3){
				return new PromoCodeResponse("Valid", prePromoCode.getValidFrom(), prePromoCode.getValidTo());
			}	
			
		}
		
		return new PromoCodeResponse("Invalid");
	}

}
