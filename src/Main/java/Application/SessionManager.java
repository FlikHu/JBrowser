package Application;

import Component.SettingComponent.History;
import Constant.Constants;
import DAO.*;

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
    private List<String[]> downloads;
    private List<String[]> history;

    private SessionManager() {
        this.userId = Constants.GUEST_USERID;
        this.username = Constants.GUEST_NAME;
        this.homepage = Constants.GUEST_HOMEPAGE;
        this.fontSize = Constants.GUEST_FONT_SIZE;
        this.pageZoom = Constants.GUEST_PAGE_ZOOM;
        this.searchEngine = Constants.GUEST_SEARCH_ENGINE;
        this.enableJS = Constants.GUEST_ENABLE_JAVASCRIPT;
        this.enableBookmarkBar = Constants.GUEST_ENABLE_BOOKMARKBAR;

        BookmarkDAO bookmarkDAO = new BookmarkDAO(this.userId);
        bookmarkDAO.getBookmarks();
        this.bookmarks = bookmarkDAO.getBookmarkList();

        DownloadDAO downloadDAO = new DownloadDAO(this.userId);
        downloadDAO.getDownloads();
        this.downloads = downloadDAO.getDownloadHistory();

        HistoryDAO historyDAO = new HistoryDAO(this.userId);
        historyDAO.getHistory();
        this.history = historyDAO.getHistoryList();
    }

    private SessionManager(String userId, String username, String homepage, int fontSize, int pageZoom,
                           SearchEngines searchEngine, boolean enableJS, boolean bookmarkBar) {
        this.userId = userId;
        this.username = username;
        this.homepage = homepage;
        this.fontSize = fontSize;
        this.pageZoom = pageZoom;
        this.searchEngine = searchEngine;
        this.enableJS = enableJS;
        this.enableBookmarkBar = bookmarkBar;

        BookmarkDAO bookmarkDAO = new BookmarkDAO(userId);
        bookmarkDAO.getBookmarks();
        this.bookmarks = bookmarkDAO.getBookmarkList();

        DownloadDAO downloadDAO = new DownloadDAO(userId);
        downloadDAO.getDownloads();
        this.downloads = downloadDAO.getDownloadHistory();

        HistoryDAO historyDAO = new HistoryDAO(userId);
        historyDAO.getHistory();
        this.history = historyDAO.getHistoryList();
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
        bookmarkDAO.getBookmarks();
        this.bookmarks = bookmarkDAO.getBookmarkList();

        DownloadDAO downloadDAO = new DownloadDAO(this.userId);
        downloadDAO.getDownloads();
        this.downloads = downloadDAO.getDownloadHistory();

        HistoryDAO historyDAO = new HistoryDAO(this.userId);
        historyDAO.getHistory();
        this.history = historyDAO.getHistoryList();
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

    public List<String[]> getDownloads() {
        return downloads;
    }

    public List<String[]> getHistory() {
        return history;
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

    public void updateBookmarkList(){
        BookmarkDAO bookmarkDAO = new BookmarkDAO(this.userId);
        bookmarkDAO.getBookmarks();
        this.bookmarks = bookmarkDAO.getBookmarkList();
    }

    public void updateDownloadHistoryList() {
        DownloadDAO downloadDAO = new DownloadDAO(this.userId);
        downloadDAO.getDownloads();
        this.downloads = downloadDAO.getDownloadHistory();
    }

    public void updateHistoryList() {
        HistoryDAO historyDAO = new HistoryDAO(this.userId);
        historyDAO.getHistory();
        this.history = historyDAO.getHistoryList();
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
