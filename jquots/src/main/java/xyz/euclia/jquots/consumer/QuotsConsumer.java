/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.euclia.jquots.consumer;

import io.netty.handler.codec.http.HttpHeaders;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import org.asynchttpclient.AsyncHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.HttpResponseBodyPart;
import org.asynchttpclient.HttpResponseStatus;
import org.asynchttpclient.Param;
import org.asynchttpclient.Response;
import xyz.euclia.jquots.exception.JQuotsException;
import xyz.euclia.jquots.models.CanProceed;
import xyz.euclia.jquots.models.ErrorReport;
import xyz.euclia.jquots.models.Quots;
import xyz.euclia.jquots.models.QuotsUser;
import xyz.euclia.jquots.serialize.Serializer;

/**
 *
 * @author pantelispanka
 */
public class QuotsConsumer extends BaseConsumer {

    private final String basePath;
    private final String appId;
    private final String appSecret;
    private final Serializer serializer;
    private final AsyncHttpClient client;

    public QuotsConsumer(AsyncHttpClient client, Serializer serializer, String base, String appId, String appSecret) {
        super(client, serializer);
        this.serializer = serializer;
        this.client = client;
        this.basePath = base;
        this.appId = appId;
        this.appSecret = appSecret;
    }

    public Future<Response> getUserById(String userId) {
        String path = this.basePath + "/users/" + userId;
        Future<Response> f = client.prepareGet(path)
                .addHeader("Authorization", "QUOTSAPP")
                .addHeader("app-id", this.appId)
                .addHeader("app-secret", this.appSecret)
                .execute();
        return f;
//        return get(path, appId, appSecret, QuotsUser.class);
    }

    public Future<Response> createUser(QuotsUser user) throws JQuotsException {
        String path = this.basePath + "/users";
        Future<Response> f = client.preparePost(path)
                .addHeader("Authorization", "QUOTSAPP")
                .addHeader("app-id", this.appId)
                .addHeader("app-secret", this.appSecret)
                .setBody(this.serializer.write(user)).execute();
        return f;
    }

    public Future<Response> canUserProceed(String userId, String appId, String usage, String usageSize) {
        String path = this.basePath + "/users/" + userId + "/quots";
        List<Param> params = new ArrayList();
        Param a = new Param("appid", appId);
        Param b = new Param("usage", usage);
        Param c = new Param("size", usageSize);
        params.add(a);
        params.add(b);
        params.add(c);
        Future<Response> f = this.client.prepareGet(path)
                .addHeader("Authorization", "QUOTSAPP")
                .addHeader("app-id", this.appId)
                .addHeader("app-secret", this.appSecret).addQueryParams(params)
                .execute();
        return f;
//        return getWithParams(path, params, appId, appSecret, CanProceed.class, ErrorReport.class);
    }

    public Future<Response> deleteUserById(String id) {
        String path = this.basePath + "/users/" + id;
        Future<Response> f = this.client.prepareDelete(path)
                .addHeader("Authorization", "QUOTSAPP")
                .addHeader("app-id", this.appId)
                .addHeader("app-secret", this.appSecret)
                .execute();
        return f;
//        return delete(path, null, appId, appSecret, Integer.class);
    }

    public Future<Response> updateCredits(QuotsUser qu) {
        String path = this.basePath + "/users/credits";
        Future<Response> f = client.preparePut(path)
                .addHeader("Authorization", "QUOTSAPP")
                .addHeader("app-id", this.appId)
                .addHeader("app-secret", this.appSecret) 
                .setBody(serializer.write(qu))
                .execute();
        return f;
//        return put(path, qu, appId, appSecret, QuotsUser.class);
    }

}
