package com.aggiegeeks.financial.respository;

import com.aggiegeeks.financial.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Users getUsersByUserName(String userName);
}
