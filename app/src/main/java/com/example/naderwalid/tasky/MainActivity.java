package com.example.naderwalid.tasky;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.naderwalid.tasky.Adapters.RecyclerAdapter;
import com.example.naderwalid.tasky.Database.DB;
import com.example.naderwalid.tasky.Database.MySQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DB myDatabase;
    RecyclerAdapter recyclerAdapter;
    List<Task> list;
    EditText delete_name_editText;
    EditText add_name_editText, add_number_editText;
    Button add_btn, delete_btn;
    AlertDialog alertDialog;
    EditText oldNameEditText, newNameEditText, newNumberEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myDatabase = new DB(MainActivity.this);


        list = myDatabase.getAllTasks();
        sortList(list);


        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerAdapter = new RecyclerAdapter(this, list);
        recyclerView.setAdapter(recyclerAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                // ...
                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.update_dialog, null);
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Update Task")
                                .setCancelable(true)
                                .setView(dialogView);

                alertDialog = builder.create();
                alertDialog.show();

                oldNameEditText = dialogView.findViewById(R.id.oldname_update_dialog);
                newNameEditText = dialogView.findViewById(R.id.newname_update_dialog);
                newNumberEditText = dialogView.findViewById(R.id.newnumber_update_dialog);
                Button update_btn = dialogView.findViewById(R.id.btn_update);
                oldNameEditText.setText(list.get(position).getName());
                oldNameEditText.setEnabled(false);
                update_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Integer.parseInt(newNumberEditText.getText().toString()) > 0 && Integer.parseInt(newNumberEditText.getText().toString()) <= 5) {
                            myDatabase = new DB(MainActivity.this);
                            myDatabase.updateTask(oldNameEditText.getText().toString()
                                    , newNameEditText.getText().toString()
                                    , Integer.parseInt(newNumberEditText.getText().toString()));

                            list.clear();
                            list.addAll(myDatabase.getAllTasks());
                            sortList(list);
                            recyclerAdapter.notifyDataSetChanged();


                            alertDialog.dismiss();
                        } else {
                            Toast.makeText(MainActivity.this, "please enter valid priority number\n from 1-5 -_- ! ", Toast.LENGTH_LONG).show();
                        }


                    }
                });


            }

            @Override
            public void onItemLongClick(View view, int position) {
                // ...
            }

        }));
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.add_dialog, null);

                add_name_editText = dialogView.findViewById(R.id.name_add_dialog);
                add_number_editText = dialogView.findViewById(R.id.number_add_dialog);

                AlertDialog.Builder builder =
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Add Task")
                                .setCancelable(true)
                                .setView(dialogView);

                alertDialog = builder.create();
                alertDialog.show();
                add_btn = dialogView.findViewById(R.id.btn_add);
                TextView googleText = dialogView.findViewById(R.id.google);
                SpannableString spannableString = new SpannableString(googleText.getText());
                spannableString.setSpan(new ForegroundColorSpan(Color.BLUE),0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                 spannableString.setSpan(new ForegroundColorSpan(Color.RED),1,2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.yellowColor)),2,3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new ForegroundColorSpan(Color.BLUE),3,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new ForegroundColorSpan(Color.GREEN),4,5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new ForegroundColorSpan(Color.RED),5,6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                googleText.setText(spannableString);



                add_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Integer.parseInt(add_number_editText.getText().toString()) > 0 && Integer.parseInt(add_number_editText.getText().toString()) <= 5) {
                            Date c = Calendar.getInstance().getTime();

                            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                            String formattedDate = df.format(c);
                            myDatabase.addTask(new Task(add_name_editText.getText().toString(), formattedDate, Integer.parseInt(add_number_editText.getText().toString())));
                            list.add(new Task(add_name_editText.getText().toString(), formattedDate, Integer.parseInt(add_number_editText.getText().toString())));
                            sortList(list);
                            recyclerAdapter.notifyDataSetChanged();
                            alertDialog.dismiss();
                        } else {
                            Toast.makeText(MainActivity.this, "please enter valid priority number\n from 1-5 -_- ! ", Toast.LENGTH_LONG).show();
                        }

                    }
                });


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {


            case R.id.action_delete:
                View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.delete_dialog, null);
                delete_name_editText = dialogView.findViewById(R.id.name_delete_dialog);


                AlertDialog.Builder builder =
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Delete Task")
                                .setCancelable(true)
                                .setView(dialogView);
                alertDialog = builder.create();
                alertDialog.show();

                delete_btn = dialogView.findViewById(R.id.btn_delete);
                delete_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String itemPosition = getItemListPosition(list, delete_name_editText.getText().toString());
                        if (itemPosition != null) {
                            myDatabase.deleteTask(delete_name_editText.getText().toString());
                            list.remove(Integer.parseInt(itemPosition));
                            recyclerAdapter.notifyDataSetChanged();
                            sortList(list);
                            alertDialog.dismiss();
                        } else {
                            Toast.makeText(MainActivity.this, "Item does not exist !", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                return true;

            case R.id.action_settings:
                Toast.makeText(this, "Customize Your Settings !", Toast.LENGTH_SHORT).show();

                return true;
        }

        return false;
    }

    public String getItemListPosition(List<Task> list, String name) {
        String index = null;

        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).getName().equals(name)) {
                index = String.valueOf(i);

                return index;
            }
        }

        return index;
    }

    public void sortList(List<Task> list) {
        Collections.sort(list, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                if (o1.getPriority_number() ==
                        o2.getPriority_number()) {
                    return 0;
                } else if (o1.getPriority_number() <
                        o2.getPriority_number()) {
                    return -1;
                }
                return 1;
            }
        });
    }


}
