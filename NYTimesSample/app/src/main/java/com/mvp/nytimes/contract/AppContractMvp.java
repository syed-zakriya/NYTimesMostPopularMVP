package com.mvp.nytimes.contract;

import com.mvp.nytimes.model.MostViewedModel;

import java.util.ArrayList;

public interface AppContractMvp {

    //The below interactor interface in used to preform API request to fetch the most popular articles from NYTimes
    interface GetMostPopularIntractor{
        interface OnFinishListener{
            void onFinish(ArrayList<MostViewedModel> mostViewedList);
            void onFailed(String msg, int code);
        }
        void getMostRecentArticles(OnFinishListener onFinishListener);
    }

    //handling the UI from Presenter
    interface MostPopularListView{
        void showToastMessage(String message, int statusCode);
        void showProgressDialog();
        void hideProgressDialog();
        void addDataToRecyclerView(ArrayList<MostViewedModel> list);
        void onFailedResponse(String message, int statusCode);
    }

    //Presenter fetching data from API, creating list of model object
    interface MostPopularPresenter{
        void fetchDataUsingAPI();
    }

}
