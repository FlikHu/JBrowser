package Main;

public class SessionManager {

    private SessionManager manager;

    private SessionManager() {

    }

    public SessionManager getInstance() {
        if(this.manager == null) {
            this.manager = new SessionManager();
            return this.manager;
        } else {
            return this.manager;
        }
    }
}
