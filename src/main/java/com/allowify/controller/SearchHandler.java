package com.allowify.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.allowify.config.MongoDBConfig;
import com.allowify.model.Code;
import com.allowify.model.CodeAccessActivity;
import com.allowify.repository.CodeRepositoryCustom;
import com.allowify.repository.CodeAccessActivityRepositoryCustom;




@Controller
@RequestMapping("/search")
public class SearchHandler {
	
/*	@Autowired
	private CodeRepositoryCustom codeRepositoryCustom;
	
	@Autowired
	private Code_Access_ActivityRepositoryCustom codeAccessRepository;
	
	@RequestMapping(value="/searching-code", method=RequestMethod.POST)
	public @ResponseBody String searchingCode(@RequestBody String code, HttpServletRequest request) {
		
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoDBConfig.class);
		MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
		
		if(code != null) {
			
			//mongoOperation.save(code);
			List<Code> listCode = codeRepositoryCustom.findByCode(code);
				Code code1 = listCode.get(0);		
			listCode.get(0).setTotalReach(listCode.get(listCode.size()-1).getTotalReach()+1);
			mongoOperation.save(code);
			
			Code_Access_Activity codeAccessActivity =  new Code_Access_Activity();
			codeAccessActivity.setCode(code1);
			codeAccessActivity.setUser_id(code1.getUserId());
			codeAccessActivity.setIp(request.getRemoteAddr().toString());
			mongoOperation.save(codeAccessActivity);
		
		}
		
		return null;
	}*/
	
	/*@Override
	public @ResponseBody List<Code> findByCode(@Param("code") String code) {
		
		
		if(code != null) {
			
			List<Code> listCode = this.findByCode(code);
			System.out.println(listCode.size());
			Code code1 = listCode.get(0);	
			System.out.println(listCode.get(0));
			code1.setTotalReach(listCode.get(listCode.size()-1).getTotalReach()+1);
			
			Code_Access_Activity codeAccessActivity =  new Code_Access_Activity();
			codeAccessActivity.setCode(code1);
			codeAccessActivity.setUser_id(code1.getUserId());
			
		}
		return null;
	}

	@Override
	public List<Code> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Code> findAll(Sort arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Code> List<S> save(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Code> findAll(Pageable arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Code arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Iterable<? extends Code> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Code> findAll(Iterable<String> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Code findOne(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Code> S save(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Code findByStatus(int status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Code findByUserId(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Code> findByLatLongNear(Point point, Distance range) {
		// TODO Auto-generated method stub
		return null;
	}*/

}
