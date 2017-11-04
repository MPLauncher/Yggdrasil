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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

import java.io.InputStreamReader;

final class YggdrasilDispatcher extends Dispatcher {

    private final Gson gson = new Gson();

    @Override
    public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
        String path = request.getPath();

        JsonObject body = gson.fromJson(new InputStreamReader(request.getBody().inputStream()), JsonObject.class);
        JsonObject response = new JsonObject();

        if (path.equals("/authenticate")) {
            return this.authenticate(request, body);
        }

        if (path.equals("/refresh")) {
            return this.refresh(request, body);
        }

        if (path.equals("/validate")) {
            return this.validate(request, body);
        }

        if (path.equals("/signout")) {
            return this.signout(request, body);
        }

        if (path.equals("/invalidate")) {
            return this.invalidate(request, body);
        }

        response.addProperty("error", "Not Found");
        response.addProperty("errorMessage", "The server has not found anything matching the request URI");

        return new MockResponse()
                .setResponseCode(404)
                .setBody(gson.toJson(response));
    }

    private MockResponse authenticate(RecordedRequest request, JsonObject body) {
        JsonObject result = new JsonObject();
        result.addProperty("accessToken", "token");
        result.addProperty("clientToken", "token");

        JsonArray profiles = new JsonArray();

        JsonObject profile = new JsonObject();
        profile.addProperty("id", "id");
        profile.addProperty("name", body.get("username").getAsString());

        profiles.add(profile);
        result.add("availableProfiles", profiles);

        JsonObject user = new JsonObject();
        user.addProperty("id", "id");

        JsonArray properties = new JsonArray();

        JsonObject langProperties = new JsonObject();
        langProperties.addProperty("name", "preferredLanguage");
        langProperties.addProperty("value", "en");

        JsonObject twitchProperties = new JsonObject();
        twitchProperties.addProperty("name", "token");
        twitchProperties.addProperty("value", "token");

        properties.add(langProperties);
        properties.add(twitchProperties);

        user.add("properties", properties);

        result.add("user", user);

        return new MockResponse()
                .setResponseCode(200)
                .setBody(gson.toJson(result));
    }

    private MockResponse refresh(RecordedRequest request, JsonObject body) {
        JsonObject result = new JsonObject();
        result.addProperty("accessToken", "token");
        result.addProperty("clientToken", "token");

        JsonObject profile = new JsonObject();
        profile.addProperty("id", "id");
        profile.addProperty("name", body.get("selectedProfile")
                .getAsJsonObject()
                .get("name")
                .getAsString());

        result.add("selectedProfile", profile);

        JsonObject user = new JsonObject();
        user.addProperty("id", "id");

        JsonArray properties = new JsonArray();

        JsonObject langProperties = new JsonObject();
        langProperties.addProperty("name", "preferredLanguage");
        langProperties.addProperty("value", "en");

        JsonObject twitchProperties = new JsonObject();
        twitchProperties.addProperty("name", "token");
        twitchProperties.addProperty("value", "token");

        properties.add(langProperties);
        properties.add(twitchProperties);

        user.add("properties", properties);

        result.add("user", user);

        return new MockResponse()
                .setResponseCode(200)
                .setBody(gson.toJson(result));
    }

    private MockResponse validate(RecordedRequest request, JsonObject body) {
        return new MockResponse()
                .setResponseCode(200) // TODO: 204 No Content (AssertionError)
                .setBody("{}");
    }

    private MockResponse signout(RecordedRequest request, JsonObject body) {
        return null;
    }

    private MockResponse invalidate(RecordedRequest request, JsonObject body) {
        return null;
    }

}
