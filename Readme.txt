CPM - Alex Pavy

Lancement du programme :
	A partir de CPM-pavy.jar : 1 argument : nom du fichier d'entrée, dans le même répertoire que CPM-pavy.jar
	Par exemple : CPM-pavy.jar "bourges.txt"
	
	Fonction main dans la classe FiltersView
	
Utilisation :

	- 1ère fenêtre : Filtrage
	Etape facultative.
	Le filtrage va écrire un nouveau fichier dont le nom est <fichier_entrée>+<nom_filtrage>.
	Plusieurs filtrages successifs sont possibles.
	Par la suite le fichier filtré sera la fichier utilisé. Le premier fichier utilisé est toujours celui donné en argument du programme.
		- Request File Type : Par type de fichier dans la requête GET
		Dans la zone de texte, écrire par exemple jpg ou htm pour garder uniquement les requêtes de .jpg ou .htm
		- Request Status : Par statut de la requête GET
		Dans la zone de texte, écrire par exemple 404 ou 200 pour garder uniquement les requêtes de statut 404 ou 200
		- Bots : Filtrage de la majorité des bots, au cas par cas
		
	- 2ème fenêtre : Analyses
		- Choix de la méthode de création des sessions.
		- Statistiques :
		Longueur des sessions : Nombre de requêtes GET
		Sessions uniques : Qui n'ont pas le même triplet ip + id + userAgent
		Pages les plus visitées: Avec le nombre de visites
		Adresses ip ayant le plus de sessions : Avec le nombre de sessions
		- Export Only Int : Si coché, le fichier d'export contient uniquement des vecteurs d'entiers.
		Sinon, il contient aussi le triplet ip + id + userAgent des sessions (ce n'était pas demandé, je m'étais trompé).
		- Fichier exporté : au format .arff, dans le même répertoire que le CPM-pavy.jar