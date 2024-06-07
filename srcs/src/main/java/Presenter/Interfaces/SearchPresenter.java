package Presenter.Interfaces;

import Model.DataBaseModel;

public interface SearchPresenter
{
    public void onEnterKeyPress();
    public void onClickSearchButton();
    public void setRatedDataBaseModel(DataBaseModel dataBaseModel);
}