package mysample.boot.mybatis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mysample.boot.mybatis.domain.User;
import mysample.boot.mybatis.mapper.user.UserMapper;

@Service
public class HelloService {

	@Autowired UserMapper mapper;
	
	public List<User> getUserAll(){
		return mapper.getUserAll();
	}
	
}
