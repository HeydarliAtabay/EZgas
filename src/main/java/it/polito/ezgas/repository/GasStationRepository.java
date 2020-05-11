package it.polito.ezgas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.polito.ezgas.entity.*;

@Repository
/* repository interface. Since it extends JpaRepository it already has the methods to 
 * communicate with the db, with the right types since <GasStation, Integer> is specified
 */
public interface GasStationRepository extends JpaRepository<GasStation, Integer> {
	
}
