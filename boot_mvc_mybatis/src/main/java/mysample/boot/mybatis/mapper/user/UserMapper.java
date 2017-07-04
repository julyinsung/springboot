package mysample.boot.mybatis.mapper.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import mysample.boot.mybatis.domain.User;

@Mapper
public interface UserMapper {

	List<User> getUserAll();
}
