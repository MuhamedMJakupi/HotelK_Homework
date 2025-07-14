package core;

public class User {
    protected String id;
    protected String name;
    protected String role;

    public User(String id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    //2. - Homework 3
    public String buildGreeting(char[] template) {
        StringBuilder greeting = new StringBuilder();
        for (char c : template) {
            if (c == '*')
                greeting.append(role.charAt(0));
            else
                greeting.append(c);
        }
        return greeting.toString() + ", " + name + "!";
    }
}
