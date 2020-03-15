package www.spring.com.sample.rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import www.spring.com.sample.rest.model.SampleVO;

@RestController
@RequestMapping("/sample")
public class SampleController {
	@GetMapping(value="/getText", produces="text/plain;charset=utf-8")
	public String getText() {
		return "안녕하세요";
	}

	@GetMapping(value="/getSample"/* , produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE} */)
	public SampleVO getSample() {
		SampleVO obj = new SampleVO(5, "길동", "홍");
		return obj;
	}

	@GetMapping(value="/getSampleList")
	public List<SampleVO> getSampleList() {
		List<SampleVO> ret = new ArrayList<>();
		ret.add(new SampleVO(5, "길동", "홍"));
		ret.add(new SampleVO(6, "길동", "김"));
		ret.add(new SampleVO(7, "길동", "이"));
		return ret;
	}

	@GetMapping(value="/getList")
	public List<SampleVO> getList() {
		//0~9라는 값을 각각만들고 이를 활용하여 객체 생성한다. 이후에 리스로 만든다.
		return IntStream.range(0, 10)
				.mapToObj(i -> new SampleVO(i, "길동" + i, "이"))
				.collect(Collectors.toList());
	}

	@GetMapping(value="/getList2")
	public List<SampleVO> getList2() {
		//0~9라는 값을 각각만들고 이를 활용하여 객체 생성한다. 이후에 리스로 만든다.
		List<SampleVO> ret = new ArrayList<>();
		
		for (int i = 0; i < 10; i++) {
			SampleVO svo = new SampleVO(i, "길동" + i, "이");
			ret.add(svo);
		}
		return ret;
	}

	@GetMapping(value="/getMap")
	public Map<String, String> getMap() {
		return IntStream.range(0, 10).mapToObj(i -> new SampleVO(i, "길동" + i, "이")).collect(Collectors.toMap(SampleVO::getFirstName, SampleVO::getSecondName));
	}

	@GetMapping(value="/check", params= {"height", "weight"})
	public ResponseEntity<SampleVO> check(double height, double weight) {
		SampleVO svo = new SampleVO(0, "" + height, "" + weight);
		ResponseEntity<SampleVO> ret = null;
		if (height < 150) {
			ret = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(svo);
		} else {
			ret = ResponseEntity.status(HttpStatus.OK).body(svo);
		}
		
		return ret;
	}

	@GetMapping(value="/product/{cat}/{pid}")
	public String[] getPath(@PathVariable("cat") String cat,
			@PathVariable("pid") int pid) {
		return new String[] {"category" + cat, "productID :" + pid};
	}
	
	@PostMapping("/ticket")
	public SampleVO convert(@RequestBody SampleVO obj) {
		return obj;
	}
}
