package com.example.altimetrikproject.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.altimetrikproject.database.InformationDBManager;
import com.example.altimetrikproject.R;
import com.example.altimetrikproject.common.RequestBuilder;
import com.example.altimetrikproject.common.Utils;
import com.example.altimetrikproject.interfaces.IinformationActivity;
import com.example.altimetrikproject.interfaces.IinformationPresenter;
import com.example.altimetrikproject.interfaces.ListAdapterListener;
import com.example.altimetrikproject.pojo.Details;
import com.example.altimetrikproject.presenter.InformationPresenter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

public class DisplayListActivity extends BaseActivity implements IinformationActivity, ListAdapterListener {

    private OkHttpClient client;

    private SearchView searchView;
    private RecyclerView recyclerView;
    private IinformationPresenter presenter;
    private List<Details> detailsArrayList = new ArrayList<>();
    private CustomAdapter adapter;
    private ProgressBar progressBar;
    private boolean isScrolling = false;
    private int currentItem, totalItem, scrolledOutItem;
    private LinearLayoutManager linearLayoutManager;
    private List<Details> detailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        if (Utils.isInternetAvailable(DisplayListActivity.this)) {
            setProgressDialog(true);
            loadContent();

        } else {
            showNoNetworkAlert(new AlertDialog.AlertDialogListener() {
                @Override
                public void onPositiveButtonClicked() {
                    finish();
                }

                @Override
                public void onNegativeButtonClicked() {

                }
            });
        }

        initializePresenter();

    }

    private void initializePresenter() {
        presenter = new InformationPresenter(this);
        presenter.setList();
    }

    private void init() {
        client = new OkHttpClient();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(R.string.LIST);
        recyclerView = findViewById(R.id.recycler_view);

        progressBar = findViewById(R.id.progress);
        linearLayoutManager = new LinearLayoutManager(DisplayListActivity.this);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItem = linearLayoutManager.getChildCount();
                totalItem = linearLayoutManager.getItemCount();
                scrolledOutItem = linearLayoutManager.findFirstVisibleItemPosition();

                boolean islast = totalItem > 2 && (scrolledOutItem + currentItem) >= totalItem;
                if (isScrolling && islast) {
                    isScrolling = false;
                    fetchNextData();
                }
            }
        });
    }

    private void fetchNextData() {
        progressBar.setVisibility(View.VISIBLE);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                detailsArrayList.remove(detailsArrayList.size() - 1);
                int scrollPosition = detailsArrayList.size();
                adapter.notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;
                int nextLimit = currentSize + 10;

                while (currentSize - 1 <= nextLimit) {
                    detailsArrayList.add(new Details(detailList.get(currentSize).getmTitle(), detailList.get(currentSize).getmBlurp(), detailList.get(currentSize).getmPleadge(),
                            detailList.get(currentSize).getmCountry(), detailList.get(currentSize).getmLocation(), detailList.get(currentSize).getmBy()));
                    currentSize++;
                }
                adapter.notifyDataSetChanged();

                progressBar.setVisibility(View.GONE);
            }
        }, 500);
    }

    private void loadContent() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    String response = Utils.GET(client, RequestBuilder.buildURL());
                    Log.d("Response", response);

                    InformationDBManager informationDBManager = new InformationDBManager(DisplayListActivity.this);
                    try {
                        informationDBManager.open();
                        informationDBManager.deleteAllRecords();

                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String title = object.getString("title");
                            String blurb = object.getString("blurb");
                            String pleadge = object.getString("amt.pledged");
                            String country = object.getString("country");
                            String location = object.getString("location");
                            String by = object.getString("by");

                            informationDBManager.insert(title, blurb, pleadge, country, location, by);
                        }
                        informationDBManager.close();

                    } catch (Exception e) {
                        informationDBManager.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                setProgressDialog(false);
                if (Utils.response_code == 200) {
                    adapter = new CustomAdapter(detailsArrayList, DisplayListActivity.this);

                    recyclerView.setAdapter(adapter);

                    recyclerView.setLayoutManager(linearLayoutManager);
                } else {
                    showRetryDialog(new AlertDialog.AlertDialogListener() {
                        @Override
                        public void onPositiveButtonClicked() {
                            setProgressDialog(true);
                            loadContent();
                        }

                        @Override
                        public void onNegativeButtonClicked() {

                        }
                    });
                }
            }
        }.execute();
    }

    @Override
    public void setList(List<Details> detailList) {

        int i = 0;
        while (i < 10) {
            detailsArrayList.add(new Details(detailList.get(i).getmTitle(), detailList.get(i).getmBlurp(), detailList.get(i).getmPleadge(),
                    detailList.get(i).getmCountry(), detailList.get(i).getmLocation(), detailList.get(i).getmBy()));
            i++;
        }

        this.detailList = detailList;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                adapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onSelected(Details details) {
        Intent intent = new Intent(DisplayListActivity.this, DescriptionActivity.class);
        intent.putExtra("title",details.getmTitle());
        intent.putExtra("blurb",details.getmBlurp());
        intent.putExtra("pleadge",details.getmPleadge());
        intent.putExtra("country",details.getmCountry());
        intent.putExtra("location",details.getmLocation());
        intent.putExtra("by",details.getmBy());
        intent.putExtra("backers",details.getmBacker());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
}
