package Stubs;

import Model.APIModel;
import Model.PageModel;
import retrofit2.Response;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PageModelStub extends PageModel
{
    public void getPageFromWikipedia(String pageID)
    {
        response = mock(Response.class);
        when(response.body()).thenReturn("response.body()");
    }
}