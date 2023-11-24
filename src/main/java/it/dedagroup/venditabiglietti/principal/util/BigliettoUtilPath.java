package it.dedagroup.venditabiglietti.principal.util;

public class BigliettoUtilPath {
    public static final String BIGLIETTO_PATH = "http://localhost:8087/biglietto";
    public static final String INSERT_BIGLIETTO_PATH = BIGLIETTO_PATH+"/save";
    public static final String MODIFY_BIGLIETTO_PATH = BIGLIETTO_PATH+"/modify";
    public static final String DELETE_BIGLIETTO_PATH = BIGLIETTO_PATH+"/delete/id";
    public static final String FIND_BY_ID_PATH = BIGLIETTO_PATH+"/find/id";
    public static final String FIND_ALL_PATH = BIGLIETTO_PATH+"/find/all";
    public static final String FIND_BY_SERIALE_PATH = BIGLIETTO_PATH+"/find/seriale";
    public static final String FIND_ALL_BY_PREZZO_IS_GREATER_THAN_EQUAL_PATH = BIGLIETTO_PATH+"/find/all/prezzo/greater";
    public static final String FIND_ALL_BY_PREZZO_IS_LESS_THAN_EQUAL_PATH = BIGLIETTO_PATH+"/find/all/prezzo/less";
    public static final String FIND_ALL_BY_ID_UTENTE_PATH = BIGLIETTO_PATH+"/find/all/utente/id";
    public static final String FIND_ALL_BY_DATA_ACQUISTO = BIGLIETTO_PATH+"/find/all/data-acquisto";
    public static final String FIND_DISTINCT_PREZZO_BIGLIETTO_PATH = BIGLIETTO_PATH+"/find/distinct/prezzo/prezzo-settore-evento";
    public static final String FIND_ALL_BY_ID_PREZZO_SETTORE_EVENTO_IN_IDS = BIGLIETTO_PATH+"/find/all/prezzo-settore-evento/in/ids";
}
