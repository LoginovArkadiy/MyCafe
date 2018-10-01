package com.develop.reapps.mycafe.server.user;

import android.content.Context;
import android.os.AsyncTask;

import com.develop.reapps.mycafe.MainActivity;
import com.develop.reapps.mycafe.server.AnswerBody;
import com.develop.reapps.mycafe.server.products.ProductService;
import com.develop.reapps.mycafe.server.retrofit.Requests;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Retrofit;

public class UserClient {

    private static Retrofit retrofit;
    private static UserService userService;
    private Context context;

    public UserClient(Context context) {
        retrofit = Requests.getClient();
        userService = retrofit.create(UserService.class);
        this.context = context;
    }

    public UserClient() {
        this(MainActivity.getAppContext());
    }

    public int logOut() {
        UserLogOutTask task = new UserLogOutTask();
        task.execute();
        try {
            int status = task.get(2, TimeUnit.SECONDS);
            Requests.makeToastNotification(context, status);
            return status;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int signUp(String email, String username, String password) {
        UserSignUpTask task = new UserSignUpTask();
        task.execute(username, email, password);
        try {
            int status = task.get(2, TimeUnit.SECONDS);
            Requests.makeToastNotification(context, status);
            return status;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
            Requests.makeToastNotification(context, "Timeout");
        }
        return -1;
    }

    public int signIn(String email, String password) {
        UserSignInTask task = new UserSignInTask();
        task.execute(email, password);
        try {
            int status = task.get(2, TimeUnit.SECONDS);
            Requests.makeToastNotification(context, status);
            return status;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
            Requests.makeToastNotification(context, "Timeout");
        }
        return -1;
    }

    public User getUserById(int id) {
        UserGetIdTask task = new UserGetIdTask();
        task.execute(id);
        try {
            return task.get(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static class UserGetIdTask extends AsyncTask<Integer, Void, User> {
        @Override
        protected User doInBackground(Integer... integers) {
            UserService userService = retrofit.create(UserService.class);
            Call<User> call = userService.getUser(integers[0]);

            try {
                return call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    private static class UserLogOutTask extends AsyncTask<Void, Void, Integer> {
        @Override
        protected Integer doInBackground(Void... voids) {
            Call<AnswerBody> call = userService.logout();
            try {
                return call.execute().code();
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            }
        }
    }

    private static class UserSignInTask extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            String password = params[1], email = params[0];
            Call<AnswerBody> call = userService.signIn(email, password);
            try {
                return call.execute().code();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return -1;
        }
    }

    private static class UserSignUpTask extends AsyncTask<String, String, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            String username = params[0], email = params[1], password = params[2];
            Call<AnswerBody> call = userService.loadUser(username, password, email);
            try {
                return call.execute().code();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return -1;
        }

    }
}