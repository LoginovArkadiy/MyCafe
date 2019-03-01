package com.develop.reapps.mycafe.server.workers;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.develop.reapps.mycafe.MainActivity;
import com.develop.reapps.mycafe.server.AnswerBody;
import com.develop.reapps.mycafe.server.retrofit.Requests;
import com.develop.reapps.mycafe.server.uploads.UploadsService;
import com.develop.reapps.mycafe.server.user.User;
import com.develop.reapps.mycafe.server.user.UserService;
import com.develop.reapps.mycafe.workers.Worker;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WorkerClient {
    private Context context;
    private static Retrofit retrofit;
    private static WorkerService workerService;

    public WorkerClient(Context context) {
        this.context = context;
        retrofit = Requests.getClient();
        workerService = retrofit.create(WorkerService.class);
    }

    public WorkerClient() {
        this(MainActivity.getAppContext());
    }

    public void loadWorker(Worker worker) {
        WorkerPostTask task = new WorkerPostTask();
        task.execute(worker);
        try {
            Requests.makeToastNotification(context, task.get(5, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
            Requests.makeToastNotification(context, "Timeout Workers");
        }
    }

    public Worker[] getWorkers() {
        WorkersGetTask task = new WorkersGetTask();
        task.execute();
        try {
            Worker[] workers = task.get(10, TimeUnit.SECONDS);
            return workers == null ? new Worker[0] : workers;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
            Requests.makeToastNotification(context, "TimeOut Workers");
        }
        return new Worker[0];
    }

    public void changeTime(boolean isWorkToday, int id) {
        Call<AnswerBody> call = workerService.changeWorkTime(isWorkToday, id);
        call.enqueue(new Callback<AnswerBody>() {
            @Override
            public void onResponse(@NonNull Call<AnswerBody> call, @NonNull Response<AnswerBody> response) {
                Requests.makeToastNotification(context, response.code());
            }

            @Override
            public void onFailure(@NonNull Call<AnswerBody> call, @NonNull Throwable t) {

            }
        });
    }

    private static class WorkerPostTask extends AsyncTask<Worker, Void, Integer> {
        @Override
        protected Integer doInBackground(Worker... workers) {
            Retrofit retrofit = Requests.getClient();
            UserService userService = retrofit.create(UserService.class);
            WorkerService workerService = retrofit.create(WorkerService.class);
            UploadsService uploadsService = retrofit.create(UploadsService.class);

            int role = workers[0].getRole();
            String post = workers[0].getPost();
            String email = workers[0].getEmail();
            File f = workers[0].getFile();

            RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), f);
            MultipartBody.Part body = MultipartBody.Part.createFormData("picture", email + ".png", reqFile);
            RequestBody reqDescription = RequestBody.create(MediaType.parse("multipart/form-data"), post);

            try {
                Call<User> userCall = userService.getUser(email);
                Integer id = Objects.requireNonNull(userCall.execute().body()).getId();

                Call<AnswerBody> callEditPost = workerService.loadWorker(id, post);
                Integer workerId = Objects.requireNonNull(callEditPost.execute().body()).id;

                Call<AnswerBody> callEditRole = workerService.editRole(workerId, role);
                AnswerBody answerBody = callEditRole.execute().body();

                Call<AnswerBody> uploadCall = uploadsService.loadPicture3(body, reqDescription);
                Integer imageId = Objects.requireNonNull(uploadCall.execute().body()).id;

                Call<AnswerBody> callEditImage = workerService.editImage(workerId, imageId);
                return callEditImage.execute().code();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return -1;
        }

    }

    private static class WorkersGetTask extends AsyncTask<Void, Void, Worker[]> {
        @Override
        protected Worker[] doInBackground(Void... voids) {
            Call<Worker[]> call = workerService.getWorkers();
            try {
                return call.execute().body();
            } catch (IOException e) {
                return new Worker[0];
            }
        }
    }
}