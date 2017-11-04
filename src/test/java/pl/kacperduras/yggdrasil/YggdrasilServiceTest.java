/*
   Copyright 2017 MPLauncher Team

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package pl.kacperduras.yggdrasil;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.mplauncher.yggdrasil.YggdrasilClient;
import pl.mplauncher.yggdrasil.YggdrasilService;
import retrofit2.Retrofit;
import retrofit2.adapter.java8.Java8CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class YggdrasilServiceTest {

    private final static MockWebServer server = new MockWebServer();

    private static YggdrasilService service;

    @BeforeEach
    public void setup() throws IOException {
        server.setDispatcher(new YggdrasilDispatcher());
        server.start();

        service = new Retrofit.Builder()
                .baseUrl(server.url("/"))
                .client(new YggdrasilClient())
                .addCallAdapterFactory(Java8CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(YggdrasilService.class);
    }

    @AfterEach
    public void destroy() throws IOException {
        service = null;

        server.close();
    }

    @Test
    public void testAuthenticate() {
        JsonObject payload = new JsonObject();
        payload.addProperty("username", "Notch");
        payload.addProperty("password", "password");

        JsonObject agent = new JsonObject();
        agent.addProperty("name", "Minecraft");
        agent.addProperty("version", 1);

        payload.add("agent", agent);

        CompletableFuture<JsonObject> result = service.authenticate(payload);
        CompletableFuture.allOf(result).join();

        JsonObject object = result.join();
        this.validateAuthenticate(object);
    }

    public void validateAuthenticate(JsonObject result) {
        Assert.assertNotNull(result);
        JsonObject user = result.get("user").getAsJsonObject();

        Assert.assertEquals("token", result.get("accessToken").getAsString());
        Assert.assertEquals("token", result.get("clientToken").getAsString());
        Assert.assertEquals(1, result.get("availableProfiles").getAsJsonArray().size());

        Assert.assertEquals("id", user.get("id").getAsString());
        Assert.assertEquals(2, user.get("properties").getAsJsonArray().size());
    }

    @Test
    public void testRefresh() {
        Assert.fail();
    }

    @Test
    public void testValidate() {
        Assert.fail();
    }

    @Test
    public void testSignout() {
        Assert.fail();
    }

    @Test
    public void testInvalidate() {
        Assert.fail();
    }

}
