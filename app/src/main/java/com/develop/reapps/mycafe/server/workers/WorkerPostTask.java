package com.develop.reapps.mycafe.server.workers;

import android.os.AsyncTask;

import com.develop.reapps.mycafe.server.Requests;
import com.develop.reapps.mycafe.server.uploads.UploadPostAnswerBody;
import com.develop.reapps.mycafe.server.uploads.UploadsService;
import com.develop.reapps.mycafe.server.user.User;
import com.develop.reapps.mycafe.server.user.UserService;
import com.develop.reapps.mycafe.workers.Worker;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class WorkerPostTask extends AsyncTask<Worker, Void, String> {
    @Override
    protected String doInBackground(Worker... workers) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Requests.baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        UserService userService = retrofit.create(UserService.class);
        WorkerService workerService = retrofit.create(WorkerService.class);
        UploadsService uploadsService = retrofit.create(UploadsService.class);

        int role = workers[0].getRole();
        String post = workers[0].getPost();
        byte[] image = workers[0].getBytes();

        Call<User> userCall = userService.getUser(workers[0].getEmail());
        try {
            User user = userCall.execute().body();
            assert user != null;
            int id = user.id;
            String name = user.username;

            Call<UploadPostAnswerBody> pictureCall = uploadsService.loadPicture(image, name, ".png");
            int imageId = Objects.requireNonNull(pictureCall.execute().body()).imageId;

            Call<String> callPostWorker = workerService.loadWorker(id, role, post, imageId);
            return callPostWorker.execute().body();
        } catch (IOException e) {
            return "ОШИБКА";
        }
    }

}
