package DAO;

import java.sql.*;

public class SettingDAO {
    private String userId;
    private String homepage;
    private int fontSize;
    private int pageZoom;
    private int searchEngine;
    private boolean enableJS;
    private boolean enableBookmarkBar;

    public SettingDAO() {
        this.userId = "0";
        this.homepage = "https://stackoverflow.com/";
        this.fontSize = 100;
        this.pageZoom = 100;
        this.searchEngine = 0;
        this.enableJS = true;
        this.enableBookmarkBar = false;
    }

    public SettingDAO(String userId, String homepage, int fontSize, int pageZoom, int searchEngine, boolean enableJS, boolean enableBookmarkBar) {
        this.userId = userId;
        this.homepage = homepage;
        this.fontSize = fontSize;
        this.pageZoom = pageZoom;
        this.searchEngine = searchEngine;
        this.enableJS = enableJS;
        this.enableBookmarkBar = enableBookmarkBar;
    }

    public void getSetting() {

    }

    public void updateSetting() {

    }

    public void deleteSetting() {

    }

    public String getUserId() {
        return userId;
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
}
