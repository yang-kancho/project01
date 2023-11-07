package com.project.project00.repository;


import com.project.project00.entity.UserInfoDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoDataEntity, Long> {

	// userid로 회원정보 조회
	Optional<UserInfoDataEntity> findByUseridAndPassword(String userid,String Password);

	Optional<UserInfoDataEntity> findByUserid(String userid);
	Optional<UserInfoDataEntity> findByEmail(String email);

	Optional<UserInfoDataEntity> findByNickname(String nickname);
	Optional<UserInfoDataEntity> findByUseridAndEmail(String userid,String email);
	Optional<UserInfoDataEntity> findByUseridAndNickname(String userid,String nickname);

	@Query(value = "SELECT nickname FROM user_info_data_entity WHERE id = :userId")
	@Transactional
	String findNicknameById(@Param("userId")Long userId);



	@Query(value = "SELECT * FROM user_info_data_entity WHERE userid LIKE CONCAT('%',:userId,'%')" , nativeQuery = true)
	@Transactional
	List<UserInfoDataEntity> searchByUserId(@Param("userId") String userId);

	@Query(value = "SELECT * FROM user_info_data_entity WHERE nickname LIKE CONCAT('%',:userNick,'%')" , nativeQuery = true)
	@Transactional
	List<UserInfoDataEntity> searchByUserNick(@Param("userNick")String userNick);


}
