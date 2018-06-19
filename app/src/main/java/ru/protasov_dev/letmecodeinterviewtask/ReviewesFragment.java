package ru.protasov_dev.letmecodeinterviewtask;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ReviewesFragment extends Fragment implements ParseTask.MyCustomCallBack{

    private EditText keywords;
    private EditText date;
    private ListView listReviews;
    public ArrayAdapter<String> adapter;

    public ParseTaskTwo parseTaskTwo;
    private List<Result> results;
    public String titles[];

    private Calendar Date = Calendar.getInstance();

    private String dateForSearch;
    private String keywordForSearch;

    private final String URL = "https://api.nytimes.com/svc/movies/v2/reviews/search.json";
    public String url;

    ProgressDialog progressDialog;

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
        listReviews = getView().findViewById(R.id.list_reviews);

        //При клике на поле ввода даты - отображаем диалог выбора даты
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), d, Date.get(Calendar.YEAR), Date.get(Calendar.MONTH), Date.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });
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
        //progressDialog = ProgressDialog.show(getContext(), "Please, wait...", "Download reviews", true, false);

        url = URL + "?" + "api-key=" + getString(R.string.api_key_nyt);

        ParseTask parseTask = new ParseTask(this, url);
        parseTask.execute();
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

        results = parseTaskTwo.getResults();

        titles = new String[20];
        for (int i = 0; i < results.size(); i++) {
            titles[i] = results.get(i).getDisplayTitle();
        }

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, titles);

        listReviews.setAdapter(adapter);
    }
}
