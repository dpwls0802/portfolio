package www.flopo.com.domain;

import lombok.Data;

@Data
public class CurrentScreenInfo {
	private double sw_latitude;
	private double sw_longitude;
	private double ne_latitude;
	private double ne_longitude;
	private int map_level;
}
