package com.bn.library.repository;

import com.bn.library.constant.CheckoutStatus;
import com.bn.library.model.Checkout;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckoutRepository extends JpaRepository<Checkout, Integer> {
    List<Checkout> findAllByUserId(int userId);

    List<Checkout> findAllByUserIdAndStatusIn(Integer userId, List<CheckoutStatus> checkoutStatuses);
}
