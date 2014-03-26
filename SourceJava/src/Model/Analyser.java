package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Analyser {
	public ArrayList<Session> sessions;
	public String description;
	public ArrayList<String> allRequests;
	public ArrayList<Visit> visitsByNum;
	public ArrayList<Session> ipsByNum;
	
	public int numUniqueSessions;

	public Analyser() {
	}
	
	public void analyse() {
		allRequests = new ArrayList<String>();
		ArrayList<String> allIPs = new ArrayList<String>();
		visitsByNum = new ArrayList<Visit>(); // visits ordered by number of total requests to corresponding requests
		ipsByNum = new ArrayList<Session>(); // ip adresses ordered by number of sessions created by this ip
		for (Session s : sessions) {
			int ipPos = allIPs.indexOf(s.ip);
			if (ipPos == -1) {
				allIPs.add(s.ip);
				Session ipByNum = new Session(s.ip);
				ipByNum.sessionsNum++;
				ipsByNum.add(ipByNum);
			} else {
				Session sess = ipsByNum.get(ipPos);
				sess.sessionsNum++;
			}
			for (Visit v : s.visits) {
				int pos = allRequests.indexOf(v.request);
				if (pos == -1) {
					allRequests.add(v.request);
					Visit vbyNum = new Visit(v.request);
					vbyNum.visitedNum++;
					visitsByNum.add(vbyNum);
				} else {
					Visit vis = visitsByNum.get(pos);
					vis.visitedNum++;
				}
			}
		}
		Collections.sort(visitsByNum, new visitNumComparator());
		Collections.sort(ipsByNum, new sessionNumComparator());
	}
	
	public class visitNumComparator implements Comparator<Visit> {
		@Override
		public int compare(Visit v2, Visit v1) {
			return v1.visitedNum < v2.visitedNum ? -1 : v1.visitedNum == v2.visitedNum ? 0 : 1;
		}
	}
	
	public class sessionNumComparator implements Comparator<Session> {
		@Override
		public int compare(Session s2, Session s1) {
			return s1.sessionsNum < s2.sessionsNum ? -1 : s1.sessionsNum == s2.sessionsNum ? 0 : 1;
		}
	}
	
	public void setVectorValues() {	
		int vectorsSize = allRequests.size();

		// Pour visualiser quelques sessions, maxDisplay et displayed
		int maxDisplay = 20;
		int displayed = 0;

		for (Session s : sessions) {
			s.vectorSession = new int[vectorsSize];
			if (s.visits.size() > 2 && displayed <= maxDisplay) {
				System.out.println(s.printVisits());
				displayed++;
			}
			for (Visit v : s.visits) {

				int pos = allRequests.indexOf(v.request);
				if (pos == -1) {
					System.out
							.println("Error : Missing a request in allRequests");
				} else {
					s.vectorSession[pos] = 1;
				}
			}
		}
	}
	
	public int meanSessionLength() {
		int mean = 0;
		for (Session s : sessions) {
			mean += s.visits.size();
		}
		mean = mean / sessions.size();
		return mean;
	}
}
