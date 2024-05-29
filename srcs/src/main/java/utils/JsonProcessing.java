package utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import retrofit2.Response;

public class JsonProcessing
{
    protected Gson gson;
    protected JsonObject query;

    public JsonProcessing()
    {
        gson = new Gson();
    }

    public JsonArray getQueryResults(Response<String> response, String identifier)
    {
        JsonObject jsonObject = jsonObjectSetUp(response);
        query = jsonQuery(jsonObject);
        return queryResults(identifier);
    }
    public JsonObject jsonObjectSetUp(Response<String> response)
    {
        return gson.fromJson(response.body(), JsonObject.class);
    }
    public JsonObject jsonQuery(JsonObject jsonObject)
    {
        query = jsonObject.get("query").getAsJsonObject();
        return query;
    }
    public JsonArray queryResults(String identifier)
    {
        return query.get(identifier).getAsJsonArray();
    }
}