package it.dedagroup.venditabiglietti.principal.util;

public class UtilPath {

	//-------------------------------------------SUPER ADMIN------------------------------------------------------/
	public static final String SUPER_ADMIN_PATH = "/superAdmin";
	
	public static final String AGGIUNGI_ADMIN = "admin/add";
	public static final String DISATTIVA_ADMIN = "admin/deactivate/{id}";
	
	//-------------------------------------------ADMIN------------------------------------------------------/
	public static final String ADMIN_PATH = "/admin";
	
	public static final String ELIMINA_UTENTE_VENDITORE = "/elimina-utente-venditore";
	public static final String ELIMINA_UTENTE_CLIENTE = "/elimina-utente-cliente";
}
