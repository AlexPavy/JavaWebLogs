package Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LogsReader {
	public String fileR;
	public String fileW;

	private RegexExtractor extractor;

	private static final String filterRequestFile = "_FRF_";
	private static final String filterRequestStatus = "_FRS_";
	private static final String filterBots = "_FB_";

	private static final String fileExt = ".txt";
	private static final String wekaExt = ".arff";

	public static final String requestFileType = "Request File Type";
	public static final String requestStatus = "Request Status";
	public static final String bot = "Bots";
	public static final String byExpiration = "By expiration after 30 min";
	public static final String byReferer = "By same referer";

	public static final String wString = "string";
	public static final String wBool = "{0,1}";
	public static final String wComSp = ", ";

	public LogsReader() {
		extractor = new RegexExtractor();
	}

	/**
	 * 
	 * @param filter
	 * @param match
	 * @return The number of lines of the file
	 */
	public int filter(String filter, String match) {
		String nameW = fileR.split(fileExt)[0];
		fileW = nameW + abbreviation(filter);
		if (filter.equals(bot)) {
			fileW += fileExt;
		} else {
			fileW += match + fileExt;
		}
		int n = 0;

		BufferedReader brR = null;
		BufferedWriter brW = null;
		try {
			brR = new BufferedReader(new FileReader(fileR));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			brW = new BufferedWriter(new FileWriter(fileW));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			n = 0; // number of lines
			while (true) {
				String line = brR.readLine();
				if (line == null) {
					break;
				} else {
					if (line.length() <= 3) {
						System.out.println("Petite Ligne");
						break;
					}
				}
				boolean keep = true;
				if (filter.equals(requestFileType)) {
					keep = RegexExtractor.hasRequestFileType(line, match);
				} else if (filter.equals(requestStatus)) {
					keep = RegexExtractor.hasRequestStatus(line, match);
				} else if (filter.equals(bot)) {
					keep = !RegexExtractor.isBot(line);
				}
				if (keep) {
					brW.append(line + "\n");
					n++;
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			brR.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			brW.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return n;
	}

	public void readSessions(String method, Analyser analyser) {
		ArrayList<Session> sessions = new ArrayList<Session>();
		int uniqueSessions = 0;
		BufferedReader brR = null;
		try {
			brR = new BufferedReader(new FileReader(fileR));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			int l = 0; // line number
			while (true) {
				l++;
				String line = brR.readLine();
				if (line == null) {
					break;
				} else {
					if (line.length() <= 3) {
						System.out.println("Petite Ligne");
						break;
					}
				}
				Session s = extractor.getSession(line, l);
				boolean isNewSession = true;
				boolean isNewUser = true;
				for (Session s1 : sessions) {
					if (method.equals(byExpiration)) {
						if (s1.sameSession1(s)) {
							s1.addAndSort(s.lastVisit());
							isNewSession = false;
						}
					} else if (method.equals(byReferer)) {
						if (s1.sameSession2(s)) {
							s1.addAndSort(s.lastVisit());
							isNewSession = false;
						}
					}
					if(s1.sameUser(s)) {
						isNewUser = false;
					}
				}
				if (isNewSession) {
					sessions.add(s);
				}
				if (isNewUser) {
					uniqueSessions++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			brR.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		analyser.numUniqueSessions = uniqueSessions;
		analyser.sessions = sessions;
	}

	public void exportSessions(Analyser analyser, boolean onlyInt) {
		String nameW = fileR.split(fileExt)[0];
		fileW = nameW + wekaExt;
		BufferedWriter brW = null;
		try {
			brW = new BufferedWriter(new FileWriter(fileW));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			brW.append(analyser.description);
			brW.append("\n\n@relation visitedSites\n\n");
			if (!onlyInt) {
				brW.append("@attribute user_ip " + wString + "\n");
				brW.append("@attribute user_id " + wString + "\n");
				brW.append("@attribute userAgent " + wString + "\n");
			}
			// analyser.setAllDifferentRequests();
			for (String request : analyser.allRequests) {
				brW.append("@attribute " + request + " " + wBool + "\n");
			}
			brW.append("\n@data\n\n");
			for (Session s : analyser.sessions) {
				if (!onlyInt) {
					brW.append(s.ip + wComSp);
					brW.append(s.id + wComSp);
					brW.append(extractor.getShortUserAgent(s.userAgent));
				}
				for (int i = 0; i < s.vectorSession.length; i++) {
					int has = s.vectorSession[i];
					if (onlyInt && i == 0) {
						brW.append("" + has);
					} else {
						brW.append(wComSp + has);
					}
				}
				brW.append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			brW.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String abbreviation(String filter) {
		if (filter.equals(requestFileType)) {
			return filterRequestFile;
		} else if (filter.equals(requestStatus)) {
			return filterRequestStatus;
		} else if (filter.equals(bot)) {
			return filterBots;
		}
		return null;
	}
}
