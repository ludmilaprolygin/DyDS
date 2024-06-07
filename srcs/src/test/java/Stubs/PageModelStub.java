package Stubs;

import Model.APIModel;
import Model.PageModel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import retrofit2.Response;
import utils.JsonParsing;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PageModelStub extends PageModel
{
    protected Gson gson = new Gson();
    public void getPageFromWikipedia(String pageID)
    {
        JsonObject jsonObject = mock(JsonObject.class);

        response = mock(Response.class);
        when(JsonParsing.getQueryResultAsJsonObject(response, anyString()))
                .thenReturn(jsonObject);

        when(JsonParsing.getAttributeAsString(jsonObject, "pageid"))
                .thenReturn("111");
        when(JsonParsing.getAttributeAsString(jsonObject, "title"))
                .thenReturn("Title1");
    }
}