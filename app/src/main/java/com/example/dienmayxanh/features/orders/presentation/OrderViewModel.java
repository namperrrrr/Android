package com.example.dienmayxanh.features.orders.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dienmayxanh.core.network.Resource;
import com.example.dienmayxanh.features.orders.data.Order;
import com.example.dienmayxanh.features.orders.domain.OrderUseCases;

import java.util.List;

public class OrderViewModel extends ViewModel {

    private OrderUseCases useCases = new OrderUseCases();

    // Biến chứa danh sách đơn hàng để đẩy ra màn hình
    private MutableLiveData<Resource<List<Order>>> orderListState = new MutableLiveData<>();
    public LiveData<Resource<List<Order>>> getOrderListState() { return orderListState; }

    // Biến chứa trạng thái Thêm/Sửa/Xóa (Thành công hay Thất bại)
    private MutableLiveData<Resource<String>> actionState = new MutableLiveData<>();
    public LiveData<Resource<String>> getActionState() { return actionState; }

    public void fetchOrders() {
        orderListState.setValue(Resource.loading());
        useCases.getOrders.execute(resource -> orderListState.setValue(resource));
    }

    public void addOrder(Order order) {
        actionState.setValue(Resource.loading());
        useCases.addOrder.execute(order, resource -> actionState.setValue(resource));
    }

    public void updateOrder(Order order) {
        actionState.setValue(Resource.loading());
        useCases.updateOrder.execute(order, resource -> actionState.setValue(resource));
    }

    public void deleteOrder(String orderId) {
        actionState.setValue(Resource.loading());
        useCases.deleteOrder.execute(orderId, resource -> actionState.setValue(resource));
    }

    public void clearActionState() {
        actionState.setValue(null);
    }
}