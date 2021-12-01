package www.flopo.com.service;

import java.util.List;

import www.flopo.com.domain.AreaInfoVO;
import www.flopo.com.domain.ClickedPoint;
import www.flopo.com.domain.CurrentScreenInfo;
import www.flopo.com.domain.MapVO;

public interface MapService {
	public MapVO get(int id);
	public List<MapVO> getList();
	public List<MapVO> getCurrentScreenAreas(CurrentScreenInfo currentScreenInfo);
	public AreaInfoVO getClickedAreaInfo(ClickedPoint clickedPoint);
	
}
