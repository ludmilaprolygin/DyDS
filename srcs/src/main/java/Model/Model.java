package Model;

import Model.Listeners.ModelListener;
import java.util.ArrayList;

public abstract class Model
{
    protected ArrayList<ModelListener> modelListeners;

    public Model()
        { modelListeners = new ArrayList<ModelListener>(); }

    public void addListener(ModelListener listener)
        { modelListeners.add(listener);}

    public ArrayList<ModelListener> getListeners()
        { return modelListeners; }
}