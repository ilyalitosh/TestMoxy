package com.example.litosh.moxytest.app;

import com.example.litosh.moxytest.models.Student;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SchoolApi {

    @GET("students")
    Observable<List<Student>> getStudents();

    @GET("students/students")
    Observable<List<Student>> getStudents(@Query("id") int id);

    @POST("addStudent")
    Observable<Student> addStudent(@Body Student student);

}
