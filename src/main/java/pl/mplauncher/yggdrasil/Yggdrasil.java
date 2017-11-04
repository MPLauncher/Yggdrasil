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
package pl.mplauncher.yggdrasil;

import retrofit2.Retrofit;
import retrofit2.adapter.java8.Java8CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Yggdrasil {

    public final static String URL = "https://authserver.mojang.com/";
    public final static String CONTENT_TYPE = "application/json";

    private final static YggdrasilService SERVICE = new Retrofit.Builder()
            .baseUrl(URL)
            .client(new YggdrasilClient())
            .addCallAdapterFactory(Java8CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(YggdrasilService.class);

    public static YggdrasilService service() {
        return SERVICE;
    }

}
