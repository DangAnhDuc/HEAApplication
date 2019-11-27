package com.example.heaapp.view.activity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.example.heaapp.R;
import com.example.heaapp.presenter.UserInfoPresenterImpl;
import com.example.heaapp.service.RealmService;
import com.example.heaapp.ultis.ultis;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfoActivity extends AppCompatActivity implements UserInfoView {

    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_age)
    TextInputEditText edtAge;
    @BindView(R.id.radioSexGrp)
    RadioGroup radioSexGrp;
    @BindView(R.id.edt_weight)
    TextInputEditText edtWeight;
    @BindView(R.id.edt_height)
    TextInputEditText edtHeight;
    @BindView(R.id.edt_waist)
    TextInputEditText edtWaist;
    @BindView(R.id.edt_Hip)
    TextInputEditText edtHip;
    @BindView(R.id.edt_chest)
    TextInputEditText edtChest;
    @BindView(R.id.btn_saveInfo)
    AppCompatButton btnSaveInfo;

    RadioButton radioButton;
    UserInfoPresenterImpl userInfoPresenter;
    @BindView(R.id.radioM)
    RadioButton radioM;
    @BindView(R.id.radioF)
    RadioButton radioF;
    @BindView(R.id.img_user_ava)
    CircleImageView imgUserAva;
    private static final int IMAGE_REQUEST=1;
    private Uri imageUri;
    private StorageTask<UploadTask.TaskSnapshot> uploadTask;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
    private StorageReference storageReference= FirebaseStorage.getInstance().getReference("upload");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);

        RealmService realmService = RealmService.getInstance();
        userInfoPresenter = new UserInfoPresenterImpl(this, getContext(), realmService);

        btnSaveInfo.setOnClickListener(v -> {
            int selectedId = radioSexGrp.getCheckedRadioButtonId();
            radioButton = findViewById(selectedId);
            userInfoPresenter.saveInfo(edtAge.getText().toString(), radioButton.getText().toString(), edtWeight.getText().toString(),
                    edtHeight.getText().toString(), edtWaist.getText().toString(), edtHip.getText().toString(), edtChest.getText().toString());
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        userInfoPresenter.loadInfo();
    }

    @Override
    public void onSaveInfoSuccess() {
        ultis.showMessage(getContext(), getString(R.string.msg_save_info_success));
        ultis.setIntent(getContext(), HomeActivity.class);
    }

    @Override
    public void onSaveInfoFail(String error) {
        ultis.showMessage(getContext(), error);
    }

    @Override
    public void displayInfo(String name,String imgURL, int age, String sex, long weight, long height, long waist, long hip, long chest) {
        edtName.setEnabled(false);
        edtName.setText(name);
        if(imgURL.equals("default")){
            Glide.with(getContext()).load(R.drawable.default_ava).into(imgUserAva);
        }
        else {
            Glide.with(getContext()).load(imgURL).into(imgUserAva);

        }
        edtAge.setText(String.valueOf(age));
        if (sex.equals("Male")) {
            radioM.setChecked(true);
        } else {
            radioF.setChecked(true);
        }
        edtWeight.setText(String.valueOf(weight));
        edtHeight.setText(String.valueOf(height));
        edtWaist.setText(String.valueOf(waist));
        edtHip.setText(String.valueOf(hip));
        edtChest.setText(String.valueOf(chest));

    }

    @Override
    public void displayErrorMessage(String message) {
        ultis.showMessage(getContext(), message);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @OnClick(R.id.img_user_ava)
    public void onViewClicked() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== IMAGE_REQUEST&& resultCode==RESULT_OK && data!=null && data.getData()!=null) {

            imageUri = data.getData();

            if (uploadTask != null && uploadTask.isInProgress()) {
                ultis.showMessage(getContext(), "Upload in progress");
            } else {
                uploadImageFromGallery();
            }
        }
    }

    private void uploadImageFromGallery() {
        final ProgressDialog progressDialog= new ProgressDialog(getContext(), R.style.AppTheme_Dark_Dialog);
        progressDialog.setMessage("Uploading");
        progressDialog.show();

        if(imageUri!=null){
            final StorageReference fileReference=storageReference.child(System.currentTimeMillis()+
                    "."+ getFileExtension(imageUri));

            uploadTask =fileReference.putFile(imageUri);
            uploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()){
                    throw  task.getException();
                }
                return fileReference.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Uri downloadUri=task.getResult();
                    String mUri= downloadUri.toString();
                    databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(firebaseAuth.getCurrentUser().getUid());
                    databaseReference.child("imageURL").setValue(mUri);
                    Glide.with(getContext()).load(mUri).into(imgUserAva);
                    progressDialog.dismiss();

                }
                else {
                    ultis.showMessage(getContext(),"Upload failed!");
                }

            }).addOnFailureListener(e -> {
                ultis.showMessage(getContext(),e.getMessage());
                progressDialog.dismiss();
            });
        }
        else {
            ultis.showMessage(getContext(),"No image selected!");
        }
    }

    public String getFileExtension(Uri uri){
        ContentResolver contentResolver= getContext().getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}
