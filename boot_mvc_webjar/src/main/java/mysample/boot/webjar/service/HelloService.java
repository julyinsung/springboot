package mysample.boot.webjar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mysample.boot.webjar.domain.User;
import mysample.boot.webjar.mapper.user.UserMapper;

@Service
public class HelloService {

	@Autowired UserMapper mapper;
	
	public List<User> getUserAll(){
		return mapper.getUserAll();
	}
	
}
