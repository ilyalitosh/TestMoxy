package com.example.litosh.moxytest.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.litosh.moxytest.app.SchoolApp;
import com.example.litosh.moxytest.models.Student;
import com.example.litosh.moxytest.views.AddStudentPageView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class AddStudentPresenter extends MvpPresenter<AddStudentPageView> implements IAddStudent{
    public AddStudentPresenter(){

    }

    @Override
    public void addStudent(Student student) {
        SchoolApp.getSchoolApi()
                .addStudent(student)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Student>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Student student) {
                        getViewState().showMainActivityToast("Успешное добавление");
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewState().showMainActivityToast("Ошибка добавления");
                        System.out.println(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
