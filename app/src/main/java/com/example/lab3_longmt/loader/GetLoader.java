package com.example.lab3_longmt.loader;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab3_longmt.adapter.RecycleViewAdapter;
import com.example.lab3_longmt.model.Detail;
import com.example.lab3_longmt.model.Item;
import com.example.lab3_longmt.model.ListData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GetLoader extends AsyncTask<String, Integer, List<String>> {
    private RecyclerView rvList;
    private Context context;
    private ProgressDialog mProgressDialog;

    public GetLoader(RecyclerView rvList, Context context) {
        this.rvList = rvList;
        this.context = context;
        mProgressDialog = new ProgressDialog(context);
    }

    @Override
    protected List<String> doInBackground(String... strings) {
        List<String> postDetailList = new ArrayList<>();
        String link = strings[0];
        String data = "";
        try {
            URL url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            //7 cach lay inputStream
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNext()) {
                data += scanner.nextLine();
            }
            scanner.close();
            inputStream.close();
            httpURLConnection.disconnect();

            Gson gson = new Gson();
            ArrayList<Item> arrayList = gson.fromJson(data, new TypeToken<ArrayList<Item>>() {
            }.getType());

            for (int i = 0; i < arrayList.size(); i++) {
                String data1 = "";
                String xLink = arrayList.get(i).getLinks().getSelf().get(0).getHref();
                URL href = new URL(xLink);

                HttpURLConnection urlConnection = (HttpURLConnection) href.openConnection();
                InputStream inP = urlConnection.getInputStream();
                Scanner sc = new Scanner(inP);
                while (sc.hasNext()) {
                    data1 += sc.nextLine();
                }
                sc.close();
                inP.close();
                urlConnection.disconnect();
                postDetailList.add(data1);
                Log.e("arrayList", xLink);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return postDetailList;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog.setMessage("Loading ...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

    }

    @Override
    protected void onPostExecute(List<String> s) {
        super.onPostExecute(s);
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        List<ListData> dataList = new ArrayList<>();
        for (int i = 0; i < s.size(); i++) {
            Gson gson = new Gson();
            Detail detail = gson.fromJson(s.get(i), Detail.class);
            dataList.add(new ListData(detail.getTitle().getRendered(), detail.getDate(), detail.getContent().getRendered(), detail.getExcerpt().getRendered(), detail.getLink()));
        }
        rvList.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        rvList.setLayoutManager(layoutManager);

        RecycleViewAdapter mAdapter = new RecycleViewAdapter(dataList, context);
        rvList.setAdapter(mAdapter);

    }

}
