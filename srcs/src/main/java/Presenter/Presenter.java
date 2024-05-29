package Presenter;

import Model.Model;
import View.View;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import retrofit2.Response;

public abstract class Presenter
{
    protected Gson gson;
    protected JsonObject query;

    public Presenter()
    {
        gson = new Gson();
    }

    protected JsonArray getQueryResults(Response<String> response, String identifier)
    {
        JsonObject jsonObject = jsonObjectSetUp(response);
        query = jsonQuery(jsonObject);
        return queryResults(identifier);
    }
    protected JsonObject jsonObjectSetUp(Response<String> response)
    {
        return gson.fromJson(response.body(), JsonObject.class);
    }
    protected JsonObject jsonQuery(JsonObject jsonObject)
    {
        query = jsonObject.get("query").getAsJsonObject();
        return query;
    }
    protected JsonArray queryResults(String identifier)
    {
        return query.get(identifier).getAsJsonArray();
    }
}