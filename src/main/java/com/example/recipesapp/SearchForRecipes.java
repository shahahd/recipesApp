package com.example.recipesapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ThrowOnExtraProperties;

public class SearchForRecipes extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usrCll = db.collection("recipes");//.whereEqualTo("UserId",user.getUid());
    private userDataAdapter adapter;
    TextView showResult;
    EditText searchRecipe;
    String search;
    private static final String TAG = "DocSnippets";
    private SearchView searchBying;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_recipes);
       // searchBying=(SearchView)findViewById(R.id.SearchByIngrediant);
        showResult=(TextView)findViewById(R.id.TextViewSearchResult);
        searchRecipe=(EditText)findViewById(R.id.searchText);
       /* searchBying.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });*/
    }
    public void searchForRecipeInDB(View v){
        search=searchRecipe.getText().toString();
        db.collection("recipes").whereArrayContains("ing",search).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                       // if(document.exists()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                        showResult.setText(document.getString("recipeName"));
                         //showtxt.setText(document.getData().toString());
                       // }else {
                            //Toast.makeText(getApplicationContext(),"no result",Toast.LENGTH_SHORT).show();
                        //showResult.setText("");
                       // }
                    }
                    //.whereArrayContains("ingrediant",search)
                } else {

                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

    }

}
