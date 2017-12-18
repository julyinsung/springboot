package mysample.boot.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import mysample.boot.security.domain.User;
import mysample.boot.security.domain.common.CommonListVO;
import mysample.boot.security.mapper.user.UserMapper;
import mysample.boot.security.util.CommonUtil;

@Service
public class HelloService {

	@Autowired
	UserMapper mapper;

	@Autowired
	PasswordEncoder passwordEncoder;

	public CommonListVO getUserAll(User user) {
		// Count 조회
		CommonListVO oListVO = mapper.getUserTotalCount();
		if (oListVO.getTotalCount() > 0) {
			// 페이지 설정
			int iNowPage = Integer.parseInt(user.getNowPage());
			oListVO = CommonUtil.setPaging(oListVO, iNowPage, user.getCountPerPage());

			// 실제 데이터 조회
			List<User> dataList = mapper.getUserList(user, oListVO.getStartNum(),
					oListVO.getCountPerPage());
			oListVO.setLists(dataList);
		}
		return oListVO;
	}

	public void addUser(User user) {
		// encoding password
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		mapper.insertUser(user);
	}

	public void editUser(User user) {
		mapper.updateUser(user);
	}

}
