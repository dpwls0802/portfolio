package www.flopo.com.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import www.flopo.com.domain.AreaInfoVO;
import www.flopo.com.domain.ClickedPoint;
import www.flopo.com.domain.CurrentScreenInfo;
import www.flopo.com.domain.MapVO;
import www.flopo.com.mapper.MapMapper;

@Log4j
@Service
@AllArgsConstructor
public class MapServiceImpl implements MapService{
	private MapMapper mapper;

	@Override
	public List<MapVO> getList() {
		log.info("getList....");
		return mapper.getList();
	}

	@Override
	public MapVO get(int id) {
		log.info("get......."+id);
		return mapper.read(id);
	}
	
	@Override
	public List<MapVO> getCurrentScreenAreas(CurrentScreenInfo currentScreenInfo) {
		log.info("get current screen areas");
		return mapper.getCurrentScreenAreas(currentScreenInfo);
		
	}
	@Override
	public AreaInfoVO getClickedAreaInfo(ClickedPoint clickedPoint) {
		log.info("get clicked area info");
		AreaInfoVO areainfo = new AreaInfoVO();
		areainfo.setMale(mapper.getClickedAreaMale(clickedPoint));
		areainfo.setFemale(mapper.getClickedAreaFemale(clickedPoint));
		areainfo.setId(mapper.getClickedAreaId(clickedPoint));

		return areainfo;
	}

	
	
}
