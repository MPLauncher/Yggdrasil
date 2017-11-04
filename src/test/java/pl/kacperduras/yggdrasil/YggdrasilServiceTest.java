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

import okhttp3.mockwebserver.MockWebServer;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.mplauncher.yggdrasil.YggdrasilClient;
import pl.mplauncher.yggdrasil.YggdrasilService;
import retrofit2.Retrofit;
import retrofit2.adapter.java8.Java8CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class YggdrasilServiceTest {

    private final static MockWebServer server = new MockWebServer();

    private static YggdrasilService service;

    @BeforeEach
    public static void setup() throws IOException {
        server.setDispatcher(new YggdrasilDispatcher());
        server.start();

        service = new Retrofit.Builder()
                .baseUrl(server.url("/"))
                .client(new YggdrasilClient())
                .addCallAdapterFactory(Java8CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(YggdrasilService.class);
    }

    @Test
    public void testAuthenticate() {
        Assert.fail();
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

    @AfterEach
    public static void destroy() throws IOException {
        service = null;

        server.close();
    }


}
