package com.example.dienmayxanh.features.employees.data;

import com.example.dienmayxanh.core.network.Resource;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String COLLECTION_NAME = "employees";
    public interface EmployeeCallback<T> {
        void onComplete(Resource<T> resource);
    }
    public void getEmployees(EmployeeCallback<List<Employee>> callback) {
        db.collection(COLLECTION_NAME).get()
        .addOnSuccessListener(queryDocumentSnapshots -> {
            List<Employee> employees = new ArrayList<>();
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                Employee employee = document.toObject(Employee.class);
                employee.setId(document.getId());
                employees.add(employee);
            }
            callback.onComplete(Resource.success(employees));
            })
        .addOnFailureListener(e -> {
            callback.onComplete(Resource.error(e.getMessage()));
        });
    }
    public void addEmployee(Employee employee, EmployeeCallback<String> callback) {
        // Dùng .document() không truyền ID để Firebase tự động đẻ ra chuỗi ID ngẫu nhiên (chống trùng lặp)
        db.collection(COLLECTION_NAME).document().set(employee)
                .addOnSuccessListener(aVoid -> callback.onComplete(Resource.success("Thêm nhân viên thành công!")))
                .addOnFailureListener(e -> callback.onComplete(Resource.error(e.getMessage())));
    }
    public void updateEmployee(Employee employee, EmployeeCallback<String> callback) {
        if (employee.getId() == null) {
            callback.onComplete(Resource.error("Không tìm thấy ID nhân viên để cập nhật!"));
            return;
        }

        // Tìm đúng cái Document ID đó và ghi đè thông tin mới lên
        db.collection(COLLECTION_NAME).document(employee.getId()).set(employee)
                .addOnSuccessListener(aVoid -> callback.onComplete(Resource.success("Cập nhật thành công!")))
                .addOnFailureListener(e -> callback.onComplete(Resource.error(e.getMessage())));
    }
    public void deleteEmployee(String employeeId, EmployeeCallback<String> callback) {
        db.collection(COLLECTION_NAME).document(employeeId).delete()
                .addOnSuccessListener(aVoid -> callback.onComplete(Resource.success("Đã xóa nhân viên!")))
                .addOnFailureListener(e -> callback.onComplete(Resource.error(e.getMessage())));
    }

}
