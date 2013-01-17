package codechef.api.models;

import java.util.ArrayList;
import java.util.List;

public class MessageList {

    List<String> users;

    public MessageList() {
        this.users = new ArrayList<>();
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}
