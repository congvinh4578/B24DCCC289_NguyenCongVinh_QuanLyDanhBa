package com.example.quanlydanhba;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/** Screen 2: contact list with statistics. */
public class MainActivity extends AppCompatActivity {

    private ContactRepository repository;
    private ContactAdapter adapter;
    private TextView tvStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repository = ContactRepository.getInstance();

        tvStats = findViewById(R.id.tv_stats);
        ListView lvContacts = findViewById(R.id.lv_contacts);
        TextView tvEmpty = findViewById(R.id.tv_empty);
        Button btnAdd = findViewById(R.id.btn_add);

        // The adapter holds the shared list itself, so changes made by other
        // Activities show up after notifyDataSetChanged()
        adapter = new ContactAdapter(this, repository.getContacts());
        lvContacts.setAdapter(adapter);
        lvContacts.setEmptyView(tvEmpty);

        // Tap a row -> open the detail screen and pass the contact in the Intent
        lvContacts.setOnItemClickListener((parent, view, position, id) -> {
            Contact contact = adapter.getItem(position);
            Intent intent = new Intent(MainActivity.this, ContactDetailActivity.class);
            intent.putExtra(ContactDetailActivity.EXTRA_CONTACT, contact);
            startActivity(intent);
        });

        // Add button -> open the add screen
        btnAdd.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AddContactActivity.class)));
    }

    /**
     * Runs every time this screen comes back to the foreground (after adding,
     * deleting, or toggling favorite), so the list and statistics stay current.
     */
    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        updateStats();
    }

    private void updateStats() {
        tvStats.setText(getString(R.string.main_stats,
                repository.getTotalCount(),
                repository.getFavoriteCount()));
    }
}
