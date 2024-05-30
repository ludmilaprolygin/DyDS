package utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import retrofit2.Response;
import java.util.Map;
import java.util.Set;

public class JsonProcessing
{
    protected static final Gson gson = new Gson();
    protected static JsonObject query;

    public static JsonArray getQueryResultsAsJsonArray(Response<String> response, String identifier)
    {
        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
        query = jsonObject.get("query").getAsJsonObject();
        return query.get(identifier).getAsJsonArray();
    }
    public static JsonObject getQueryResultAsJsonObject(Response<String> response, String identifier)
    {
        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
        query = jsonObject.get("query").getAsJsonObject();

        JsonObject pages = query.get(identifier).getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> pagesSet = pages.entrySet();
        Map.Entry<String, JsonElement> first = pagesSet.iterator().next();
        return first.getValue().getAsJsonObject();
    }
}