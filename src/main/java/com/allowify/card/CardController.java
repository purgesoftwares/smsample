package com.allowify.card;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.allowify.model.Card;
import com.allowify.model.User;
import com.allowify.repository.CardRepositoryCustom;
import com.allowify.repository.UserRepositoryCustom;
import com.allowify.response.CardResponse;

/**
 * 
 * @author tonyacunar
 *
 */
@Controller
@RequestMapping("/cards")
public class CardController {

	private final static Logger logger = Logger.getLogger(CardController.class);
	
	private final UserRepositoryCustom userRepository;
	private final CardRepositoryCustom cardRepository;
	
	@Autowired
	public CardController(UserRepositoryCustom userRepository,
			CardRepositoryCustom cardRepository) {
		this.userRepository = userRepository;
		this.cardRepository = cardRepository;
	}
	
	@RequestMapping("/addCard")
	public @ResponseBody Card addCard(@RequestBody Card card) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		User user = userRepository.findByUserName(username);
		logger.info("Adding card " +card + "  for " + user);
		if(user != null) {
			card = cardRepository.save(card);
			
			System.out.println(card);
			
			//user.addCard(card);
			userRepository.save(user);
			
		}
		return card;
	}
	
	@RequestMapping("/deleteCard")
	public @ResponseBody Card deleteCard(@RequestParam("ID") String id) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		logger.info("Deleting card for " + id);
		
		User user = userRepository.findByUserName(username);
		Card card = null;
		if(user != null) {
			
			card = cardRepository.findOne(id);
			cardRepository.delete(id);

			//user.removeCard(id);
			userRepository.save(user);
		}
		
		
		return card;
	}
	
	@RequestMapping("/getCards")
	public @ResponseBody CardResponse getCards() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		User user = userRepository.findByUserName(username);
		if(user != null) {
			//return new CardResponse(user.getCards());
		}				
		return new CardResponse(new HashSet<Card>());		
	}
	
}
