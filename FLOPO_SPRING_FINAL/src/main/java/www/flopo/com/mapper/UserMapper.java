package www.flopo.com.mapper;

import java.util.List;

import www.flopo.com.domain.UserVO;

public interface UserMapper {
	
	public List<UserVO> getTimeList();
	public UserVO read (int bno);

	public int insert (UserVO userVO);
	public int update (UserVO userVO);
	public int delete (int bno);
}
