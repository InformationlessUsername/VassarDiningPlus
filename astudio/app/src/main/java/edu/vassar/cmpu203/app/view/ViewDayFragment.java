package edu.vassar.cmpu203.app.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import edu.vassar.cmpu203.app.model.Day;

import edu.vassar.cmpu203.app.databinding.FragmentViewDayBinding;

/**
 * A     private final User savedUser;
simple {@link Fragment} subclass.
 * Use the {@link ViewDayFragment()}  method to
 * create an instance of this fragment.
 */
public class ViewDayFragment extends Fragment implements IViewDay {

    private FragmentViewDayBinding binding;
    private final Listener listener;


    public ViewDayFragment(Listener listener){
        this.listener = listener;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.binding = FragmentViewDayBinding.inflate(inflater, container, false);

        return this.binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        // Generate current date as YYYY-MM-DD
        LocalDate dateObj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String today = dateObj.format(formatter);
        // Set the date input to today's date
        this.binding.dateInput.setText(today);


        View.OnClickListener dayInputsListener = view1 -> {
            String date = binding.dateInput.getText().toString();
            boolean favoritesOnly = binding.favoritesFilterCheckbox.isChecked();
            ViewDayFragment.this.listener.onDayRequested(date, favoritesOnly, ViewDayFragment.this); // let controller know!
        };
        // set up add item handler so when the search button is clicked, the controller is notified
        this.binding.dateInputButton.setOnClickListener(dayInputsListener);
        // Do the same for the favorites filter checkbox
        this.binding.favoritesFilterCheckbox.setOnClickListener(dayInputsListener);

        // Let the controller know that the view has been created
        // By default, the current day is displayed and the favorites filter is off
        boolean onlyFavorites = this.binding.favoritesFilterCheckbox.isChecked();
        this.listener.onDayRequested(today, onlyFavorites,this);

    }

    /**
     * Updates the day display to show the given day
     * @param day
     * @param listener
     */
    public void updateDayDisplay(Day day, DishViewHolder.Listener listener) {
        RecyclerView recyclerView = this.binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(new DayAdapter(day, listener));
    }

    /**
     * Displays a message to the user that the date they entered was invalid
     * Called by the controller when the user enters an invalid date in the date input
     * and clicks the search button
     * @param rootView The root view of the fragment to display the message on
     */
    @Override
    public void onInvalidDate(View rootView) {
        Snackbar.make(rootView, "Invalid date! Use the format YYYY-MM-DD", Snackbar.LENGTH_LONG).show();
    }

}