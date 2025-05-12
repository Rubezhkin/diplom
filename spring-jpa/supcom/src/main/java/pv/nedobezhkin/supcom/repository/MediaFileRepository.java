package pv.nedobezhkin.supcom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pv.nedobezhkin.supcom.entity.MediaFile;

@Repository
public interface MediaFileRepository extends JpaRepository<MediaFile, Long> {

}
