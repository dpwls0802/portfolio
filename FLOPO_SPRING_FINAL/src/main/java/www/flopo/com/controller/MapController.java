package www.flopo.com.controller;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import www.flopo.com.domain.AreaInfoVO;
import www.flopo.com.domain.ClickedPoint;
import www.flopo.com.domain.CurrentScreenInfo;
import www.flopo.com.domain.MapVO;
import www.flopo.com.domain.UserTraceVO;
import www.flopo.com.domain.UserVO;
import www.flopo.com.mapper.UserMapper;
import www.flopo.com.mapper.UserTraceMapper;
import www.flopo.com.service.MapService;

@Controller
@RequestMapping("/map/*")
@Log4j
@AllArgsConstructor
//http://localhost:8080/map/mapview
public class MapController {

	private MapService service;

	//지도를 띄어준다
	@GetMapping("/mapview")
	public String mapView(Model model) {
		log.info("map view...");
		return "/map/mapMoveEvent";
	}

	//사용자디바이스에 보여야하는 구역 구하기
	@RequestMapping(value = "/updateCurrentAreas", method = RequestMethod.POST)
	@ResponseBody
	public List<MapVO> updateCurrentArea(Model model, CurrentScreenInfo currentScreenInfo) {
		log.info("updateCurrentAreas");
		List<MapVO> currentScreenAreas = service.getCurrentScreenAreas(currentScreenInfo);
		log.info("사용자화면에 출력할 구역: " + currentScreenAreas);
		model.addAttribute("currentScreenAreas", currentScreenAreas);
		return currentScreenAreas;
	}

	//사용자가 클릭한 구역 구하기
	@RequestMapping(value = "/getClickedAreaInfo", method = RequestMethod.POST)
	@ResponseBody
	public AreaInfoVO getClickedAreaInfo(Model model, ClickedPoint clickedPoint) {
		log.info("getClickedAreaInfo");
		AreaInfoVO clickedAreaInfo;
		clickedAreaInfo = service.getClickedAreaInfo(clickedPoint);
		log.info("클릭한 구역: " + clickedPoint);
		model.addAttribute("clickedAreaInfo", clickedAreaInfo);
		return clickedAreaInfo;
	}
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserTraceMapper userTraceMapper;
	
	//안드로이드에서 로그인 시, 사용자 정보를 가져와 DB에 저장
	@RequestMapping("/android/login")
	@ResponseBody
	public void androidLoginWithRequestAndResponse(HttpServletRequest request) throws ParseException {	//안드로이드가 보낸 데이터를 request로받는다
		String name = request.getParameter("name");		
		String ageRange = request.getParameter("ageRange");
		String gender = request.getParameter("gender");
		String email = request.getParameter("email");

		UserVO userVO = new UserVO(); //유저 객체 만들기
		userVO.setName(name);
		userVO.setAgeRange(ageRange);
		userVO.setGender(gender);
		userVO.setEmail(email);

		System.out.println("이름: " + name);		
		System.out.println("연령대" + ageRange);
		System.out.println("성별: " + gender);
		System.out.println("주소: " + email);
		userMapper.insert(userVO); //받아온 정보를 DB에 저장				
	}
	
	//유저가 설정한 시간 저장
	@RequestMapping("/android/selectDate")
	@ResponseBody
	public void androidSelectDateWithRequestAndResponse(HttpServletRequest request) throws ParseException {	
		String settingTimeString = request.getParameter("setTime");
		String email = request.getParameter("email");

		UserTraceVO userTraceVO = new UserTraceVO();
		userTraceVO.setEmail(email);
		userTraceVO.setSettingTime(settingTimeString);

		System.out.println("이메일: " + email);
		System.out.println("설정시간: " + settingTimeString);
		userTraceMapper.updateTime(userTraceVO);			
	}
	
	//10분에 1번씩 유저 이메일과 위치정보 기록
	@RequestMapping("/android/updateUserLoc")
	@ResponseBody
	public void androidUpdateUserLocDateWithRequestAndResponse(HttpServletRequest request) throws ParseException {	
		String currentTime = request.getParameter("currentTime");
		String latitude = request.getParameter("latitude");
		String longitude = request.getParameter("longitude");
		String email = request.getParameter("email");

		UserTraceVO userTraceVO = new UserTraceVO();
		userTraceVO.setEmail(email);
		userTraceVO.setSettingTime(currentTime);
		userTraceVO.setLatitude(latitude);
		userTraceVO.setLongitude(longitude);

		System.out.println("latitude: " + latitude);		
		System.out.println("longitude: " + longitude);
		System.out.println("현재시간: " + currentTime);
		userTraceMapper.insert1(userTraceVO);
		userTraceMapper.insert3(userTraceVO);
		userTraceMapper.insert5(userTraceVO);		
	}
}
