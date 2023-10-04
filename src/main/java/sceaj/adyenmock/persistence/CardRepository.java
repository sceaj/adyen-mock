package sceaj.adyenmock.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import sceaj.adyenmock.persistence.entities.CardEntity;

public interface CardRepository extends JpaRepository<CardEntity, Integer> {
	
	CardEntity findByExternalKey(UUID externalKey);

}
