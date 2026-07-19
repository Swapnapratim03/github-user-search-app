package com.example.githubusersearch.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.githubusersearch.data.model.User;
import com.example.githubusersearch.data.repository.UserRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel extends ViewModel {

    private final UserRepository repository = new UserRepository();

    private final MutableLiveData<User> user = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);

    public LiveData<User> getUser() {
        return user;
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<Boolean> isLoading() {
        return loading;
    }

    public void fetchUser(String username) {
        loading.setValue(true);
        error.setValue(null);

        repository.getUser(username).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                loading.postValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    user.postValue(response.body());
                    error.postValue(null);
                } else {
                    user.postValue(null);
                    error.postValue("User not found");
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                loading.postValue(false);
                user.postValue(null);
                error.postValue("User not found");
            }
        });
    }
}
