/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.euclia.jquots;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.AsyncHttpClientConfig;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import xyz.euclia.jquots.consumer.QuotsConsumer;
import xyz.euclia.jquots.serialize.Serializer;

/**
 *
 * @author pantelispanka
 */
public class JQuotsClientFactory {

    public static JQuots createNewClient(String basePath, String appid, String appSecret, Serializer serializer) {
        AsyncHttpClient httpClient = ClientFactory.INSTANCE.getClient();
        QuotsConsumer quotsConsumer = new QuotsConsumer(httpClient, serializer, basePath, appid, appSecret);
        JQuots client = new JQuotsImplementation(httpClient, appid, appSecret, quotsConsumer);
        return client;
    }

    private enum ClientFactory {

        INSTANCE;

        private DefaultAsyncHttpClient s;

        ClientFactory() {
            AsyncHttpClientConfig config = new DefaultAsyncHttpClientConfig.Builder()
                    .setPooledConnectionIdleTimeout(500)
                    .setMaxConnections(20000)
//                    .setAcceptAnyCertificate(true)
                    .setMaxConnectionsPerHost(5000).build();

            s = new DefaultAsyncHttpClient(config);

        }

        public AsyncHttpClient getClient() {
            return s;
        }
    }

    public AsyncHttpClient getClient() {
        return ClientFactory.INSTANCE.getClient();
    }

}
