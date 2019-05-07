package com.example.acerpc.wedknot.NavigationFragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.acerpc.wedknot.ChatFragmentInside.ChatBoxActivity;
import com.example.acerpc.wedknot.ChatFragmentInside.ChatListItemPojo;
import com.example.acerpc.wedknot.ChatFragmentInside.ChatRecycleViewAdapter;
import com.example.acerpc.wedknot.InitialForms.AllFormDetailsPojo;
import com.example.acerpc.wedknot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment implements ChatRecycleViewAdapter.ListItemClickListener {

    public static final String EXTRA_URL = "imageURL";
    public static final String EXTRA_NAME = "userName";
    public static final String EXTRA_USER_EMAIL = "userEmail";
    List<ChatListItemPojo> chatListItemPojos;
    RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ChatRecycleViewAdapter adapter;
    View view;
    View emptyView;


    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.chat_recycleview_item);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        emptyView = view.findViewById(R.id.chat_empty_view);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();

        //DividerLine
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        chatListItemPojos = new ArrayList<ChatListItemPojo>();
        //callVolley();
        getAcceptedUser();

        return view;
    }

    public void getAcceptedUser() {
        String dotCurrentUser = mAuth.getCurrentUser().getEmail();
        String currentUser = dotCurrentUser.replace(".", "");

        databaseReference.child("ConnectionSuccessful").child(currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postDataSnapShot : dataSnapshot.getChildren()) {
                    final String requestFromPerson = postDataSnapShot.getKey();
                 //   Log.v("Person Requesting:", requestFromPerson);

                    databaseReference.child(requestFromPerson).child("Image").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            final String personImage = dataSnapshot.getValue(String.class);
                    //        Log.v("Person Image", personImage);

                            databaseReference.child(requestFromPerson).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    AllFormDetailsPojo allFormDetailsPojo = dataSnapshot.getValue(AllFormDetailsPojo.class);
                                    if(allFormDetailsPojo!=null) {
                                        String friendName = allFormDetailsPojo.loginDetailsFullName;
                                        String friendEmail = allFormDetailsPojo.loginDetailsEmailId.replace(".", "");

                                        //     Log.v("Chat Person Name:",friendName);

                                        chatListItemPojos.add(new ChatListItemPojo(personImage, friendName, friendEmail));
                                    }
                                    adapter = new ChatRecycleViewAdapter(getActivity(), ChatFragment.this,chatListItemPojos);
                                    recyclerView.setAdapter(adapter);

                                    if (chatListItemPojos.isEmpty()) {
                                        recyclerView.setVisibility(View.GONE);
                                        emptyView.setVisibility(View.VISIBLE);
                                    }
                                    else {
                                        recyclerView.setVisibility(View.VISIBLE);
                                        emptyView.setVisibility(View.GONE);
                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    //recycleview click event
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onListItemClick(int clickedItemIndex) {
        View imageView = getActivity().findViewById(R.id.person_image);
        Intent intent = new Intent(getActivity(), ChatBoxActivity.class);
        ChatListItemPojo chatListItemPojo = chatListItemPojos.get(clickedItemIndex);
        intent.putExtra(EXTRA_URL, chatListItemPojo.getChatimage());
        intent.putExtra(EXTRA_NAME, chatListItemPojo.getChatname());
        intent.putExtra(EXTRA_USER_EMAIL, chatListItemPojo.getChatEmail());
        ActivityOptions chatBoxImage = ActivityOptions.makeSceneTransitionAnimation(getActivity(),imageView,"chatBoxImage");
        startActivity(intent,chatBoxImage.toBundle());

    }
}
