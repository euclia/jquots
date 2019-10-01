/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.euclia.jquots;

import java.io.Closeable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import org.asynchttpclient.Response;
import xyz.euclia.jquots.exception.JQuotsException;
import xyz.euclia.jquots.models.CanProceed;
import xyz.euclia.jquots.models.QuotsUser;

/**
 *
 * @author pantelispanka
 */
public interface JQuots extends Closeable {
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
    Future<Response> getUserById(String id);
//    CompletableFuture<QuotsUser> createUser(String id, String username, String email);

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
    Future<Response> createUser(String id, String username, String email);
    /**
     *
     * Checks user's ability to continue the task After the call user's credits
     * will be subtracted a GET method at /users/{id}/quots?appid={appid}&usage={usageType}&size={usageSize}
     *
     * @param userid is the users id
     * @param usageType is the procedures type
     * @param usageSize is the procedures size
     * @return Future Response get the response when finished. At the body you
     * get the CanProceed object
     *
     */
    Future<Response> canProceed(String userid, String usageType, String usageSize);
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
    Future<Response> deleteUser(String userid);
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
    Future<Response> updateUserCredits(QuotsUser quotsUser);

}
