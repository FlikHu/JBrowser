package Main;

public class SessionManager {
    private static SessionManager manager;
    private String userId;
    private String username;
    private String homepage;
    private int fontSize;
    private int pageZoom;
    private int searchEngine;
    private boolean enableJS;
    private boolean enableBookmarkBar;

    private SessionManager() {
        this.userId = "0";
        this.username = "Guest";
        this.homepage = "https://stackoverflow.com/";
        this.fontSize = 100;
        this.pageZoom = 100;
        this.searchEngine = 0;
        this.enableJS = true;
        this.enableBookmarkBar = false;
    }

    private SessionManager(String userId, String username, String homepage, int fontSize, int pageZoom,
                           int searchEngine, boolean enableJS, boolean bookmarkBar) {
        this.userId = userId;
        this.username = username;
        this.homepage = homepage;
        this.fontSize = fontSize;
        this.pageZoom = pageZoom;
        this.searchEngine = searchEngine;
        this.enableJS = enableJS;
        this.enableBookmarkBar = bookmarkBar;
    }

    public void initialize(String userId) {

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

    public int getSearchEngine() {
        return searchEngine;
    }

    public boolean isEnableJS() {
        return enableJS;
    }

    public boolean isEnableBookmarkBar() {
        return enableBookmarkBar;
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

    public void setSearchEngine(int searchEngine) {
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
