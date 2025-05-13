package pv.nedobezhkin.supcom.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import pv.nedobezhkin.supcom.service.PostService;
import pv.nedobezhkin.supcom.service.dto.PostDTO;

@Service
public class PostServiceImpl implements PostService {

	@Override
	public PostDTO save(PostDTO postDTO) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'save'");
	}

	@Override
	public PostDTO update(Long id, PostDTO postDTO) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'update'");
	}

	@Override
	public PostDTO findOne(Long id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'findOne'");
	}

	@Override
	public Optional<PostDTO> findById(Long id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'findById'");
	}

	@Override
	public List<PostDTO> findAll() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'findAll'");
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'delete'");
	}

}
