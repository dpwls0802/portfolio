package www.flopo.com.mapper;

import java.util.List;

import www.flopo.com.domain.UserTraceVO;

public interface UserTraceMapper {
	
	public List<UserTraceVO> getTimeList();
	public UserTraceVO read (int bno);

	public int insert1(UserTraceVO userTraceVO);
	public int insert3(UserTraceVO userTraceVO);
	public int insert5(UserTraceVO userTraceVO);
	public void updateTime(UserTraceVO userTraceVO);

	public int update (UserTraceVO userTraceVO);
	public int delete (int bno);
}
