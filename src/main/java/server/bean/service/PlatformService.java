package server.bean.service;

import org.springframework.stereotype.Service;
import server.bean.Platform;

@Service
public interface PlatformService {

    Platform findById(Long platformId);

}
