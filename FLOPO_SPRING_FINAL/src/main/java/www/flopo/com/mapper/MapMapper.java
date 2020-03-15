package www.flopo.com.mapper;

import java.util.List;

import www.flopo.com.domain.AreaInfoVO;
import www.flopo.com.domain.ClickedPoint;
import www.flopo.com.domain.CurrentScreenInfo;
import www.flopo.com.domain.MapVO;

public interface MapMapper {
	public List<MapVO> getList();
	public MapVO read(int id);
	public List<MapVO> getCurrentScreenAreas(CurrentScreenInfo currentScreenInfo);
	public AreaInfoVO getClickedAreaInfo(ClickedPoint clickedPoint);
	public int getClickedAreaMale(ClickedPoint clickedPoint);
	public int getClickedAreaFemale(ClickedPoint clickedPoint);
	public int getClickedAreaId(ClickedPoint clickedPoint);

}
