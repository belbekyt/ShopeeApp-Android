package com.example.projektshopee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    int count = 0;
    TextView countText;
    Button plus;
    Button minus;
    int spinnerPrice = 0;
    int spinnerMultipledPrice = 0;
    int endingPrice = 0;
    int bonus1 = 0;
    int bonus2 = 0;
    int bonus3 = 0;
    TextView price;
    CheckBox bon1;
    CheckBox bon2;
    CheckBox bon3;
    Spinner spinner;
    Button order;
    EditText name;
    EditText surname;
    EditText email;
    String nameStr;
    String surnameStr;
    String emailStr;

    DBHelper DB;
    Boolean flag;
    String computerDB;
    int additions;
    Cursor cursor;

    ListView listView;
    HashMap<String, Object> hashMap;
    ArrayList<HashMap<String, Object>> list;

    int [] computers = {
            R.drawable.option1,
            R.drawable.option2,
            R.drawable.option3
    };

    String [] descriptions = {
            "Intel Core i5 12400 6 x 2,4GHz 16GB DDR4 SSD 256GB M.2 + HDD 1TB Sata GTX 1050Ti 4GB, cena 3824zł",
            "Intel Core i7 11700 8 x 2,9GHz 16GB DDR4 SSD 500GB M.2 GTX 1660 6GB, cena 5832zł",
            "RYZEN 9 3900X 12 x 3,8GHz 32GB DDR4 SSD 500GB M.2 GTX 2060 6GB, cena 7764zł"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);
        MyAdapter myAdapter = new MyAdapter(getApplicationContext(), computers, descriptions);
        spinner.setAdapter(myAdapter);

        countText = findViewById(R.id.amount);
        plus = findViewById(R.id.plusBtn);
        minus = findViewById(R.id.minusBtn);
        price = findViewById(R.id.price);
        bon1 = findViewById(R.id.bonus1);
        bon2 = findViewById(R.id.bonus2);
        bon3 = findViewById(R.id.bonus3);
        order = findViewById(R.id.order);
        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        email = findViewById(R.id.email);

        DB = new DBHelper(this);

        listView = findViewById(R.id.orderList);
        list = new ArrayList<>();

        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                        if(pos == 1){
                            spinnerPrice = 5832;
                            computerDB = descriptions[0];
                        }
                        else if(pos == 2){
                            spinnerPrice = 7764;
                            computerDB = descriptions[1];
                        }
                        else{
                            spinnerPrice = 3824;
                            computerDB = descriptions[2];
                        }

                        calcPrice();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {}
                }
        );

        plus.setOnClickListener(view -> {
            if(count != 10){count++;}
            calcPrice();
        });

        minus.setOnClickListener(view -> {
            if(count > 0){count--;}
            calcPrice();
        });

        bon1.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b){
                bonus1 = 329;
            }
            else{
                bonus1 = 0;
            }

            calcPrice();
        });

        bon2.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b){
                bonus2 = 109;
            }
            else{
                bonus2 = 0;
            }

            calcPrice();
        });

        bon3.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b){
                bonus3 = 49;
            }
            else{
                bonus3 = 0;
            }

            calcPrice();
        });

        order.setOnClickListener(view -> {
            flag = true;
            nameStr = name.getText().toString();
            surnameStr = surname.getText().toString();
            emailStr = email.getText().toString();
            additions = bonus1+bonus2+bonus3;

            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = currentTime.format(dateTimeFormatter);

            if(nameStr.length() < 2 || surnameStr.length() < 2){
                Toast.makeText(getApplicationContext(), "Błędne dane zamawiającego", Toast.LENGTH_LONG).show();
                flag = false;
            }

            if(!(emailStr.contains("@") && emailStr.length() > 5)){
                Toast.makeText(getApplicationContext(), "Niepoprawny Email", Toast.LENGTH_SHORT).show();
                flag = false;
            }

            if(endingPrice == 0){
                Toast.makeText(getApplicationContext(), "Nic nie wybrałeś", Toast.LENGTH_SHORT).show();
                flag = false;
            }

            if(flag){
                boolean checkInsertData = DB.insertOrderData(nameStr, surnameStr, emailStr, computerDB, count, additions, endingPrice, formattedDateTime);

                if(checkInsertData) {
                    Toast.makeText(getApplicationContext(), "Pomyślnie zamówiono", Toast.LENGTH_LONG).show();
                    count = 0;
                    bon1.setChecked(false);
                    bonus1 = 0;
                    bon2.setChecked(false);
                    bonus2 = 0;
                    bon3.setChecked(false);
                    bonus3 = 0;
                    name.setText("");
                    surname.setText("");
                    email.setText("");
                    calcPrice();
                }else{
                    Toast.makeText(getApplicationContext(), "Niestety nie udało się zamówić", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item0:
                setContentView(R.layout.activity_main);
                return true;
            case R.id.item1:
                cursor = DB.getData();
                showDBRecords();
                setContentView(R.layout.order_list);
                return true;
            case R.id.item2:
                setContentView(R.layout.login_data);
                return true;
            case R.id.item3:
                setContentView(R.layout.about);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {}

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    @SuppressLint("SetTextI18n")
    public void calcPrice(){
        countText.setText(Integer.toString(count));
        spinnerMultipledPrice = spinnerPrice * count;
        endingPrice = spinnerMultipledPrice + bonus1 + bonus2 + bonus3;
        price.setText(Integer.toString(endingPrice));
    }

    public void showDBRecords(){
        while (cursor.moveToNext()){
            hashMap = new HashMap<>();
            hashMap.put("nameSurname", cursor.getString(0)+cursor.getString(1));
            hashMap.put("email", cursor.getString(2));
            hashMap.put("computer", cursor.getString(3));
            hashMap.put("count", cursor.getString(4));
            hashMap.put("additions", cursor.getString(5));
            hashMap.put("price", cursor.getString(6));
            hashMap.put("orderData", cursor.getString(7));
            list.add(hashMap);
        }

        Log.i("ii", list.toString());

        String[] from = {"nameSurname", "email", "computer", "count", "additions", "price", "orderData"};
        int[] to = {R.id.nameSurname, R.id.emailLV, R.id.computer, R.id.count,  R.id.additions, R.id.priceLV, R.id.orderData};

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getApplicationContext(),
                list,
                R.layout.list_view_items,
                from,
                to
        );

        listView.setAdapter(simpleAdapter);
    }
}