package org.hmcampoverde.service;

import org.hmcampoverde.dto.ProfileDto;
import org.hmcampoverde.message.Message;

public interface ProfileService {

    public ProfileDto findByIdentification(String identification);

    public Message updateProfile(String identification, ProfileDto profileDto);

}
