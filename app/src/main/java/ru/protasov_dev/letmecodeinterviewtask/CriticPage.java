package ru.protasov_dev.letmecodeinterviewtask;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

import ru.protasov_dev.letmecodeinterviewtask.Adapters.MyCustomAdapterReviewes;

public class CriticPage extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, ParseTaskReviewes.MyCustomCallBack{
    private SwipeRefreshLayout refreshLayout;
    private String URL;

    private int offset = 0;
    private ParseTaskTwo parseTaskTwo;
    private List<Result> results;
    private List<ReviewesElement> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_critic_page);

        Intent intent = getIntent();
        URL = intent.getStringExtra("URL");
        String name = intent.getStringExtra("NAME");
        String status = intent.getStringExtra("STATUS");
        String bio = intent.getStringExtra("BIO");
        Bitmap img = intent.getParcelableExtra("IMG");

        //Устанавливаем наш кастомный тулбар
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle(name);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView nameCritic = findViewById(R.id.txt_name);
        nameCritic.setText(name);
        TextView statusCritic = findViewById(R.id.txt_status);
        statusCritic.setText(status);
        ImageView imageView = findViewById(R.id.img_photo);
        imageView.setImageBitmap(img);
        TextView bioCritic = findViewById(R.id.txt_bio);
        bioCritic.setText(bio.replace("<br/>", " ")); //У A. O. Scott встречаются html теги в БИО. Заменю на пробелы

        ImageButton nextPage = findViewById(R.id.next_critic_page_post);
        ImageButton prevPage = findViewById(R.id.prev_critic_page_post);


        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                offset += 20;
                URL += "&offset=" + offset;
                getCriticPosts();
            }
        });

        prevPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(offset >= 20){
                    offset -= 20;
                    URL += "&offset=" + offset;
                    getCriticPosts();
                }
            }
        });


        refreshLayout = findViewById(R.id.swipe_container);
        //Устанавливаем слушатель и какими цветами будет переливаться кружочек на
        //Swipe-to-refresh
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        getCriticPosts(); //При запуске активити прогружаем посты


    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Если "потянули" вниз, то обновляем
                getCriticPosts();
                refreshLayout.setRefreshing(false);
            }
        }, 4000);
    }

    private void getCriticPosts(){
        //Если лист и результаты не нулл, то очищаем их
        if(list != null && results != null) {
            list.clear();
            results.clear();
        }

        //Вызываем парсес
        ParseTaskReviewes parseTaskReviewes = new ParseTaskReviewes(this, URL);
        parseTaskReviewes.execute();
    }

    @Override
    public void doSomething(String strJson) {
        // выводим целиком полученную json-строку
        Log.d("JSON: ", strJson);

        //С помощью Gson будем разбирать json на составляющие
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        //Заполняем ParseTaskTwo нашими данными из JSON
        parseTaskTwo = gson.fromJson(strJson, ParseTaskTwo.class);

        //В List получаем наш Result, основное, с чем будем работать
        results = parseTaskTwo.getResults();

        RecyclerView recyclerView = findViewById(R.id.recycler_critic_page);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        MyCustomAdapterReviewes adapterReviewes = new MyCustomAdapterReviewes(initData());
        recyclerView.setAdapter(adapterReviewes);
    }

    private List<ReviewesElement> initData() {
        list = new ArrayList<>();
        String dateAndTime;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
        for (int i = 0; i < results.size(); i++) {
            //Преобразуем дату и время в следующий формат: ГОД/МЕСЯЦ/ДЕНЬ ЧАС:МИНУТА:СЕКУНДА (так задано в ТЗ)
            //Тут я установил дату публикации. Если нужна дата обновления статьи, то
            //пишем за место getPublicationDate() -> getDateUpdated()
            //если нужно дата открытия, то -> getOpeningDate
            dateAndTime = results.get(i).getPublicationDate().replace("-", "/");
            try {
                list.add(new ReviewesElement(results.get(i).getDisplayTitle(), results.get(i).getSummaryShort(), dateAndTime, results.get(i).getByline(), results.get(i).getMultimedia().getSrc(), imageLoader));
            } catch (NullPointerException e){
                list.add(new ReviewesElement(results.get(i).getDisplayTitle(), results.get(i).getSummaryShort(), dateAndTime, results.get(i).getByline(), getString(R.string.scr_find), imageLoader));
            }
        }
        return list;
    }
}
