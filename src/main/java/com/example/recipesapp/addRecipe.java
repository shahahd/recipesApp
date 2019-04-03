package com.example.recipesapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class addRecipe extends AppCompatActivity {
    private static final int GALLERY_REQUEST_CODE=1;
    private ImageView picPhoto;
    private EditText recipeName,recipeDescription;
    private TextView showTime;
    private TextView showTime2;
    private TextView rctime,prepTime;
    private EditText serving,QntEditText;
    private LinearLayout parentLinearLayout;
    private LinearLayout parentLinearLayout2;
    private Spinner sp1;

    private FirebaseFirestore db ;
    private Uri selectedImage;//to assign the chosen image URI &send it to dbhandler
    private Map<String, Object> ingrediant=new HashMap<>();
    private String []dt;
    private FirebaseAuth mAuth;
    private int cTime=0;//set value for time
    private int tPrep=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        //bind the layout
        picPhoto =(ImageView)findViewById(R.id.imageView);
        recipeName=(EditText)findViewById(R.id.rcName);
        recipeDescription=(EditText)findViewById(R.id.rcDescrip);
        parentLinearLayout = (LinearLayout) findViewById(R.id.parent_linear_layout);//*****************for dynamic layout*****
        parentLinearLayout2 = (LinearLayout) findViewById(R.id.parent_linear_layout2);
        showTime=(TextView)findViewById(R.id.timeIn);
        showTime2=(TextView)findViewById(R.id.pretime2);
        serving=(EditText)findViewById(R.id.serving);
        rctime=(TextView)findViewById(R.id.timeIn);
        prepTime=(TextView)findViewById(R.id.pretime2);
        sp1=(Spinner)findViewById(R.id.spinner);
        parentLinearLayout = (LinearLayout) findViewById(R.id.parent_linear_layout);
        parentLinearLayout2 = (LinearLayout) findViewById(R.id.parent_linear_layout2);
        //
        mAuth = FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();
    }

    //*****to the dynamic layout onAddField & onDelete
    //add field to ingredient
    public void onAddField(View v) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.field, null);
        // Add the new row before the add field button.
        parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount());
    }
    //*****to delete or remove field that add ingredient
    public void onDelete(View v) {
        parentLinearLayout.removeView((View) v.getParent());
    }

    public void onAddStep(View v) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.step, null);
        // Add the new row before the add field button.
        parentLinearLayout2.addView(rowView, parentLinearLayout2.getChildCount() );
    }

    public void onSDelete(View v) {
        parentLinearLayout2.removeView((View) v.getParent());
    }




    //increase time value
    public void tUp2(View v){
        cTime++;
        showTime.setText(Integer.toString(cTime));
    }

    public void tDown2(View v){
        cTime--;
        showTime.setText(Integer.toString(cTime));
    }



    public void tUp(View v){
        tPrep++;
        showTime2.setText(Integer.toString(tPrep));
    }

    public void tDown(View v){
        tPrep--;
        showTime2.setText(Integer.toString(tPrep));
    }

    //back button ///////////////////////////////////////////////////////////////////////////////////////////
    public void back(View v){
        Intent n= new Intent(this,MainActivity.class);
        startActivity(n);
        finish();
    }

    //pick image from gallery
    public void pickFromGallery(View v) {
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, GALLERY_REQUEST_CODE);
            } else {
                //Create an Intent with action as ACTION_PICK
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intent.setType("image/*");//show just image
                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Request permission to Access gallery
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        switch (requestCode) {////////////////////////////////////////////////////////////////////////////why switch
            case GALLERY_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
                } else {
                    Toast.makeText(getApplicationContext(),"cannot open gallery",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //get image name & show the chosen image
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case GALLERY_REQUEST_CODE :
                    //data.getData returns the content URI for the selected Image
                    selectedImage = data.getData();
                    //picPhoto.setImageURI(selectedImage);
                   // selectedImage = data.getData();
                    Picasso.get().load(selectedImage).into(picPhoto);
                    //UploadToFireBase();//***********not called here it must call when user click on upload button delete it*****
                    break;
            }}

    //to get the data from ingredient fields
    //part of the code from https://stackoverflow.com/questions/25154446/getting-value-from-dynamically-created-textview-in-android
    public void calllThis(){
        int count = parentLinearLayout.getChildCount();
        dt=new String[count];
        for (int i = 0; i < count; i++) {
            final View row = parentLinearLayout.getChildAt(i);
            EditText textOut = (EditText) row.findViewById(R.id.number_edit_text);
            EditText QntEditText=(EditText)findViewById(R.id.quantityEditText);
            Spinner typeSpinner=(Spinner)findViewById(R.id.type_spinner);
            ingrediant.put("ingrediant",QntEditText.getText().toString()+" "+ typeSpinner.getSelectedItem().toString()
                    +" "+textOut.getText().toString());
            String data =textOut.getText().toString();
            dt[i]=data;

            //ArrayList a =
            //String ing="ing"+String.valueOf(dt);
        }
        List <String>ingrediant_tag=Arrays.asList(dt);
        ingrediant.put("ing",ingrediant_tag);
    }



    //upload to fireStore
    public void UploadToFireBase(View v){
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            if(selectedImage.)
            calllThis();
//
            //serving.getText().toString();
            //rctime.getText().toString();
            //prepTime.getText().toString();
            //get the time and the item unit (h,min)
            String timePerUnit=rctime.getText().toString()+sp1.getSelectedItem().toString();
            recipeDbHandler rc=new recipeDbHandler();
            rc.addRecipes(selectedImage, recipeName.getText().toString(),recipeDescription.getText().toString(),ingrediant,
                    serving.getText().toString(),timePerUnit);
            Toast.makeText(getApplicationContext(),"added succesuful",Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getApplicationContext(),"not signed",Toast.LENGTH_LONG).show();
        }
    }
}
