package server.bean.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.bean.Platform;
import server.bean.dao.PlatformRepository;
import server.bean.service.PlatformService;

@Service
public class PlatformServiceImpl implements PlatformService {

    @Autowired
    public PlatformRepository platformRepository;

    @Override
    public Platform findById(Long platformId) {
        return platformRepository.getOne(platformId);
    }
}
