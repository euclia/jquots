package xyz.euclia.jquots;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import junit.framework.Assert;
import org.asynchttpclient.Response;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import xyz.euclia.jquots.models.CanProceed;
import xyz.euclia.jquots.models.ErrorReport;
import xyz.euclia.jquots.models.QuotsUser;
import xyz.euclia.jquots.serialize.Serializer;

/**
 *
 * @author pantelispanka
 */
public class JQuotsImplementetionIntTest {

    private static JQuots client;
    private static Serializer serializer;

    public JQuotsImplementetionIntTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        client = JQuotsClientFactory.createNewClient("http://localhost:8002",
                "jaqpot", "2KjV#NJaEZG&K2CC",
                new JQuotsSerializer(new ObjectMapper()));
        serializer = new JQuotsSerializer(new ObjectMapper());
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        client.close();
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @org.junit.Test
    public void testCreateUSer() throws InterruptedException, ExecutionException {
        System.out.println("Creating User");
        Future<Response> user = client.createUser("idrand", "username", "email");
        if (user.get().getStatusCode() == 409) {
            ErrorReport er = serializer.parse(user.get().getResponseBody(), ErrorReport.class);
            Assert.assertEquals(409, er.getStatus());
        } else {
            QuotsUser qu = serializer.parse(user.get().getResponseBody(), QuotsUser.class);
            Assert.assertEquals("idrand", qu.getId());
            Assert.assertEquals("username", qu.getUsername());
            Assert.assertEquals("email", qu.getEmail());
        }

    }

    @org.junit.Test
    public void testGetUser() throws InterruptedException, ExecutionException {
        System.out.println("Geting User");
        Future<Response> user = client.getUserById("idrand");
        if (user.get().getStatusCode() != 200) {
            ErrorReport er = serializer.parse(user.get().getResponseBody(), ErrorReport.class);
//            Assert.assertEquals(409, er.getStatus());

        } else {
            QuotsUser qu = serializer.parse(user.get().getResponseBody(), QuotsUser.class);
            Assert.assertEquals("idrand", qu.getId());
            Assert.assertEquals("username", qu.getUsername());
            Assert.assertEquals("email", qu.getEmail());
        }
    }

    @org.junit.Test
    public void testCanUserProceed() throws InterruptedException, ExecutionException {
        System.out.println("Checking proceed");
        Future<Response> proceed = client.canProceed("idrand", "GB", "10");

        if (proceed.get().getStatusCode() == 200) {
            CanProceed cp = serializer.parse(proceed.get().getResponseBody(), CanProceed.class);
            Assert.assertEquals("idrand", cp.getUserid());
            Assert.assertEquals(true, cp.isProceed());
        } else {
            ErrorReport er = serializer.parse(proceed.get().getResponseBody(), ErrorReport.class);
        }
    }

    @org.junit.Test
    public void updateUserCredits() throws InterruptedException, ExecutionException {
        System.out.println("Checking update user credits");
        QuotsUser qu = new QuotsUser();
        qu.setId("idrand");
        qu.setEmail("email");
        qu.setUsername("username");
        qu.setCredits(20);
        Future<Response> userUpdated = client.updateUserCredits(qu);
        if (userUpdated.get().getStatusCode() == 200) {
            QuotsUser quu = serializer.parse(userUpdated.get().getResponseBody(), QuotsUser.class);
            Assert.assertEquals(20.0, quu.getCredits(), 0.1);
        } else {
            ErrorReport er = serializer.parse(userUpdated.get().getResponseBody(), ErrorReport.class);
        }
    }

    @org.junit.Test
    public void deleteUser() throws InterruptedException, ExecutionException {
        System.out.println("Checking delete user");
        Future<Response> userDeleted = client.deleteUser("idrand");
        if (userDeleted.get().getStatusCode() == 410) {
            int del = serializer.parse(userDeleted.get().getResponseBody(), Integer.class);
            Assert.assertEquals(1, del);
        } else {
            ErrorReport er = serializer.parse(userDeleted.get().getResponseBody(), ErrorReport.class);
        }
    }

}
