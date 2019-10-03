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
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.asynchttpclient.AsyncHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.HttpResponseBodyPart;
import org.asynchttpclient.HttpResponseStatus;
import org.asynchttpclient.Param;
import xyz.euclia.jquots.exception.JQuotsException;
import xyz.euclia.jquots.serialize.Serializer;

/**
 *
 * @author pantelispanka
 */
public abstract class BaseConsumer {

    private final AsyncHttpClient client;
    private final Serializer serializer;
//    private final String basePath;

    BaseConsumer(AsyncHttpClient client, Serializer serializer) {
        this.client = client;
        this.serializer = serializer;
//        this.basePath = basePath;
    }

    public <T> CompletableFuture<T> get(String path, String appid, String appsecret, Class<T> c) {

        CompletableFuture<T> future = this.client
                .prepareGet(path)
                .addHeader("Authorization", "QUOTSAPP")
                .addHeader("app-id", appid)
                .addHeader("app-secret", appsecret)
                .execute(new AsyncHandler<T>() {

                    private Integer statusCode;
                    private InputStream sis;
                    io.netty.handler.codec.http.HttpHeaders headers;

                    @Override
                    public AsyncHandler.State onStatusReceived(HttpResponseStatus status) throws Exception {
                        statusCode = status.getStatusCode();
                        if (statusCode >= 400) {
                            return AsyncHandler.State.ABORT;
                        }
                        return AsyncHandler.State.CONTINUE;
                    }

                    @Override
                    public AsyncHandler.State onHeadersReceived(HttpHeaders h) throws Exception {
                        headers = h;
                        return AsyncHandler.State.CONTINUE;
                    }

                    @Override
                    public T onCompleted() throws Exception {
                        return serializer.parse(sis, c);
                    }

                    @Override
                    public void onThrowable(Throwable t) {
                        throw new JQuotsException(t);
                    }

                    @Override
                    public AsyncHandler.State onBodyPartReceived(HttpResponseBodyPart httpResponseBodyPart) throws Exception {
                        if (sis == null) {
                            sis = new ByteArrayInputStream(httpResponseBodyPart.getBodyPartBytes(), 0, httpResponseBodyPart.length());
                        } else {
                            sis = new SequenceInputStream(sis, new ByteArrayInputStream(httpResponseBodyPart.getBodyPartBytes(), 0, httpResponseBodyPart.length()));
                        }
                        return AsyncHandler.State.CONTINUE;
                    }
                }).toCompletableFuture();
        return future;
    }

    public <T, E> CompletableFuture<T> getWithParams(String path, List<Param> params, String appid, String appsecret, Class<T> c, Class<E> er) {

        CompletableFuture<T> future = this.client
                .prepareGet(path)
                .addHeader("Authorization", "QUOTSAPP")
                .addHeader("app-id", appid)
                .addHeader("app-secret", appsecret)
                .addQueryParams(params)
                .execute(new AsyncHandler<T>() {

                    private Integer statusCode;
                    private InputStream sis;
                    io.netty.handler.codec.http.HttpHeaders headers;

                    @Override
                    public AsyncHandler.State onStatusReceived(HttpResponseStatus status) throws Exception {
                        statusCode = status.getStatusCode();
                        if (statusCode >= 400) {
                            return AsyncHandler.State.CONTINUE;
                        }
                        return AsyncHandler.State.CONTINUE;
                    }

                    @Override
                    public AsyncHandler.State onHeadersReceived(HttpHeaders h) throws Exception {
                        headers = h;
                        return AsyncHandler.State.CONTINUE;
                    }

                    @Override
                    public T onCompleted() throws Exception {
//                        ErrorReport er = serializer.parse(sis, er);
                        
                        return serializer.parse(sis, c);
                        
                    }

                    @Override
                    public void onThrowable(Throwable t) {
                        serializer.parse(sis, er);
                        throw new JQuotsException(t);
                    }

                    @Override
                    public AsyncHandler.State onBodyPartReceived(HttpResponseBodyPart httpResponseBodyPart) throws Exception {
                        if (sis == null) {
                            sis = new ByteArrayInputStream(httpResponseBodyPart.getBodyPartBytes(), 0, httpResponseBodyPart.length());
                        } else {
                            sis = new SequenceInputStream(sis, new ByteArrayInputStream(httpResponseBodyPart.getBodyPartBytes(), 0, httpResponseBodyPart.length()));
                        }
                        return AsyncHandler.State.CONTINUE;
                    }
                }).toCompletableFuture();
        return future;
    }

