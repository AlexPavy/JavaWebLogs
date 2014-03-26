CPM - Alex Pavy

Lancement du programme :
	A partir de CPM-pavy.jar : 1 argument : nom du fichier d'entr�e, dans le m�me r�pertoire que CPM-pavy.jar
	Par exemple : CPM-pavy.jar "bourges.txt"
	
	Fonction main dans la classe FiltersView
	
Utilisation :

	- 1�re fen�tre : Filtrage
	Etape facultative.
	Le filtrage va �crire un nouveau fichier dont le nom est <fichier_entr�e>+<nom_filtrage>.
	Plusieurs filtrages successifs sont possibles.
	Par la suite le fichier filtr� sera la fichier utilis�. Le premier fichier utilis� est toujours celui donn� en argument du programme.
		- Request File Type : Par type de fichier dans la requ�te GET
		Dans la zone de texte, �crire par exemple jpg ou htm pour garder uniquement les requ�tes de .jpg ou .htm
		- Request Status : Par statut de la requ�te GET
		Dans la zone de texte, �crire par exemple 404 ou 200 pour garder uniquement les requ�tes de statut 404 ou 200
		- Bots : Filtrage de la majorit� des bots, au cas par cas
		
	- 2�me fen�tre : Analyses
		- Choix de la m�thode de cr�ation des sessions.
		- Statistiques :
		Longueur des sessions : Nombre de requ�tes GET
		Sessions uniques : Qui n'ont pas le m�me triplet ip + id + userAgent
		Pages les plus visit�es: Avec le nombre de visites
		Adresses ip ayant le plus de sessions : Avec le nombre de sessions
		- Export Only Int : Si coch�, le fichier d'export contient uniquement des vecteurs d'entiers.
		Sinon, il contient aussi le triplet ip + id + userAgent des sessions (ce n'�tait pas demand�, je m'�tais tromp�).
		- Fichier export� : au format .arff, dans le m�me r�pertoire que le CPM-pavy.jar