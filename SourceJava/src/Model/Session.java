package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class Session {
	public String ip;
	public String id;
	public String userAgent;
	public String referer;
	public ArrayList<Visit> visits;
	public int[] vectorSession;
	public int sessionsNum;

	public Session(String _ip, String _id, String _userAgent, String _referer) {
		ip = _ip;
		id = _id;
		userAgent = _userAgent;
		referer = _referer;
		visits = new ArrayList<Visit>();
	}
	
	public Session(String _ip) {
		ip = _ip;
		sessionsNum = 0;
	}


	public boolean sameSession1(Session s) {
		if (!sameUser(s)) {
			return false;
		}
		Date lastVisitDate = lastVisit().date;
		long lastTime = lastVisitDate.getTime();
		lastTime += 30 * 60 * 1000;
		long newTime = s.visits.get(0).date.getTime();
		return lastTime >= newTime;
	}
	
	public boolean sameSession2(Session s) {
		if (!sameUser(s)) {
			return false;
		}
		return referer.equals(s.referer);
	}
	
	public boolean sameUser(Session s) {
		if (!ip.equals(s.ip)) {
			return false;
		}
		if (!id.equals(s.id)) {
			return false;
		}
		if (!userAgent.equals(s.userAgent)) {
			return false;
		}
		return true;
	}

	public class visitComparator implements Comparator<Visit> {

		@Override
		public int compare(Visit v1, Visit v2) {
			return v1.date.compareTo(v2.date);
		}
	}

	public void addAndSort(Visit v) {
		visits.add(v);
		Collections.sort(visits, new visitComparator());
	}

	public Visit lastVisit() {
		return visits.get(visits.size() - 1);
	}

	public String printVisits() {
		String toPrint = "";
		for (Visit v : visits) {
			toPrint += "s:" + v.request;
			toPrint += " d:" + v.date.toString();
			toPrint += " | ";
		}
		return toPrint;
	}
}
