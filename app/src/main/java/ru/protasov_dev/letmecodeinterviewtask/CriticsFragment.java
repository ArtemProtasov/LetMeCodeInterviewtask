package ru.protasov_dev.letmecodeinterviewtask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class CriticsFragment extends Fragment implements ParseTaskCritics.MyCustomCallBack{

    EditText nameCritics;
    ListView listCritics;
    public ArrayAdapter<String> adapter;

    private final String URL = "https://api.nytimes.com/svc/movies/v2/critics/all.json";
    public String url;

    public ParseTaskThree parseTaskThree;

    private List<ResultCritics> results;
    public String titles[];

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.critics_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        nameCritics = getView().findViewById(R.id.criticName);
        listCritics = getView().findViewById(R.id.list_critics);

        url = URL + "?" + "api-key=" + getString(R.string.api_key_nyt); //формируем URL (тут можем задать дополнительные параметры)

        //Вызываем парсес
        ParseTaskCritics parseTaskCritics = new ParseTaskCritics(this, url);
        parseTaskCritics.execute();

    }

    private void getCritics(){
        url = URL + "?" + "api-key=" + getString(R.string.api_key_nyt); //формируем URL (тут можем задать дополнительные параметры)

        //Вызываем парсес
        ParseTaskCritics parseTaskCritics = new ParseTaskCritics(this, url);
        parseTaskCritics.execute();
    }

    @Override
    public void doSomething(String strJson) {
        // выводим целиком полученную json-строку
        Log.d("JSON: ", strJson);

        //С помощью Gson будем разбирать json на составляющие
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        //Заполняем ParseTaskTwo нашими данными из JSON
        parseTaskThree = gson.fromJson(strJson, ParseTaskThree.class);

        //В List получаем наш Result, основное, с чем будем работать
        results = parseTaskThree.getResults();

        //Тут извлекаем заголовки (Titles) в массив
        titles = new String[results.size()];
        for (int i = 0; i < results.size(); i++) {
            titles[i] = results.get(i).getDisplayName();
        }

        //И присваиваем адаптеру
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, titles);

        //А в ListReviews устанавливаем адаптер
        listCritics.setAdapter(adapter);
    }
}
