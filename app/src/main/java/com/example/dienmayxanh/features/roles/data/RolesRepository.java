package com.example.dienmayxanh.features.roles.data;

import com.example.dienmayxanh.core.network.Resource;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class RolesRepository {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private  static  final  String COLLECTION_NAME = "roles";
    public  interface  RoleCallback<T> {
        void onComplete(Resource<T> resource);
    }
    public void getRoles(RoleCallback<List<Role>> callback) {
        db.collection(COLLECTION_NAME).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Role> roles = new ArrayList<>();
                for(QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    Role role = document.toObject(Role.class);
                    role.setId(document.getId());
                    roles.add(role);
                }
                callback.onComplete(Resource.success(roles));
                })
                .addOnFailureListener(e -> {
                    callback.onComplete(Resource.error(e.getMessage()));
                });
    }
    public void addRoles(Role role, RoleCallback<String> callback){
        db.collection(COLLECTION_NAME).document().set(role)
                .addOnSuccessListener(aVoid -> callback.onComplete(Resource.success("Thêm chức danh thành công!")))
                .addOnFailureListener(e -> callback.onComplete(Resource.error(e.getMessage())));

    }
    public void updateRoles(Role role, RoleCallback<String> callback){
        if(role.getId() == null){
            callback.onComplete(Resource.error("Không tìm thấy ID chức danh để cập nhật!"));
            return;
        }
        db.collection(COLLECTION_NAME).document(role.getId()).set(role)
                .addOnSuccessListener(aVoid -> callback.onComplete(Resource.success("Cập nhật thành công!")))
                .addOnFailureListener(e -> callback.onComplete(Resource.error(e.getMessage())));
    }
    public void  deleteRoles(String roleId, RoleCallback<String> callback){
        db.collection(COLLECTION_NAME).document(roleId).delete()
                .addOnSuccessListener(aVoid -> callback.onComplete(Resource.success("Đã xóa chức danh!")))
                .addOnFailureListener(e -> callback.onComplete(Resource.error(e.getMessage())));
    }

}
