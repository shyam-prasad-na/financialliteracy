package com.aggiegeeks.financial.respository;

import com.aggiegeeks.financial.entity.UserLiked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersLikedRepository extends JpaRepository<UserLiked, Long> {
}
