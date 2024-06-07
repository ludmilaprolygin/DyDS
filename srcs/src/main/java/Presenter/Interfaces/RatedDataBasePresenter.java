package Presenter.Interfaces;

import Presenter.Implemented.ShowSearchedPagePresenterImpl;


public interface RatedDataBasePresenter
{
    public void onClickRateButton();

    public void onClickRatedEntry();

    public void setShowSearchedPagePresenter
            (ShowSearchedPagePresenterImpl showSearchedPagePresenter);
}