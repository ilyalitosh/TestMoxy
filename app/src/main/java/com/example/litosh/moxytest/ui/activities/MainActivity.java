package com.example.litosh.moxytest.ui.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.litosh.moxytest.R;
import com.example.litosh.moxytest.models.Student;
import com.example.litosh.moxytest.presenters.AddStudentPresenter;
import com.example.litosh.moxytest.presenters.IInitStudent;
import com.example.litosh.moxytest.presenters.ShowStudentsPresenter;
import com.example.litosh.moxytest.ui.adapters.ViewPagerAdapter;
import com.example.litosh.moxytest.views.AddStudentPageView;
import com.example.litosh.moxytest.views.ShowStudentsPageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends MvpAppCompatActivity implements AddStudentPageView, ShowStudentsPageView, IInitStudent {

    private ViewPagerAdapter vpAdapter;
    private ViewPager vp;
    private EditText inputIdForShowStudentsById;
    private TextView showingStudent;
    private Button showStudentsByIdButton;
    private EditText inputFirstName;
    private EditText inputSecondName;
    private EditText inputBirthday;
    private EditText inputClass;
    private EditText inputLetterOfClass;
    private EditText inputAddress;
    private Button addStudentButton;
    @InjectPresenter
    public AddStudentPresenter addStudentPresenter;
    @InjectPresenter
    public ShowStudentsPresenter showStudentsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vp = findViewById(R.id.view_pager);
        initAdapter();
        vp.setAdapter(vpAdapter);

    }

    private void initComponents(View v1, View v2){
        inputAddress = v1.findViewById(R.id.input_address);
        inputFirstName = v1.findViewById(R.id.input_firstName);
        inputSecondName = v1.findViewById(R.id.input_secondName);
        inputBirthday = v1.findViewById(R.id.input_birthday);
        inputClass = v1.findViewById(R.id.input_class);
        inputLetterOfClass = v1.findViewById(R.id.input_letterOfClass);
        addStudentButton = v1.findViewById(R.id.add_student_button);

        showingStudent = v2.findViewById(R.id.text_output);
        showStudentsByIdButton = v2.findViewById(R.id.show_students_with_id_button);
        inputIdForShowStudentsById = v2.findViewById(R.id.input_for_searchingById);
    }

    private void initAdapter(){
        LayoutInflater layoutInflater = getLayoutInflater();
        View v1 = layoutInflater.inflate(R.layout.add_student_item, null);
        View v2 = layoutInflater.inflate(R.layout.show_students_item, null);
        List<View> views = new ArrayList<>();
        views.add(v1);
        views.add(v2);
        vpAdapter = new ViewPagerAdapter(views);

        initComponents(v1, v2);
        initListeners();
    }

    private boolean isStudentEmpty(Student student){
        if(TextUtils.isEmpty(student.getFirstName())){
            return true;
        }else if(TextUtils.isEmpty(student.getSecondName())){
            return true;
        }else if(TextUtils.isEmpty(student.getAddress())){
            return true;
        }else if(TextUtils.isEmpty(student.getBirthday())){
            return true;
        }else if(TextUtils.isEmpty(String.valueOf(student.getClass_()))){
            return true;
        }else if(TextUtils.isEmpty(student.getLetterOfClass())){
            return true;
        }
        return false;
    }

    @Override
    public void initStudent(Student student){
        student.setFirstName(inputFirstName.getText().toString());
        student.setSecondName(inputSecondName.getText().toString());
        student.setBirthday(inputBirthday.getText().toString());
        student.setClass_(Integer.valueOf(inputClass.getText().toString()));
        student.setLetterOfClass(inputLetterOfClass.getText().toString());
        student.setAddress(inputAddress.getText().toString());
    }

    private void initListeners(){
        addStudentButton.setOnClickListener(v -> {
            try {
                Student student = new Student();
                initStudent(student);
                if(isStudentEmpty(student)){
                    Toast.makeText(MainActivity.this,
                            "Какое-то поле пустое",
                            Toast.LENGTH_SHORT).show();
                }else{
                    addStudentPresenter.addStudent(student);
                }
            }catch (NumberFormatException e){
                Toast.makeText(MainActivity.this,
                        "Проверьте вводимые данные",
                        Toast.LENGTH_SHORT).show();
            }
        });
        showStudentsByIdButton.setOnClickListener(v -> {
            showStudentsPresenter
                    .showStudentsById(Integer
                            .valueOf(inputIdForShowStudentsById.getText().toString()));
        });
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position,
                                       float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:

                        break;
                    case 1:
                        showStudentsPresenter.showStudents();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void showMainActivityToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showOutputStudents(String outputs) {
        showingStudent.setText(outputs);
    }
}
