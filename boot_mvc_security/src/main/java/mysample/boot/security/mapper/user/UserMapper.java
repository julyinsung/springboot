package mysample.boot.security.mapper.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import mysample.boot.security.domain.User;
import mysample.boot.security.domain.common.CommonListVO;

@Mapper
public interface UserMapper {

	public CommonListVO getUserTotalCount();
	
	public User getUserByLoginId(String loginId);
	
	public void insertUser(User user);
	
	public void updateUser(User user);  

	public List<User> getUserList(@Param(value="vo") User user, 
			@Param(value="startNum") int startNum, @Param(value="countPerPage") int countPerPage);
}
