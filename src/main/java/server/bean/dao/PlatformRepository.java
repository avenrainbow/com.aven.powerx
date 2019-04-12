package server.bean.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import server.bean.Platform;

import java.util.List;

@Repository
public interface PlatformRepository extends JpaRepository<Platform, Long> {


}