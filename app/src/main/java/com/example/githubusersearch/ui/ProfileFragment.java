package com.example.githubusersearch.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.githubusersearch.R;
import com.example.githubusersearch.databinding.FragmentProfileBinding;
import com.example.githubusersearch.viewmodel.UserViewModel;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private UserViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        setupToolbar();
        observeViewModel();

        String username = getArguments() != null ? getArguments().getString("username", "") : "";
        if (!username.isEmpty()) {
            viewModel.fetchUser(username);
        }
    }

    private void setupToolbar() {
        binding.toolbar.setNavigationOnClickListener(v ->
                Navigation.findNavController(requireView()).popBackStack());
    }

    private void observeViewModel() {
        viewModel.isLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading != null && isLoading) {
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.profileScrollView.setVisibility(View.GONE);
                binding.errorContainer.setVisibility(View.GONE);
            }
        });

        viewModel.getError().observe(getViewLifecycleOwner(), errorMsg -> {
            if (errorMsg != null) {
                binding.progressBar.setVisibility(View.GONE);
                binding.profileScrollView.setVisibility(View.GONE);
                binding.errorContainer.setVisibility(View.VISIBLE);
                binding.errorText.setText(errorMsg);
            }
        });

        viewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                binding.progressBar.setVisibility(View.GONE);
                binding.errorContainer.setVisibility(View.GONE);
                binding.profileScrollView.setVisibility(View.VISIBLE);

                // Load avatar
                Glide.with(this)
                        .load(user.getAvatarUrl())
                        .placeholder(R.drawable.ic_people)
                        .into(binding.avatarImage);

                // Set text fields
                String displayName = user.getName() != null ? user.getName() : user.getLogin();
                binding.nameText.setText(displayName);
                binding.usernameText.setText(String.format("@%s", user.getLogin()));

                String bio = user.getBio() != null ? user.getBio() : getString(R.string.no_bio_available);
                binding.bioText.setText(bio);

                binding.followersCount.setText(String.valueOf(user.getFollowers()));
                binding.followingCount.setText(String.valueOf(user.getFollowing()));
                binding.reposCount.setText(String.valueOf(user.getPublicRepos()));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
