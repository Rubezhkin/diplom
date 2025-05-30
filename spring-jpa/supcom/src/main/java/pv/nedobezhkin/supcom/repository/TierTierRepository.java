package pv.nedobezhkin.supcom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pv.nedobezhkin.supcom.entity.Author;
import pv.nedobezhkin.supcom.entity.SubscriptionTier;
import pv.nedobezhkin.supcom.entity.TierTier;
import java.util.List;

@Repository
public interface TierTierRepository extends JpaRepository<TierTier, Long> {
	List<TierTier> findByChildTier(SubscriptionTier childTier);

	List<TierTier> findByParentTier(SubscriptionTier parentTier);

	@Query("""
			Select tt from TierTier tt where tt.childTier in (select st from SubscriptionTier st where st.author = :author)
			""")
	List<TierTier> findByAuthor(Author author);

}