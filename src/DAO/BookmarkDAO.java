package DAO;

import java.sql.*;
import java.util.List;

public class BookmarkDAO {
    private String userId;
    private List<String[]> bookmarkList;

    public BookmarkDAO() {

    }

    public String getUserId() {
        return userId;
    }

    public List<String[]> getBookmarkList() {
        return bookmarkList;
    }
}
