package ru.protasov_dev.letmecodeinterviewtask;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ParseTask extends AsyncTask<Void, Void, String> {

    private String URL = "https://api.nytimes.com/svc/movies/v2/reviews/search.json?api-key=020eb74eff674e3da8aaa1e8e311edda";
    private HttpURLConnection urlConnection = null;
    private BufferedReader reader = null;
    private String resultJson = "";
    public ParseTaskTwo parseTaskTwo;
    public List<Result> results;

    @Override
    protected String doInBackground(Void... voids) {
        // получаем данные с внешнего ресурса
        try {
            URL url = new URL(URL); //создаем URL

            urlConnection = (HttpURLConnection) url.openConnection(); //открываем соединение
            urlConnection.setRequestMethod("GET"); //используем метод GET
            urlConnection.connect(); //подключаемся

            InputStream inputStream = urlConnection.getInputStream(); //создаем входной поток
            StringBuffer buffer = new StringBuffer(); //и буфер

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line); //считываем в буфер
            }

            resultJson = buffer.toString(); //преобразуем буфер в String

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultJson; //Полный JSON со страницы в формате String
    }

    @Override
    protected void onPostExecute(String strJson) {
        super.onPostExecute(strJson);
        // выводим целиком полученную json-строку
        Log.d("JSON: ", strJson);

        //С помощью Gson будем разбирать json на составляющие
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        //Заполняем ParseTaskTwo нашими данными из JSON
        parseTaskTwo = gson.fromJson(strJson, ParseTaskTwo.class);
    }
}
