package com.example.githubusersearch.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.githubusersearch.R;
import com.example.githubusersearch.data.repository.SearchHistoryManager;
import com.example.githubusersearch.databinding.FragmentSearchBinding;

import java.util.List;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private SearchHistoryManager historyManager;
    private SearchHistoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        historyManager = new SearchHistoryManager(requireContext());

        setupHistoryList();
        setupSearchButton();
        setupKeyboardSearch();
        refreshHistory();
    }

    private void setupHistoryList() {
        adapter = new SearchHistoryAdapter(item -> {
            binding.searchEditText.setText(item);
            navigateToProfile(item);
        });
        binding.historyRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.historyRecyclerView.setAdapter(adapter);
    }

    private void setupSearchButton() {
        binding.searchButton.setOnClickListener(v -> {
            String query = binding.searchEditText.getText() != null
                    ? binding.searchEditText.getText().toString().trim() : "";
            if (!query.isEmpty()) {
                hideKeyboard();
                historyManager.saveSearch(query);
                refreshHistory();
                navigateToProfile(query);
            }
        });
    }

    private void setupKeyboardSearch() {
        binding.searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                binding.searchButton.performClick();
                return true;
            }
            return false;
        });
    }

    private void navigateToProfile(String username) {
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        Navigation.findNavController(requireView())
                .navigate(R.id.profileFragment, bundle);
    }

    private void refreshHistory() {
        List<String> history = historyManager.getHistory();
        if (history.isEmpty()) {
            binding.divider.setVisibility(View.GONE);
            binding.recentSearchesLabel.setVisibility(View.GONE);
            binding.historyRecyclerView.setVisibility(View.GONE);
            binding.emptyStateContainer.setVisibility(View.VISIBLE);
        } else {
            binding.divider.setVisibility(View.VISIBLE);
            binding.recentSearchesLabel.setVisibility(View.VISIBLE);
            binding.historyRecyclerView.setVisibility(View.VISIBLE);
            binding.emptyStateContainer.setVisibility(View.GONE);
            adapter.setItems(history);
        }
    }

    private void hideKeyboard() {
        View view = requireActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) requireContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshHistory();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
