package Presenter.Interfaces;

import Model.DataBaseModel;

public interface ShowSearchedPagePresenter
{
    public void onSelectedSearchResult();

    public void showPageContent();

    public void setDataBaseModel(DataBaseModel dataBaseModel);
}