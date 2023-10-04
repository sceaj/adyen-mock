package sceaj.adyenmock.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import sceaj.adyenmock.persistence.entities.CardHolderEntity;

public interface CardHolderRepository extends JpaRepository<CardHolderEntity, Integer> {

}
