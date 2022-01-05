package ToolLibrary;

/*
 * 该类将毫秒时间转换成相对应的时间表达
 * 将用于SmartSquare类
 * */
public interface TimeChecker {
	
	/*
	 * 返回程序运行的标准时间表达
	 * @param time 在游戏开始和结束之间的时间
	 * @return 总用时（使用文本描述）
	 * */
	static String calculateTime(long time) {
		
		int ms=(int) time;
		int sec=ms/1000;
		int min=sec/60;
		int hr=min/60;
		
		if(hr==0) {
			if(min==0) {
				if(sec==0) {
					return "0 sec";
				}else {
					return sec+" sec";
				}				
			}else {
				return min+" min"+sec%60+" sec";
			}
		}else {
			return hr+" hour"+min%60+" min"+sec%60+" sec";
		}
		
		
	}

}
