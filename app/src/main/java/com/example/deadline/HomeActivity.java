package com.example.deadline;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.Collections;

public class HomeActivity extends AppCompatActivity implements GiaoDichAdapter.OnItemActionListener {

    private RecyclerView recyclerView;
    private ArrayList<Transaction> transactionList;
    private GiaoDichAdapter adapter;
    private DatabaseReference transactionsRef;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.rvGiaoDich);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        transactionsRef = FirebaseDatabase.getInstance().getReference("users").child(uid).child("transactions");

        transactionList = new ArrayList<>();
        adapter = new GiaoDichAdapter(transactionList, this); // this là listener
        recyclerView.setAdapter(adapter);

        loadTransactions();
        setupBottomNavigation();
    }

    private void loadTransactions() {
        transactionsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                transactionList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Transaction transaction = data.getValue(Transaction.class);
                    if (transaction != null) {
                        // Thêm vào đầu danh sách
                        transactionList.add(0, transaction);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, "Lỗi tải dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onEdit(int position) {
        Toast.makeText(this, "Sửa giao dịch: " + transactionList.get(position).getName(), Toast.LENGTH_SHORT).show();
        // TODO: mở giao diện sửa (bạn có thể truyền transaction vào Intent)
    }

    @Override
    public void onDelete(int position) {
        Transaction toDelete = transactionList.get(position);
        String key = toDelete.getId(); // đảm bảo Transaction có thuộc tính id để xoá

        if (key != null) {
            transactionsRef.child(key).removeValue().addOnSuccessListener(aVoid -> {
                Toast.makeText(this, "Đã xoá giao dịch", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(e -> {
                Toast.makeText(this, "Xoá thất bại", Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_home);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                return true;
            } else if (id == R.id.nav_calendar) {
                startActivity(new Intent(HomeActivity.this, CalendarActivity.class));
                return true;
            } else if (id == R.id.nav_add) {
                startActivity(new Intent(HomeActivity.this, AddTransactionActivity.class));
                return true;
            } else if (id == R.id.nav_statistics) {
                startActivity(new Intent(HomeActivity.this, StaticsticsActivity.class));
                return true;
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                return true;
            }
            return false;
        });
    }
}
