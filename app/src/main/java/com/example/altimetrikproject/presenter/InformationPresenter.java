package com.example.altimetrikproject.presenter;

import com.example.altimetrikproject.interfaces.IinformationActivity;
import com.example.altimetrikproject.interfaces.IinformationPresenter;
import com.example.altimetrikproject.model.InformationModel;
import com.example.altimetrikproject.pojo.Details;

import java.util.List;

public class InformationPresenter implements IinformationPresenter {


    private IinformationActivity iInformationActivity;
    private InformationModel iModel;

    public InformationPresenter(IinformationActivity iInformationActivity) {
        this.iInformationActivity = iInformationActivity;
        iModel = new InformationModel(iInformationActivity);

    }

    @Override
    public void setList() {
        List<Details> detailArrayList = iModel.getListFromDatabase();
        iInformationActivity.setList(detailArrayList);

    }
}
