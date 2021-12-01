package www.flopo.com.domain;

import lombok.Data;

@Data
public class MapVO {
	private int id;
	private double sw_latitude;
	private double sw_longitude;
	private double ne_latitude;
	private double ne_longitude;
	private int zoom_lvl;
	private int parent_area_id;
}
