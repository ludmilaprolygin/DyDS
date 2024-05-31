package Model;

import Presenter.Listeners.ModelListener;
import java.util.ArrayList;

public abstract class Model
{
    protected ArrayList<ModelListener> modelListeners;
    public Model() { modelListeners = new ArrayList<>(); }
    public void addListener(ModelListener listener) { modelListeners.add(listener); }

    protected void notifyListeners()
    {
        for(ModelListener modelListener : modelListeners)
            modelListener.taskFinished();
    }
}