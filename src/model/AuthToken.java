package model;

/**
 * Each of this authToken correspond to a user
 */
class AuthToken {
    /**
     * user's unique name
     */
    private String userName;

    /**
     * It takes user Name and token string to construct it
     *
     * @param userName    user's unique name
     */
    public AuthToken(String userName) {
        this.userName = userName;
    }


    public String getUserName() {
        return userName;
    }


}
