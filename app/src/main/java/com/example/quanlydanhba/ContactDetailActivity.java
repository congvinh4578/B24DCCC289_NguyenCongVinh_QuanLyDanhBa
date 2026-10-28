package com.example.quanlydanhba;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/** Screen 4: contact details, call, favorite toggle and delete. */
public class ContactDetailActivity extends AppCompatActivity {

    public static final String EXTRA_CONTACT = "contact";

    private ContactRepository repository;

    /** The live object stored in the shared list (not just a copy). */
    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        repository = ContactRepository.getInstance();

        // Receive the contact sent through the Intent, then look up the real
        // object in the shared list (the Intent only carries a copy).
        Contact received = readContactFromIntent();
        if (received != null) {
            contact = repository.findByPhone(received.getPhone());
        }
        if (contact == null) {
            Toast.makeText(this, R.string.toast_contact_not_found, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        bindViews();
    }

    @SuppressWarnings("deprecation")
    private Contact readContactFromIntent() {
        if (Build.VERSION.SDK_INT >= 33) {
            return getIntent().getSerializableExtra(EXTRA_CONTACT, Contact.class);
        }
        return (Contact) getIntent().getSerializableExtra(EXTRA_CONTACT);
    }

    private void bindViews() {
        TextView tvAvatar = findViewById(R.id.tv_avatar);
        TextView tvName = findViewById(R.id.tv_name);
        TextView tvPhone = findViewById(R.id.tv_phone);
        TextView tvGroup = findViewById(R.id.tv_group);
        TextView tvGender = findViewById(R.id.tv_gender);
        TextView tvNotes = findViewById(R.id.tv_notes);
        CheckBox cbFavorite = findViewById(R.id.cb_favorite);
        Button btnCall = findViewById(R.id.btn_call);
        Button btnBack = findViewById(R.id.btn_back);
        Button btnDelete = findViewById(R.id.btn_delete);

        tvAvatar.setText(contact.getInitial());
        tvName.setText(contact.getFullName());
        tvPhone.setText(contact.getPhone());
        tvGroup.setText(getString(R.string.detail_group, contact.getGroup()));
        tvGender.setText(getString(R.string.detail_gender, contact.getGender()));

        String notes = contact.getNotes();
        if (notes == null || notes.trim().isEmpty()) {
            tvNotes.setText(R.string.detail_notes_empty);
        } else {
            tvNotes.setText(notes);
        }

        // Set the initial state first, then listen for changes. The change is
        // written straight into the shared object; MainActivity updates its
        // list and statistics in onResume().
        cbFavorite.setChecked(contact.isFavorite());
        cbFavorite.setOnCheckedChangeListener((buttonView, isChecked) ->
                contact.setFavorite(isChecked));

        // Call: implicit Intent that opens the dialer with the number filled in
        btnCall.setOnClickListener(v -> {
            Intent dial = new Intent(Intent.ACTION_DIAL,
                    Uri.fromParts("tel", contact.getPhone(), null));
            try {
                startActivity(dial);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, R.string.toast_no_dialer, Toast.LENGTH_SHORT).show();
            }
        });

        // Back: close this screen
        btnBack.setOnClickListener((View v) -> finish());

        // Delete: remove from the shared list and return to the list screen
        btnDelete.setOnClickListener(v -> {
            repository.remove(contact);
            Toast.makeText(this, R.string.toast_deleted, Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
