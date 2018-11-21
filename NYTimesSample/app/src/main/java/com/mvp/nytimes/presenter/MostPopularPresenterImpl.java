package com.mvp.nytimes.presenter;

import com.mvp.nytimes.contract.AppContractMvp;
import com.mvp.nytimes.model.MostViewedModel;
import java.util.ArrayList;

/*
    This is the presenter class implementation. The presenter class in the mediator between the View
     and the Model. Once the View receives any User action, it forwards it to the Presenter.
     The presenter fetches the necessary data(In this case from the API) and assigns the fetched data
     to the adapter.
 */
public class MostPopularPresenterImpl implements AppContractMvp.MostPopularPresenter, AppContractMvp.GetMostPopularIntractor.OnFinishListener {

    //This is Presenter way of communicating with the View
    private AppContractMvp.MostPopularListView listView;
    //This is Presentor Fetching data from API and Creating Model list
    private AppContractMvp.GetMostPopularIntractor getMostPopularIntractor;

    public MostPopularPresenterImpl(AppContractMvp.MostPopularListView mainView, AppContractMvp.GetMostPopularIntractor getMostPopularIntractor) {
        this.listView = mainView;
        this.getMostPopularIntractor = getMostPopularIntractor;
    }

    @Override
    public void fetchDataUsingAPI() {
        if(listView!=null)
            listView.showProgressDialog();
        getMostPopularIntractor.getMostRecentArticles(this);
    }

    @Override
    public void onFinish(ArrayList<MostViewedModel> mostViewedList) {
        if(listView != null){
            listView.addDataToRecyclerView(mostViewedList);
            listView.hideProgressDialog();
        }
    }

    @Override
    public void onFailed(String msg, int statusCode) {
        if(listView != null){
            listView.onFailedResponse(msg,statusCode);
            listView.hideProgressDialog();
        }
    }
}
