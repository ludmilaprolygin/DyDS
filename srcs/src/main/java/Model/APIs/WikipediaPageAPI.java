package Model.APIs;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WikipediaPageAPI
{
  @GET("api.php?format=json&action=query&prop=extracts|info&exlimit=1&exintro=1&inprop=url")
  Call<String> getExtractByPageID(@Query("pageids") String term);
}