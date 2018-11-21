package com.mvp.nytimes;

import com.mvp.nytimes.contract.AppContractMvp;
import com.mvp.nytimes.presenter.MostPopularPresenterImpl;
import com.mvp.nytimes.contract.AppContractMvp.MostPopularListView;
import com.mvp.nytimes.contract.AppContractMvp.GetMostPopularIntractor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class MostPopularPresenterImplTest {

    private MostPopularPresenterImpl mostpopularPresenter;

    @Mock
    MostPopularListView mainView;

    @Mock
    AppContractMvp.GetMostPopularIntractor getMostPopularIntractor;

    @Mock
    AppContractMvp.GetMostPopularIntractor.OnFinishListener finishListener;

    @Before
    public void setup() throws Exception
    {
        MockitoAnnotations.initMocks(this);// required for the "@Mock" annotations

        // Make presenter a mock while using mock interactor and viewContract created above
        mostpopularPresenter = Mockito.spy(new MostPopularPresenterImpl(mainView, getMostPopularIntractor));
    }

    @Test
    public void makeAPICall() {
        // Trigger
        mostpopularPresenter.fetchDataUsingAPI();
        // Validation
        Mockito.verify(mostpopularPresenter, Mockito.timeout(2000)).fetchDataUsingAPI();
        Mockito.verify(mostpopularPresenter, Mockito.never()).onFinish(null);

    }

}
