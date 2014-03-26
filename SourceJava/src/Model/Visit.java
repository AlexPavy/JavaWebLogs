package Model;

import java.util.Date;

public class Visit {
	public String request;
	public Date date;
	public int visitedNum; // total number of visited times

	public Visit(String _request, Date _date) {
		request = _request;
		date = _date;
	}
	
	public Visit(String _request) {
		request = _request;
		visitedNum = 0;
	}
}
