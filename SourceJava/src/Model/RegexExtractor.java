package Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RegexExtractor {

	private static final String regexGet = "GET ";
	private static final String regexQotM = "\"";
	private static final String regexSpace = " ";
	private static final String regexDot = "\\.";
	private static final String regexDash = "-";
	private static final String regexOBra = "\\[";
	private static final String regexCBra = "\\]";
	private static final String regexNav = "Mozilla";
	private static final String regexCompat = "compatible";
	private static final String regexBotName = "x1crawler";
	private static final String regexEnutBot = "www.WISEnutbot.com";
	private static final String regexNomadeBot = "NomadeBot";
	private static final String regexGoogleBot = "googlebot";

	public static String getRequest(String line) {
		String[] split1 = line.split(regexQotM);
		if (split1.length <= 1) {
			System.out.println("Line too small");
			return null;
		}
		if (split1[1].contains(regexGet)) {
			String requestFileR = split1[1].split(regexGet)[1];
			requestFileR = requestFileR.split(regexSpace)[0];
			return requestFileR;
		} else {
			// System.out.println("No GET");
			return null;
		}
	}

	public static boolean hasRequestFileType(String line, String fileType) {
		String[] split1 = line.split(regexQotM);
		if (split1.length <= 1) {
			System.out.println("Line too small");
			return false;
		}
		if (split1[1].contains(regexGet)) {
			String requestFileR = split1[1].split(regexGet)[1];
			requestFileR = requestFileR.split(regexSpace)[0];
			String[] split2 = requestFileR.split(regexDot);
			String requestFileTypeR = split2[split2.length - 1];
			return requestFileTypeR.equals(fileType);
		} else {
			// System.out.println("No GET");
			return false;
		}
	}

	public static boolean hasRequestStatus(String line, String fileType) {
		String[] split1 = line.split(regexQotM);
		if (split1.length <= 1) {
			System.out.println("Line too small");
			return false;
		}
		if (split1[1].contains(regexGet)) {
			String[] split2 = split1[2].split(regexSpace);
			String requestStatus = split2[1];
			return requestStatus.equals(fileType);
		} else {
			// System.out.println("No GET");
			return false;
		}
	}

	public Session getSession(String line, int l) {
		String[] split1 = line.split(regexSpace);
		if (!split1[2].equals(regexDash)) {
			System.out.println("No second dash on line " + l);
		}
		String ip = split1[0];
		String id = split1[1];
		String[] split2 = line.split(regexQotM);
		String userAgent = split2[5];
		String referer = split2[3];

		Session s = new Session(ip, id, userAgent, referer);

		String dateR = line.split(regexOBra)[1].split(regexCBra)[0];
		SimpleDateFormat format = new SimpleDateFormat(
				"dd/MMM/yyyy:HH:mm:ss Z", Locale.ROOT);
		Date date = null;
		try {
			date = format.parse(dateR);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Visit v = new Visit(getRequest(line), date);
		s.addAndSort(v);

		return s;
	}

	public static boolean isBot(String line) {
		String[] split1 = line.split(regexQotM);
		String[] split2 = split1[5].split(regexSpace);
		if (!split2[0].contains(regexNav)) {
			return true;
		}
		if (!split2[1].contains(regexCompat)) {
			return true;
		}
		if (split1[0].contains(regexBotName)) {
			return true;
		}
		if (split1[0].contains(regexGoogleBot)) {
			return true;
		}
		if (split2.length >= 8) {
			if (split2[7].contains(regexEnutBot)) {
				return true;
			}
			if (split2.length >= 12) {
				if (split2[11].contains(regexNomadeBot)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Permet d'avoir le userAgent sans espace, pour le fichier weka, juste la
	 * première partie
	 * 
	 * @param userAgent
	 * @return
	 */
	public String getShortUserAgent(String userAgent) {
		return userAgent.split(regexSpace)[0];
	}
}
