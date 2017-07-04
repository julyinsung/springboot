package mysample.boot.webjar.mapper.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import mysample.boot.webjar.domain.User;

@Mapper
public interface UserMapper {

	List<User> getUserAll();
}
