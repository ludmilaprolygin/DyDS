package Model;

import Presenter.Listeners.ModelListener;
import retrofit2.Response;

public abstract class Model
{
    protected Response<String> response;
    protected ModelListener modelListener;

    public Response<String> getResponse() { return response; }
    public void addListener(ModelListener listener) { modelListener = listener; }
}