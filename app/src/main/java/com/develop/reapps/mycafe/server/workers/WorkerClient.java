package com.develop.reapps.mycafe.server.workers;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.develop.reapps.mycafe.MainActivity;
import com.develop.reapps.mycafe.server.AnswerBody;
import com.develop.reapps.mycafe.server.retrofit.Requests;
import com.develop.reapps.mycafe.server.uploads.UploadsService;
import com.develop.reapps.mycafe.server.user.User;
import com.develop.reapps.mycafe.server.user.UserService;
import com.develop.reapps.mycafe.workers.Worker;

import java.io.File;
import java.io.IOException;
import java.util.List;
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

    public void getWorkerByEmail(Worker worker) {
        String email = worker.getEmail();
        UserService service = retrofit.create(UserService.class);
        Call<User> userCall = service.getUser(email);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                Requests.makeToastNotification(context, response);
                if (response.isSuccessful() && response.body() != null) {
                    testLoadWorker(worker, response.body().getId());
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
            }
        });

    }

    private void testLoadWorker(Worker worker, int id) {
        String post = worker.getPost();
        File file = worker.getFile();

        RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("picture", file.getName() + ".png", reqFile);
        RequestBody reqId = RequestBody.create(MediaType.parse("multipart/form-data"), Integer.toString(id));
        RequestBody reqPost = RequestBody.create(MediaType.parse("multipart/form-data"), post);

        Call<AnswerBody> call = workerService.loadWorker2(reqId, reqPost, body);
        call.enqueue(new Callback<AnswerBody>() {
            @Override
            public void onResponse(@NonNull Call<AnswerBody> call, @NonNull Response<AnswerBody> response) {
                Requests.makeToastNotification(context, response);
                if (response.isSuccessful() && response.body() != null) {
                    editRole(worker, response.body().id);
                }
            }

            @Override
            public void onFailure(@NonNull Call<AnswerBody> call, @NonNull Throwable t) {
            }
        });
    }

    private void editRole(Worker worker, int id) {
        Call<AnswerBody> call = workerService.editRole(id, worker.getRole());
        call.enqueue(new Callback<AnswerBody>() {
            @Override
            public void onResponse(@NonNull Call<AnswerBody> call, @NonNull Response<AnswerBody> response) {
                Requests.makeToastNotification(context, response);
            }

            @Override
            public void onFailure(@NonNull Call<AnswerBody> call, @NonNull Throwable t) {

            }
        });


    }

    public void getWorkers(List<Worker> list, RecyclerView.Adapter adapter) {
        Call<List<Worker>> call = workerService.getWorkers();
        call.enqueue(new Callback<List<Worker>>() {
            @Override
            public void onResponse(@NonNull Call<List<Worker>> call, @NonNull Response<List<Worker>> response) {
                Requests.makeToastNotification(context, response);
                if (response.isSuccessful() && response.body() != null) {
                    list.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Worker>> call, @NonNull Throwable t) {
            }
        });


    }


    public void changeTime(boolean isWorkToday, int id) {
        Call<AnswerBody> call = workerService.changeWorkTime(isWorkToday, id);
        call.enqueue(new Callback<AnswerBody>() {
            @Override
            public void onResponse(@NonNull Call<AnswerBody> call, @NonNull Response<AnswerBody> response) {
                Requests.makeToastNotification(context, response);
            }

            @Override
            public void onFailure(@NonNull Call<AnswerBody> call, @NonNull Throwable t) {

            }
        });
    }


}