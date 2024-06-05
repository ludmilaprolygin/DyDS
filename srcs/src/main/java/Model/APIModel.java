package Model;

import Presenter.Listeners.ModelListener;
import retrofit2.Response;

public abstract class APIModel extends Model
{
    protected Response<String> response;
    public Response<String> getResponse() { return response; }
}