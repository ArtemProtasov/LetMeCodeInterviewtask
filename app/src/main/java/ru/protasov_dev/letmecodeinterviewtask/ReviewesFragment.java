package ru.protasov_dev.letmecodeinterviewtask;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReviewesFragment extends Fragment{

    private EditText keywords;
    private EditText date;
    private Calendar Date = Calendar.getInstance();

    private String dateForSearch;
    private String keywordForSearch;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.reviewes_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        keywords = getView().findViewById(R.id.keyword);
        date = getView().findViewById(R.id.data);
        date.setInputType(InputType.TYPE_NULL); //Не выводим клавиатуру

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

}
