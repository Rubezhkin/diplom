package pv.nedobezhkin.supcom.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pv.nedobezhkin.supcom.service.UserPostService;
import pv.nedobezhkin.supcom.service.dto.UserPostDTO;

@Service
@RequiredArgsConstructor
public class UserPostServiceImpl implements UserPostService {
	@Override
	public UserPostDTO save(UserPostDTO userPostDTO) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'save'");
	}

	@Override
	public UserPostDTO update(UserPostDTO userPostDTO) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'update'");
	}

	@Override
	public UserPostDTO partialUpdate(UserPostDTO userPostDTO) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'partialUpdate'");
	}

	@Override
	public Optional<UserPostDTO> findById(Long id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'findById'");
	}

	@Override
	public List<UserPostDTO> findAll() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'findAll'");
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'delete'");
	}

}
