package com.example.dienmayxanh.features.auth.data;

import com.example.dienmayxanh.core.network.Resource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class AuthRepository {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();//Cả ở phần này nữa  biến này có chức năng j đây bạn
    private FirebaseFirestore db = FirebaseFirestore.getInstance();//Tôi chỉ bit đây là lệnh gọi để kết nô vs Firestore trên firebase

    public interface AuthCallback<T>{//Tác dụng của thằng này là j nhi
        void onCompleted(Resource<T> resource);
    }

    public void login(String email, String password, AuthCallback<FirebaseUser> callback){
        callback.onCompleted(Resource.loading());
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> callback.onCompleted(Resource.success(authResult.getUser())))
                .addOnFailureListener(e -> callback.onCompleted(Resource.error(e.getMessage())));
    }
    public void register(String email, String password, String displayName, AuthCallback<String> callback){
        callback.onCompleted(Resource.loading());
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    String uid = authResult.getUser().getUid();
                    User user = new User(displayName, email, "user", uid);
                    db.collection("users").document(uid).set(user)
                            .addOnSuccessListener(aVoid -> callback.onCompleted(Resource.success("Đăng kí thành công")))
                            .addOnFailureListener(e -> callback.onCompleted(Resource.error(e.getMessage())));
                })
                .addOnFailureListener(e -> callback.onCompleted(Resource.error(e.getMessage())));
    }
    public void resetPassword(String email, AuthCallback<String> callback){
        callback.onCompleted(Resource.loading());
        mAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener(aVoid -> callback.onCompleted(Resource.success("Gửi mail thành công")))
                .addOnFailureListener(e -> callback.onCompleted(Resource.error(e.getMessage())));
    }
}
