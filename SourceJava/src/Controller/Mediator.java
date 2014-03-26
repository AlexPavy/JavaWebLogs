package Controller;

import Model.Analyser;
import Model.LogsReader;
import Model.Session;
import Model.Visit;
import Views.FiltersView;
import Views.SessionsView;

public class Mediator {

	private String curFile;
	private LogsReader reader;

	public FiltersView filtersView;
	public SessionsView sessionsView;
	public Analyser analyser;
	public boolean exportDone;

	public Mediator(FiltersView fView, String entryFile) {
		curFile = entryFile;
		reader = new LogsReader();
		filtersView = fView;
		exportDone = false;
	}

	public void FilterLogs(String filter, String match) {
		reader.fileR = curFile;
		int linesNum = reader.filter(filter, match);
		String toShow = "File " + curFile + " filtered into " + reader.fileW
				+ "\n\n";
		toShow += "Number of lines after filter: " + linesNum;
		filtersView.displayResult(toShow);
		curFile = reader.fileW;
	}

	public void generateSessions(String method) {
		reader.fileR = curFile;
		analyser = new Analyser();
		reader.readSessions(method, analyser);
		analyser.analyse();
		String toShow = "% Sessions générées pour " + curFile + "\n%\n";
		toShow += "% Il y a ";
		toShow += analyser.sessions.size();
		toShow += " sessions\n";
		toShow += "% Longueur moyenne des sessions: "
				+ analyser.meanSessionLength();
		toShow += "\n% Nombre de sessions uniques: "
				+ analyser.numUniqueSessions;
		toShow += "\n% Pages les plus visitées: "
				+ getMostVisited(10);
		toShow += "\n% Adresses ip ayant le plus de sessions : "
				+ getMostSessionsNum(10);
		analyser.description = toShow;
		sessionsView.displayResult(toShow);
	}

	public void exportSessions(boolean onlyInt) {
		if (analyser == null) {
			sessionsView.displayResult("Il faut d'abord générer les sessions");
			return;
		}
		String toShow = analyser.description;
		toShow += "\n\nDonnées des sessions exportées dans " + reader.fileW;
		analyser.setVectorValues();
		reader.exportSessions(analyser, onlyInt);
		exportDone = true;
		sessionsView.displayResult(toShow);
	}

	public String getMostVisited(int maxNum) {
		String res = "";
		int k = 0;
		while (k < maxNum) {
			Visit v = analyser.visitsByNum.get(k);
			res += "\n%\t" + v.visitedNum + " : " + v.request ;
			k++;
		}
		return res;
	}
	
	public String getMostSessionsNum(int maxNum) {
		String res = "";
		int k = 0;
		while (k < maxNum) {
			Session s = analyser.ipsByNum.get(k);
			res += "\n%\t" + s.sessionsNum + " : " + s.ip ;
			k++;
		}
		return res;
	}
}
