package Main;

import DAO.BookmarkDAO;
import DAO.SearchEngines;
import DAO.SettingDAO;
import DAO.UserDAO;

import java.util.ArrayList;
import java.util.List;

public class SessionManager {
    private static SessionManager manager;
    private String userId;
    private String username;
    private String homepage;
    private int fontSize;
    private int pageZoom;
    private SearchEngines searchEngine;
    private boolean enableJS;
    private boolean enableBookmarkBar;
    private List<String[]> bookmarks;

    private SessionManager() {
        this.userId = "0";
        this.username = "Guest";
        this.homepage = "https://stackoverflow.com/";
        this.fontSize = 100;
        this.pageZoom = 100;
        this.searchEngine = SearchEngines.GOOGLE;
        this.enableJS = true;
        this.enableBookmarkBar = false;
        this.bookmarks = new ArrayList<>();
    }

    private SessionManager(String userId, String username, String homepage, int fontSize, int pageZoom,
                           SearchEngines searchEngine, boolean enableJS, boolean bookmarkBar, List<String[]> bookmarks) {
        this.userId = userId;
        this.username = username;
        this.homepage = homepage;
        this.fontSize = fontSize;
        this.pageZoom = pageZoom;
        this.searchEngine = searchEngine;
        this.enableJS = enableJS;
        this.enableBookmarkBar = bookmarkBar;
        this.bookmarks = bookmarks;
    }

    public void retrieveSetting(UserDAO userDAO) {
        this.userId = userDAO.getId();
        this.username = userDAO.getUsername();
        SettingDAO settingDAO = new SettingDAO();
        settingDAO.getSetting(this.userId);
        this.homepage = settingDAO.getHomepage();
        this.fontSize = settingDAO.getFontSize();
        this.pageZoom = settingDAO.getPageZoom();
        this.searchEngine = settingDAO.getSearchEngine();
        this.enableJS = settingDAO.isEnableJS();
        this.enableBookmarkBar = settingDAO.isEnableBookmarkBar();
        BookmarkDAO bookmarkDAO = new BookmarkDAO(this.userId);
        this.bookmarks = bookmarkDAO.getBookmarkList();
    }

    public void endSession() {
        manager = new SessionManager();
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return this.username;
    }

    public String getHomepage() {
        return homepage;
    }

    public int getFontSize() {
        return fontSize;
    }

    public int getPageZoom() {
        return pageZoom;
    }

    public SearchEngines getSearchEngine() {
        return searchEngine;
    }

    public boolean isEnableJS() {
        return enableJS;
    }

    public boolean isEnableBookmarkBar() {
        return enableBookmarkBar;
    }

    public List<String[]> getBookmarks() {
        return bookmarks;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void setPageZoom(int pageZoom) {
        this.pageZoom = pageZoom;
    }

    public void setSearchEngine(SearchEngines searchEngine) {
        this.searchEngine = searchEngine;
    }

    public void setEnableJS(boolean enableJS) {
        this.enableJS = enableJS;
    }

    public void setEnableBookmarkBar(boolean enableBookmarkBar) {
        this.enableBookmarkBar = enableBookmarkBar;
    }

    public static SessionManager getInstance() {
        if(manager == null) {
            manager = new SessionManager();
            return manager;
        } else {
            return manager;
        }
    }
}
