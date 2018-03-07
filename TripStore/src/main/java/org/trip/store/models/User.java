package org.trip.store.models;

public class User {

    private long id;
    private String login;
    private String password;


    public User(long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public void setId(long id) {
        this.id = id;
    }


    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }



    @Override
    public boolean equals(Object obj) {

        if ((obj == null) || !(obj instanceof User))
            return false;

        User other = (User) obj;

        if (other.getLogin().equals(this.login)
                && other.getPassword().equals(this.password)
                && other.getId() == this.id)
            return true;

        return false;
    }
}
