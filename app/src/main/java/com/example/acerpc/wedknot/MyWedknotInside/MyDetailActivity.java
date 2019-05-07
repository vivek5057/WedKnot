package com.example.acerpc.wedknot.MyWedknotInside;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.acerpc.wedknot.MainActivity;
import com.example.acerpc.wedknot.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyDetailActivity extends AppCompatActivity {
    // BlurImageView blurImageView;
    private CardView personalDetails;
    private CardView lifestyleDetails;
    private TextView addPhoto;
    private CircleImageView myDetaiUserPic;
    static public String imageUri;
    Button deleteAccountButton;
    Button dialogDeleteAccountButton;
    TextView emailReAuth;
    EditText passwordReAuth;
    public static String currentUserGender;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    FirebaseUser currentUser;
    StorageReference storageReference;

    SharedPreferences pref;
    SharedPreferences.Editor edit;
    CoordinatorLayout coordinatorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_detail);
        init();
        clickListener();
        readData();

        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAccountDialog();
            }
        });

    }

    public void readData() {

        String userEmail = currentUser.getEmail().replace(".", "");

        databaseReference.child(userEmail).child("Image").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                imageUri = dataSnapshot.getValue(String.class);
                if (MyDetailActivity.imageUri != null) {
                    Glide.with(getApplicationContext()).load(MyDetailActivity.imageUri).into(myDetaiUserPic);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MyDetailActivity.this, "" + databaseError.toException(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void init() {
        personalDetails = (CardView) findViewById(R.id.user_main_details);
        lifestyleDetails = (CardView) findViewById(R.id.lifestyle_details);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        addPhoto = findViewById(R.id.add_photo_text_view);
        myDetaiUserPic = findViewById(R.id.my_detail_user_pic);
        deleteAccountButton = findViewById(R.id.delete_account_button);
        coordinatorLayout = findViewById(R.id.my_detail_coordinate);

        pref = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        edit = pref.edit();

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

    }

    public void clickListener() {
        personalDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyDetailActivity.this, PersonalDetailsMyDetail.class));
            }
        });


        lifestyleDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyDetailActivity.this, LifeStyleMyDetail.class));
            }
        });

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent pickImage = new Intent(Intent.ACTION_GET_CONTENT);
                pickImage.setType("image/*");
                startActivityForResult(Intent.createChooser(pickImage, "Select Picture"), 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        final String userEmail = currentUser.getEmail().replace(".", "");

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            final StorageReference imageref = storageReference.child(imageUri.getLastPathSegment());
            imageref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri downloadUrl = uri;
                            databaseReference.child(userEmail).child("Image").setValue(downloadUrl.toString());
                        }
                    });
                }
            });
        }
    }

    public void deleteAccountDialog() {
        View view = LayoutInflater.from(MyDetailActivity.this).inflate(R.layout.delete_account_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(MyDetailActivity.this);
        builder.setTitle("Re-Authenticate").setView(view).setCancelable(false).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();

        dialogDeleteAccountButton = view.findViewById(R.id.dialog_delete_acc_button);
        emailReAuth = view.findViewById(R.id.delete_account_email);
        passwordReAuth = view.findViewById(R.id.delete_account_password);
        emailReAuth.setText(currentUser.getEmail());
        readCurrentUserGender();
        dialogDeleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reAuthenticate();
            }
        });
    }


    public void readCurrentUserGender() {
        databaseReference.child(currentUser.getEmail().replace(".","")).child("personalDetailsGender").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentUserGender = dataSnapshot.getValue(String.class);
               // Log.v("User Emails: ", currentUserGender);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    public void reAuthenticate() {
        if (!passwordReAuth.getText().toString().isEmpty()) {
            AuthCredential credential = EmailAuthProvider
                    .getCredential(currentUser.getEmail(), passwordReAuth.getText().toString().trim());

            currentUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                                if (currentUserGender.equals("Male")) {
                                    databaseReference.child("Male").child(currentUser.getEmail().replace(".", "")).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            databaseReference.child("Chats").child(currentUser.getEmail().replace(".", "")).addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                                        String friend = postSnapshot.getKey();
                                                        //  Log.v("MyDetailActivity fr:-", friend);

                                                        databaseReference.child("Chats").child(friend).child(currentUser.getEmail().replace(".", "")).removeValue();

                                                    }
                                                    databaseReference.child("Chats").child(currentUser.getEmail().replace(".", "")).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            databaseReference.child("ConnectionRequest").child(currentUser.getEmail().replace(".", "")).addValueEventListener(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                                                        String friend = postSnapshot.getKey();
                                                                        databaseReference.child("ConnectionRequest").child(friend).child(currentUser.getEmail().replace(".", "")).removeValue();

                                                                    }
                                                                    databaseReference.child("ConnectionRequest").child(currentUser.getEmail().replace(".", "")).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {

                                                                            databaseReference.child("ConnectionSuccessful").child(currentUser.getEmail().replace(".", "")).addValueEventListener(new ValueEventListener() {
                                                                                @Override
                                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                                                                        String friend = postSnapshot.getKey();
                                                                                        //     Log.v("MyDetailActivity fr:-", friend);

                                                                                        databaseReference.child("ConnectionSuccessful").child(friend).child(currentUser.getEmail().replace(".", "")).removeValue();

                                                                                    }
                                                                                    databaseReference.child("ConnectionSuccessful").child(currentUser.getEmail().replace(".", "")).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                            edit.putBoolean("permission", false).apply();
                                                                                            databaseReference.child(currentUser.getEmail().replace(".", "")).removeValue();
                                                                                            startActivity(new Intent(MyDetailActivity.this, MainActivity.class));
                                                                                            finish();

                                                                                        }
                                                                                    });
                                                                                }

                                                                                @Override
                                                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                                }
                                                                            });

                                                                        }
                                                                    });
                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                }
                                                            });


                                                        }
                                                    });
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                        }
                                    });
                                } else {

                                    databaseReference.child("Female").child(currentUser.getEmail().replace(".", "")).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            databaseReference.child("Chats").child(currentUser.getEmail().replace(".", "")).addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                                        String friend = postSnapshot.getKey();
                                                        //       Log.v("MyDetailActivity fr:-", friend);
                                                        databaseReference.child("Chats").child(friend).child(currentUser.getEmail().replace(".", "")).removeValue();

                                                    }
                                                    databaseReference.child("Chats").child(currentUser.getEmail().replace(".", "")).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            databaseReference.child("ConnectionRequest").child(currentUser.getEmail().replace(".", "")).addValueEventListener(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                                                        String friend = postSnapshot.getKey();
                                                                        //       Log.v("MyDetailActivity fri:-", friend);

                                                                        databaseReference.child("ConnectionRequest").child(friend).child(currentUser.getEmail().replace(".", "")).removeValue();

                                                                    }
                                                                    databaseReference.child("ConnectionRequest").child(currentUser.getEmail().replace(".", "")).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {

                                                                            databaseReference.child("ConnectionSuccessful").child(currentUser.getEmail().replace(".", "")).addValueEventListener(new ValueEventListener() {
                                                                                @Override
                                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                                                                        String friend = postSnapshot.getKey();
                                                                                        databaseReference.child("ConnectionSuccessful").child(friend).child(currentUser.getEmail().replace(".", "")).removeValue();

                                                                                    }
                                                                                    databaseReference.child("ConnectionSuccessful").child(currentUser.getEmail().replace(".", "")).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                            edit.putBoolean("permission", false).apply();
                                                                                            databaseReference.child(currentUser.getEmail().replace(".", "")).removeValue();
                                                                                            startActivity(new Intent(MyDetailActivity.this, MainActivity.class));
                                                                                            finish();

                                                                                        }
                                                                                    });
                                                                                }


                                                                                @Override
                                                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                                }
                                                                            });

                                                                        }
                                                                    });
                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                }
                                                            });


                                                        }
                                                    });
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });

                                        }
                                    });

                                }


                                //    Log.d("MyDetailActivity", "User re-authenticated.");
                                currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        //         Log.d("MyDetailActivity", "User account deleted.");

                                    }

                                });



                            }else{
                                passwordReAuth.setError("Enter Valid Password");
                            }

                        }

                    });

        } else {
            passwordReAuth.setError("Enter Your Password");
        }
    }
}
