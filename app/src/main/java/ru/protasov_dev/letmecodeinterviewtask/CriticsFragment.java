package ru.protasov_dev.letmecodeinterviewtask;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import ru.protasov_dev.letmecodeinterviewtask.Adapters.MyCustomAdapterCritics;

public class CriticsFragment extends Fragment implements ParseTaskCritics.MyCustomCallBack, SwipeRefreshLayout.OnRefreshListener{

    private EditText nameCritics;
    private ImageButton clearNameCritics;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView.LayoutManager layoutManager;
    private List<CriticsElement> list;
    public ParseTaskThree parseTaskThree;
    private List<ResultCritics> results;
    public String url;

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
        swipeRefreshLayout = getView().findViewById(R.id.swipe_container_critics);
        clearNameCritics = getView().findViewById(R.id.clear_critics_name);

        //При нажатии на кнопку "Очистки" поля - очищаем поле и проводим новый поиск
        clearNameCritics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameCritics.setText(null);
                getReviews();
            }
        });

        //При нажатии Enter производим поиск по ключевым словам
        nameCritics.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    getReviews();
                    return true;
                }
                return false;
            }
        });

        getReviews(); //При запуске фрагмента прогружаем посты

        //Устанавливаем слушатель и какими цветами будет переливаться кружочек на
        //Swipe-to-refresh
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void createURL(){
        //Основной URL имеет вид:
        //https://api.nytimes.com/svc/movies/v2/critics/all.json?api-key=020eb74eff674e3da8aaa1e8e311edda
        url = getString(R.string.main_url_critics) + "?api-key=" + getString(R.string.api_key_nyt);
        //Дальше проверяем, если поле nameCritics заполнено, то
        if(nameCritics.getText().length() != 0){
            //Меняем в URL
            url = getString(R.string.main_url_critics_search) + nameCritics.getText().toString().replace(" ", "%20") + ".json?api-key=" + getString(R.string.api_key_nyt);
        }
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

        final RecyclerView recyclerView = getView().findViewById(R.id.recycler_critics);
        layoutManager = new GridLayoutManager(getContext(), 2);     //В отличии от Reviewes, тут нужно вывести в 2 колонки.
                                                                              //Поэтому тут используется GridLayoutManager, за место Linear
        recyclerView.setLayoutManager(layoutManager); //Устанавливаем LayoutManager
        recyclerView.setHasFixedSize(true); //Используем т.к. размер элементов у нас одинаковый
        MyCustomAdapterCritics adapterCritics = new MyCustomAdapterCritics(initData()); //Создаем адаптер
        recyclerView.setAdapter(adapterCritics); //и устанавливаем в RecyclerView
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Если "потянули" вниз, то обновляем
                getReviews();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 4000);
    }

    private void getReviews(){
        //Если лист и результаты не нулл, то очищаем их
        if(list != null && results != null) {
            list.clear();
            results.clear();
        }

        //Формируем URL
        createURL();
        //Вызываем парсес
        ParseTaskCritics parseTaskCritics = new ParseTaskCritics(this, url);
        parseTaskCritics.execute();
    }

    private List<CriticsElement> initData() {
        list = new ArrayList<>();
        //В лист добавляем элементы
        for (int i = 0; i < results.size(); i++) {
            try {
                list.add(new CriticsElement(results.get(i).getDisplayName(), results.get(i).getStatus(), results.get(i).getMultimedia().getResource().getSrc()));
            } catch (NullPointerException e){
                list.add(new CriticsElement(results.get(i).getDisplayName(), results.get(i).getStatus(), getString(R.string.src_user_avatar)));
            }
        }
        return list;
    }

}
