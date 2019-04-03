package com.example.recipesapp;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.common.util.concurrent.ExecutionError;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentChange.Type;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.Query.Direction;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.Source;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class recipeDbHandler {
   private FirebaseFirestore db;
   private FirebaseAuth mAuth ;
   private FirebaseStorage storage;
   private StorageReference storageRef;

     public void setup() {
          // [START get_firestore_instance]
         db = FirebaseFirestore.getInstance();
         mAuth = FirebaseAuth.getInstance();
         storage = FirebaseStorage.getInstance();
         storageRef = storage.getReference("recipeImage");
        // StorageReference userImagesRef = storageRef.child("images");
          // [END get_firestore_instance]

          // [START set_firestore_settings] //enable offline
          FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                  .setPersistenceEnabled(true)
                  .build();
          db.setFirestoreSettings(settings);
          // [END set_firestore_settings]
     }
     public void addRecipes(Uri selectedImage,String recipeName , String description, Map<String,Object> dt, String serve, String time){
          try {
              setup();
             // if(){
                  dt.put("UserId",mAuth.getUid());
                  dt.put("recipeName",recipeName);
                  dt.put("recipeDsc",description);
                  dt.put("Time",time);
                  dt.put("serving",serve);
                  db.collection("recipes").document().set(dt);
             // }

          }catch  (Exception e) {
              e.printStackTrace();
          }
     }
     private void uploadPhoto(){
        // Random rndm = new Random();
        // int a = rndm.nextInt(10000);
         // String fileName = mAuth.getUid()+ String.valueOf(a)+selectedImage.getLastPathSegment();was the image name
         final String imageName = UUID.randomUUID().toString() + ".jpg";
         /*storageRef.child("image").child(imageName).putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
             @Override
             public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                 if (task.isSuccessful()) {
                     dt.put("image", imageName);


                 }}});*/
     }

}

