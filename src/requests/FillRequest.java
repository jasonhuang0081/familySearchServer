package requests;

/**
 * This class is the request that is used in FillService
 */
public class FillRequest {
    /**
     * user's login name
     */
    private String userName;
    /**
     * a number of generation needs to generated
     */
    private int numGeneration;

    /**
     * construct fill request that's used in fill service
     *
     * @param userName      user's login name
     * @param numGeneration a number of generation needs to be generated, default is 4
     */
    public FillRequest(String userName, int numGeneration) {
        this.userName = userName;
        this.numGeneration = numGeneration;
    }

    public String getUserName() {
        return userName;
    }

    public int getNumGeneration() {
        return numGeneration;
    }

}
