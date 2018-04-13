package com.example.litosh.moxytest.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.litosh.moxytest.app.SchoolApp;
import com.example.litosh.moxytest.models.Student;
import com.example.litosh.moxytest.views.ShowStudentsPageView;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ShowStudentsPresenter extends MvpPresenter<ShowStudentsPageView> implements IShowStudents{
    public ShowStudentsPresenter(){

    }

    @Override
    public void showStudents(){
        SchoolApp.getSchoolApi()
                .getStudents()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Student>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Student> students) {
                        StringBuilder s = new StringBuilder();
                        if(students.size() == 0){
                            getViewState().showOutputStudents("Отсутствуют");
                        }else {
                            for(int i = 0; i < students.size(); i++){
                                s.append("id: ").append(students.get(i).getId())
                                        .append(", Фамилия: ").append(students.get(i).getSecondName())
                                        .append(", Имя: ").append(students.get(i).getFirstName())
                                        .append("\n");
                            }
                            getViewState().showOutputStudents(s.toString());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void showStudentsById(int id) {
        SchoolApp.getSchoolApi()
                .getStudents(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Student>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Student> students) {
                        StringBuilder s = new StringBuilder();
                        if(students.size() == 0){
                            getViewState().showOutputStudents("Отсутствуют");
                        }else {
                            for(int i = 0; i < students.size(); i++){
                                s.append("id: ").append(students.get(i).getId())
                                        .append(", Фамилия: ").append(students.get(i).getSecondName())
                                        .append(", Имя: ").append(students.get(i).getFirstName())
                                        .append("\n");
                            }
                            getViewState().showOutputStudents(s.toString());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
