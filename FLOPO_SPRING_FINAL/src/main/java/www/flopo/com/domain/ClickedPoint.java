package www.flopo.com.domain;

import lombok.Data;

@Data
public class ClickedPoint {
	private double clickedAreaLat;//Ŭ������ ����
	private double clickedAreaLng; //Ŭ������ �浵
	private int current_map_level; //���� ����
	private int time;
}
