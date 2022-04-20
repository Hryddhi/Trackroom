package com.rifatul.trackroom;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityPostPublic extends BaseDataActivity {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99 ;
    private static final int SELECT_REQUEST_CODE = 1;

    EditText et_post, et_description;

    TextView tv_upload_filename;

    AppCompatButton btn_upload_files, btn_create_post;

    Uri materialUri, uriMaterialImage;
    String materialName;

    Boolean fileUploaded = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_post_public);

        et_post = findViewById(R.id.et_post);
        et_description = findViewById(R.id.et_description);
        btn_upload_files = findViewById(R.id.btn_upload_files);
        btn_create_post = findViewById(R.id.btn_create_post);
        tv_upload_filename = findViewById(R.id.tv_upload_filename);

        Intent ClassroomInfo = getIntent();
        int classPk = ClassroomInfo.getIntExtra("uploadMaterialClassroomPk" , 0);






        btn_upload_files.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckPermission()) {
                    //Intent select = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    Intent select = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    select.addCategory(Intent.CATEGORY_OPENABLE);
                    select.setType("application/pdf");
                    //select.setType("application/JPEG,JPG,PNG");
                    startActivityForResult(select, SELECT_REQUEST_CODE);
                    /*Intent select = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(select, SELECT_REQUEST_CODE);*/

                }
            }
        });

        btn_create_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    uploadFile(classPk);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data.getData() == null) {
            Toast.makeText(getApplicationContext(), "File not selected", Toast.LENGTH_SHORT).show();
        }
        else {
            materialUri = data.getData();
            materialName = getFileName(materialUri);
            tv_upload_filename.setAlpha(1);
            //String path = RealPathUtil.getRealPath(getApplicationContext(),materialUri);
            //String path = getRealPathFromURI(materialUri);
            String path = FileUtils.getPath(getApplicationContext(), materialUri);

            /*Intent detailedPostFile = new Intent(getApplicationContext(), ActivityDetailedPostCreate.class);

            detailedPostFile.putExtra("postFile", path);
            getApplicationContext().startActivity(detailedPostFile);*/



            tv_upload_filename.setText(path);
            fileUploaded = true;
            Toast.makeText(getApplicationContext(), path, Toast.LENGTH_SHORT).show();
        }

    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private Boolean checkLength() {
        int classCodeLength = et_post.getText().toString().trim().length();

        if (classCodeLength > 1) return true;
        return false;
    }

    private void uploadFile(int classPk) throws IOException {
        File file = null;
        MultipartBody body = null;

        if (validateEditText(et_post)) {
            if (fileUploaded) {

                //Log.d("upload material real file path", file.toString());
                file = new File(FileUtils.getPath(getApplicationContext(), materialUri));



                body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("title",et_post.getText().toString())
                        .addFormDataPart("description",et_description.getText().toString())
                        // add deadline stuff
                        .addFormDataPart("deadline","")
                        .addFormDataPart("content_material","assignment.pdf",
                                RequestBody.create(MediaType.parse(getContentResolver().getType(materialUri)), file))
                       /* .addFormDataPart("reading_material","assignment.JPEG",
                                RequestBody.create(MediaType.parse(getContentResolver().getType(materialUri)), file))*/
                        .build();
                /*Intent postDetails = new Intent(getApplicationContext(), ActivityCreateQuiz.class);
                postDetails.putExtra("title", );
                getApplicationContext().startActivity(postDetails);*/

                //send file here
            }
            else {
                Toast.makeText(getApplicationContext(), "File not Selected", Toast.LENGTH_SHORT).show();

            }
        }
        else {
            Toast.makeText(getApplicationContext(), "Please Fill Up All The Fields!", Toast.LENGTH_SHORT).show();

        }

        Call<ResponseBody> createModule = getApi().createModule(getAccess(), classPk, body);

        createModule.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //JSONObject jsonObject = new JSONObject(response.body().toString());

                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "File Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    startTrackroom();
                }
                else {

                    tv_upload_filename.setText(response.errorBody().toString());
                    Toast.makeText(getApplicationContext(), "File Upload Unsuccessful", Toast.LENGTH_SHORT).show();
                    Log.d("activity upload material error body printing", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("activity upload material on failure method", t.toString());
                Toast.makeText(getApplicationContext(), "File Upload Unsuccessful, Make Sure You Are Connected To The Internet!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean CheckPermission() {
        if (ContextCompat.checkSelfPermission(ActivityPostPublic.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(ActivityPostPublic.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(ActivityPostPublic.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(ActivityPostPublic.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(ActivityPostPublic.this,
                    Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(ActivityPostPublic.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new android.app.AlertDialog.Builder(ActivityPostPublic.this)
                        .setTitle("Permission")
                        .setMessage("Please accept the permissions")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(ActivityPostPublic.this,
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        MY_PERMISSIONS_REQUEST_LOCATION);


                                startActivity(new Intent(ActivityPostPublic
                                        .this, ActivityPostPublic.class));
                                ActivityPostPublic.this.overridePendingTransition(0, 0);
                            }
                        })
                        .create()
                        .show();


            } else {
                ActivityCompat.requestPermissions(ActivityPostPublic.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {

            return true;
        }
    }

    public void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Are you sure you want to go back, unsaved will be lost?");
        //builder.setMessage("All unsaved data will be discarded off.");
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ActivityPostPublic.super.onBackPressed();
            }
        });
        builder.show();
    }
}
