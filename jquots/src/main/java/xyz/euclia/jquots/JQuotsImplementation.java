/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.euclia.jquots;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;
import xyz.euclia.jquots.consumer.QuotsConsumer;
import xyz.euclia.jquots.models.CanProceed;
import xyz.euclia.jquots.models.QuotsUser;

/**
 *
 * @author pantelispanka
 */
public class JQuotsImplementation implements JQuots {

    private final String appId;
    private final String appSecret;
    private final AsyncHttpClient client;
    private final QuotsConsumer quotsConsumer;

    public JQuotsImplementation(AsyncHttpClient client, String appId, String appSecret, QuotsConsumer quotsConsumer) {
        this.appId = appId;
        this.appSecret = appSecret;
        this.client = client;
        this.quotsConsumer = quotsConsumer;
    }
    
    /**
     *
     * Get user by id A GET method at /users/{id} path.
     *
     * @param id is the users to be created id
     * @return Future Response get the response when finished.
     * If 200 status you get the user. 
     * Other you get an ErrorReport.
     *
     */
    @Override
    public Future<Response> getUserById(String id) {
        return this.quotsConsumer.getUserById(id);

    }

//    @Override
//    public CompletableFuture<QuotsUser> createUser(String id, String username, String email) {
//        QuotsUser qu = new QuotsUser();
//        qu.setEmail(email);
//        qu.setUsername(username);
//        qu.setId(id);
//        return this.quotsConsumer.createUser(qu);
//    }
    
    /**
     *
     * Creates user A POST method at /users The request body should be a QuotsUser 
     *
     * @param id is the users to be created id
     * @param username is the users to be created username
     * @param email is the users to be created email
     * @return Future Response get the response when finished. If 200 status
     * you get the user that created Other you get an ErrorReport.
     *
     */
    @Override
    public Future<Response> createUser(String id, String username, String email) {
        QuotsUser qu = new QuotsUser();
        qu.setEmail(email);
        qu.setUsername(username);
        qu.setId(id);
        return this.quotsConsumer.createUser(qu);
    }

    /**
     *
     * Checks user's ability to continue the task after the call users credits will be subtracted after a GET method at /users/{id}/quots with appid, usageType and usageSize params.
     *
     * @param userid is the users id
     * @param usageType is the procedures type
     * @param usageSize is the procedures size
     * @return Future Response get the response when finished. At the body you
     * get the CanProceed object
     *
     */
    @Override
    public Future<Response> canProceed(String userid, String usageType, String usageSize) {
        return this.quotsConsumer.canUserProceed(userid, this.appId, usageType, usageSize);
    }

    /**
     *
     * Updates user's credits 
     * The provided credits will be the user's credits!
     * A PUT method at /users/{id} path. 
     *
     * @param qu is the user that is to be updated
     * @return Future Response Get the response when finished. If status 200
     * body contains the updated user. Other than 200 returns an ErrorReport.
     */
    @Override
    public Future<Response> updateUserCredits(QuotsUser qu) {
        return this.quotsConsumer.updateCredits(qu);
    }

    /**
     *
     * Deletes user by id
     * A Delete method at /users/{id} path
     *
     * @param userid is the users id
     * @return Future Response Get the response when finished. If status 409
     * body contains the number one as per user deleted. Other than 409 returns
     * an ErrorReport.
     */
    @Override
    public Future<Response> deleteUser(String userid) {
        return this.quotsConsumer.deleteUserById(userid);
    }

    @Override
    public void close() throws IOException {
        this.client.close();
    }

}
