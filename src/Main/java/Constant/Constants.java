package Constant;

import DAO.SearchEngines;

public class Constants {
    public static final String DB_ADDRESS = "jdbc:sqlite:data.db";
    public static final String DB_DRIVER = "org.sqlite.JDBC";

    public static final String GUEST_USERID = "0";
    public static final String GUEST_NAME = "Guest";
    public static final String GUEST_HOMEPAGE = "https://stackoverflow.com/";
    public static final int GUEST_FONT_SIZE = 100;
    public static final int GUEST_PAGE_ZOOM = 100;
    public static final SearchEngines GUEST_SEARCH_ENGINE = SearchEngines.GOOGLE;
    public static final boolean GUEST_ENABLE_JAVASCRIPT = true;
    public static final boolean GUEST_ENABLE_BOOKMARKBAR = false;

    public static final int DOWNLOAD_BUFFER_SIZE = 2048;
    public static final int DOWNLOAD_CONNECTION_TIMEOUT = 2000;

    public static final int MAIN_SCENE_WIDTH = 1200;
    public static final int MAIN_SCENE_HEIGHT = 800;
    public static final int ICON_LOAD_CONNECTION_TIMEOUT = 1000;
    public static final String USER_AGENT_STRING = "Mozilla/5.0 (X11; CrOS x86_64 8172.45.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.64 Safari/537.36";

    public static final int SETTING_ITEM_WIDTH = 60;
    public static final int SETTING_ITEM_HEIGHT = 30;
}
