package com.webserver.domain;

public class UserDTO {

    private Integer id;
    private String username;
    private String email;
    private Double dollars;
    private Double coins;
    private String password;

    public UserDTO() {
    }

    public UserDTO(Integer id, String username, String email, Double dollars, Double coins, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.dollars = dollars;
        this.coins = coins;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getCoins() {
        return coins;
    }

    public void setCoins(Double coins) {
        this.coins = coins;
    }

    public Double getDollars() {
        return dollars;
    }

    public void setDollars(Double dollars) {
        this.dollars = dollars;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static class Builder {

        private Integer nestedId;
        private String nestedUsername;
        private String nestedEmail;
        private Double nestedDollars;
        private Double nestedCoins;
        private String nestedPassword;

        public Builder id(int id) {
            this.nestedId = id;
            return this;
        }

        public Builder userName(String username) {
            this.nestedUsername = username;
            return this;
        }

        public Builder email(String email) {
            this.nestedEmail = email;
            return this;
        }

        public Builder dollars(Double dollars) {
            this.nestedDollars = dollars;
            return this;
        }

        public Builder password(String password) {
            this.nestedPassword = password;
            return this;
        }

        public Builder coins(double coins) {
            this.nestedCoins = coins;
            return this;
        }

        public UserDTO create() {
            return new UserDTO(nestedId, nestedUsername, nestedEmail, nestedDollars, nestedCoins, nestedPassword);
        }

    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", dollars=" + dollars +
                ", coins=" + coins +
                ", password='" + password + '\'' +
                '}';
    }
}
