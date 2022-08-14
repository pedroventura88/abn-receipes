package abn.com.receipes.core.receipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceipeRepository extends JpaRepository<ReceipeEntity, Integer> {

}
