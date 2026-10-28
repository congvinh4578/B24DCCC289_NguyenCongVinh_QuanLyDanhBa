package com.example.quanlydanhba;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

/** Screen 3: add a new contact. */
public class AddContactActivity extends AppCompatActivity {

    // Exactly 10 digits, first digit is 0
    private static final Pattern PHONE_PATTERN = Pattern.compile("^0[0-9]{9}$");

    private EditText etName;
    private EditText etPhone;
    private EditText etNotes;
    private Spinner spGroup;
    private RadioGroup rgGender;
    private CheckBox cbFavorite;

    private ContactRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        repository = ContactRepository.getInstance();

        etName = findViewById(R.id.et_name);
        etPhone = findViewById(R.id.et_phone);
        etNotes = findViewById(R.id.et_notes);
        spGroup = findViewById(R.id.sp_group);
        rgGender = findViewById(R.id.rg_gender);
        cbFavorite = findViewById(R.id.cb_favorite);
        Button btnSave = findViewById(R.id.btn_save);

        btnSave.setOnClickListener(v -> saveContact());
    }

    private void saveContact() {
        etName.setError(null);
        etPhone.setError(null);

        // Collapse repeated spaces: "  Nguyễn   Văn An " -> "Nguyễn Văn An"
        String name = etName.getText().toString().trim().replaceAll("\\s+", " ");
        String phone = etPhone.getText().toString().trim();

        // 1. Name must not be empty
        if (name.isEmpty()) {
            etName.setError(getString(R.string.error_name_empty));
            etName.requestFocus();
            return;
        }

        // 2. Phone must be 10 digits starting with 0
        if (!PHONE_PATTERN.matcher(phone).matches()) {
            etPhone.setError(getString(R.string.error_phone_invalid));
            etPhone.requestFocus();
            return;
        }

        // 3. Phone must not already exist in the contact list
        if (repository.phoneExists(phone)) {
            etPhone.setError(getString(R.string.error_phone_duplicate));
            etPhone.requestFocus();
            return;
        }

        // 4. Build the Contact from the form
        String group = spGroup.getSelectedItem().toString();
        String gender = getSelectedGender();
        boolean favorite = cbFavorite.isChecked();
        String notes = etNotes.getText().toString().trim();

        Contact contact = new Contact(name, phone, group, gender, favorite, notes);

        // 5. Add to the shared list and go back; MainActivity refreshes the
        //    list and statistics in onResume()
        repository.add(contact);
        Toast.makeText(this, R.string.toast_added, Toast.LENGTH_SHORT).show();
        finish();
    }

    private String getSelectedGender() {
        int checkedId = rgGender.getCheckedRadioButtonId();
        if (checkedId == -1) {
            return getString(R.string.gender_male); // default
        }
        RadioButton selected = findViewById(checkedId);
        return selected.getText().toString();
    }
}