    public <T> CompletableFuture<T> post(String path, Object otu, String appid, String appsecret, Class<T> c) throws JQuotsException {
        CompletableFuture<T> future = this.client
                .preparePost(path)
                .setBody(serializer.write(otu))
                .addHeader("Authorization", "QUOTSAPP")
                .addHeader("app-id", appid)
                .addHeader("app-secret", appsecret)
                .execute(new AsyncHandler<T>() {

                    private Integer statusCode;
                    private InputStream sis;
                    io.netty.handler.codec.http.HttpHeaders headers;

                    @Override
                    public AsyncHandler.State onStatusReceived(HttpResponseStatus status) throws Exception {
                        statusCode = status.getStatusCode();
                        if (statusCode >= 400) {
                            return AsyncHandler.State.CONTINUE;
                        }
                        return AsyncHandler.State.CONTINUE;
                    }

                    @Override
                    public AsyncHandler.State onHeadersReceived(HttpHeaders h) throws Exception {
                        headers = h;
                        return AsyncHandler.State.CONTINUE;
                    }

                    @Override
                    public T onCompleted() throws Exception {
                        
                        return serializer.parse(sis, c);
                    }

                    @Override
                    public void onThrowable(Throwable t) {
                        
//                        System.out.println(serializer.parse(sis, ErrorReport.));
                        throw new JQuotsException(t);
                    }

                    @Override
                    public AsyncHandler.State onBodyPartReceived(HttpResponseBodyPart httpResponseBodyPart) throws Exception {
                        if (sis == null) {
                            sis = new ByteArrayInputStream(httpResponseBodyPart.getBodyPartBytes(), 0, httpResponseBodyPart.length());
                        } else {
                            sis = new SequenceInputStream(sis, new ByteArrayInputStream(httpResponseBodyPart.getBodyPartBytes(), 0, httpResponseBodyPart.length()));
                        }
                        return AsyncHandler.State.CONTINUE;
                    }
                }).toCompletableFuture();
//                .exceptionally(fn -> {
//                    ErrorReport er = new ErrorReport();
//                    er.setMessage("Could not create user");
//                    return er;
////                    return serializer.parse(sis, er);
//                });
        return future;
    }

    public <T> CompletableFuture<T> put(String path, Object otu, String appid, String appsecret, Class<T> c) {

        CompletableFuture<T> future = this.client
                .preparePut(path)
                .setBody(serializer.write(otu))
                .addHeader("Authorization", "QUOTSAPP")
                .addHeader("app-id", appid)
                .addHeader("app-secret", appsecret)
                .execute(new AsyncHandler<T>() {

                    private Integer statusCode;
                    private InputStream sis;
                    io.netty.handler.codec.http.HttpHeaders headers;

                    @Override
                    public AsyncHandler.State onStatusReceived(HttpResponseStatus status) throws Exception {
                        statusCode = status.getStatusCode();
                        if (statusCode >= 400) {
                            return AsyncHandler.State.ABORT;
                        }
                        return AsyncHandler.State.CONTINUE;
                    }

                    @Override
                    public AsyncHandler.State onHeadersReceived(HttpHeaders h) throws Exception {
                        headers = h;
                        return AsyncHandler.State.CONTINUE;
                    }

                    @Override
                    public T onCompleted() throws Exception {
                        return serializer.parse(sis, c);
                    }

                    @Override
                    public void onThrowable(Throwable t) {
                        throw new JQuotsException(t);
                    }

                    @Override
                    public AsyncHandler.State onBodyPartReceived(HttpResponseBodyPart httpResponseBodyPart) throws Exception {
                        if (sis == null) {
                            sis = new ByteArrayInputStream(httpResponseBodyPart.getBodyPartBytes(), 0, httpResponseBodyPart.length());
                        } else {
                            sis = new SequenceInputStream(sis, new ByteArrayInputStream(httpResponseBodyPart.getBodyPartBytes(), 0, httpResponseBodyPart.length()));
                        }
                        return AsyncHandler.State.CONTINUE;
                    }
                }).toCompletableFuture();
        return future;
    }

    public <T> CompletableFuture<T> delete(String path, Object otd, String appid, String appsecret, Class<T> c) {

        CompletableFuture<T> future = this.client
                .prepareDelete(path)
                .setBody(serializer.write(otd))
                .addHeader("Authorization", "QUOTSAPP")
                .addHeader("app-id", appid)
                .addHeader("app-secret", appsecret)
                .execute(new AsyncHandler<T>() {

                    private Integer statusCode;
                    private InputStream sis;
                    io.netty.handler.codec.http.HttpHeaders headers;

                    @Override
                    public AsyncHandler.State onStatusReceived(HttpResponseStatus status) throws Exception {
                        statusCode = status.getStatusCode();
//                        if (statusCode >= 400) {
//                            return AsyncHandler.State.CONTINUE;
//                        }
                        return AsyncHandler.State.CONTINUE;
                    }

                    @Override
                    public AsyncHandler.State onHeadersReceived(HttpHeaders h) throws Exception {
                        headers = h;
                        return AsyncHandler.State.CONTINUE;
                    }

                    @Override
                    public T onCompleted() throws Exception {
                        return serializer.parse(sis, c);
                    }

                    @Override
                    public void onThrowable(Throwable t) {
                        throw new JQuotsException(t);
                    }

                    @Override
                    public AsyncHandler.State onBodyPartReceived(HttpResponseBodyPart httpResponseBodyPart) throws Exception {
                        if (sis == null) {
                            sis = new ByteArrayInputStream(httpResponseBodyPart.getBodyPartBytes(), 0, httpResponseBodyPart.length());
                        } else {
                            sis = new SequenceInputStream(sis, new ByteArrayInputStream(httpResponseBodyPart.getBodyPartBytes(), 0, httpResponseBodyPart.length()));
                        }
                        return AsyncHandler.State.CONTINUE;
                    }
                }).toCompletableFuture();
        return future;
    }

}
