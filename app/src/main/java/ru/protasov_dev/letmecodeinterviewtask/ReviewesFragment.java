package ru.protasov_dev.letmecodeinterviewtask;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ru.protasov_dev.letmecodeinterviewtask.Adapters.MyCustomAdapterReviewes;

public class ReviewesFragment extends Fragment implements ParseTaskReviewes.MyCustomCallBack, SwipeRefreshLayout.OnRefreshListener {

    private EditText keywords;
    private EditText date;
    RecyclerView.LayoutManager layoutManager;

    public ParseTaskTwo parseTaskTwo;
    private List<Result> results;
    public String titles[];

    private Calendar Date = Calendar.getInstance();

    private String dateForSearch;
    private String keywordForSearch;

    private final String URL = "https://api.nytimes.com/svc/movies/v2/reviews/search.json";
    public String url;

    private List<ReviewesElement> list;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.reviewes_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        keywords = getView().findViewById(R.id.keyword);
        date = getView().findViewById(R.id.data);
        date.setInputType(InputType.TYPE_NULL); //Не выводим клавиатуру

        url = URL + "?" + "api-key=" + getString(R.string.api_key_nyt); //формируем URL (тут можем задать дополнительные параметры)

        //Вызываем парсес
        ParseTaskReviewes parseTaskReviewes = new ParseTaskReviewes(this, url);
        parseTaskReviewes.execute();

        //При клике на поле ввода даты - отображаем диалог выбора даты
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), d, Date.get(Calendar.YEAR), Date.get(Calendar.MONTH), Date.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });

        swipeRefreshLayout = getView().findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getReviews();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 4000);
    }

    //Установка даты
    private void setInitialDate() {
        //Преобразуем с помощью SimpleDateFormat дату в миллисекундах в следующий формат: ГОД/МЕСЯЦ/ДЕНЬ (так задано в ТЗ)
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        //И сразу устанавливаем в поле ввода форматированную дату
        dateForSearch = formatter.format(Date.getTimeInMillis());
        date.setText(dateForSearch);
        //Выполняем поиск при выборе даты
        getReviews();

    }

    //Установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Date.set(Calendar.YEAR, year);
            Date.set(Calendar.MONTH, monthOfYear);
            Date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDate();
        }
    };

    private void getReviews() {

        url = URL + "?" + "api-key=" + getString(R.string.api_key_nyt); //формируем URL (тут можем задать дополнительные параметры)

        //Вызываем парсес
        ParseTaskReviewes parseTaskReviewes = new ParseTaskReviewes(this, url);
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

        //Тут извлекаем заголовки (Titles) в массив
        titles = new String[results.size()];
        for (int i = 0; i < results.size(); i++) {
            titles[i] = results.get(i).getDisplayTitle();
        }

        RecyclerView recyclerView = getView().findViewById(R.id.recycler_reviews);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        MyCustomAdapterReviewes adapterReviewes = new MyCustomAdapterReviewes(initData());
        recyclerView.setAdapter(adapterReviewes);
    }

    private List<ReviewesElement> initData() {
        list = new ArrayList<>();
        String dateAndTime;
        for (int i = 0; i < results.size(); i++) {
            //Преобразуем дату и время в следующий формат: ГОД/МЕСЯЦ/ДЕНЬ ЧАС:МИНУТА:СЕКУНДА (так задано в ТЗ)
            dateAndTime = results.get(i).getDateUpdated().replace("-", "/");
            list.add(new ReviewesElement(results.get(i).getDisplayTitle(), results.get(i).getSummaryShort(), dateAndTime, results.get(i).getByline(), results.get(i).getMultimedia().getSrc()));
        }
        return list;
    }

}
