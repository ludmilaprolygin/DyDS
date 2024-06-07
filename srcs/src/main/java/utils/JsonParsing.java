package utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import retrofit2.Response;
import java.util.Map;
import java.util.Set;

public class JsonParsing
{
    protected static final Gson gson = new Gson();

    public static JsonArray getQueryResultsAsJsonArray(Response<String> response, String identifier)
    {
        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
        JsonObject query = jsonObject.get("query").getAsJsonObject();
        return query.get(identifier).getAsJsonArray();
    }
    public static JsonObject getQueryResultAsJsonObject(Response<String> response, String identifier)
    {
        JsonObject query = getQuery(response);

        JsonObject pages = query.get(identifier).getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> pagesSet = pages.entrySet();
        Map.Entry<String, JsonElement> first = pagesSet.iterator().next();
        return first.getValue().getAsJsonObject();
    }
    public static String getAttributeAsString(JsonObject jsonObject, String attribute)
        { return jsonObject.get(attribute).getAsString(); }
    protected static JsonObject getQuery(Response<String> response)
    {
        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
        return jsonObject.get("query").getAsJsonObject();
    }
}