package mysample.boot.security.mapper.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import mysample.boot.security.domain.User;

@Mapper
public interface UserMapper {

	List<User> getUserAll();

	User getUserByLoginId(String loginId);
}
