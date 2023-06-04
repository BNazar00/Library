package com.bn.book.repository;

import com.bn.clients.book.constant.CheckoutStatus;
import com.bn.book.model.Checkout;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckoutRepository extends JpaRepository<Checkout, Integer> {
    List<Checkout> findAllByUserId(int userId);

    List<Checkout> findAllByUserIdAndStatusIn(Integer userId, List<CheckoutStatus> checkoutStatuses);
}
