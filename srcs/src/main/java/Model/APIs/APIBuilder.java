package Model.APIs;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class APIBuilder
{
    protected final static Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("https://en.wikipedia.org/w/")
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .build();

    public static WikipediaSearchAPI createSearchAPI() { return retrofit.create(WikipediaSearchAPI.class); }
    public static WikipediaPageAPI createPageAPI() { return retrofit.create(WikipediaPageAPI.class); }
}