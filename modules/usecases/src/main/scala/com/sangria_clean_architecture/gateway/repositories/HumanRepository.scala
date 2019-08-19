package com.sangria_clean_architecture.gateway.repositories

import com.sangria_clean_architecture.entities.episode.EpisodeId
import com.sangria_clean_architecture.entities.human.{HomePlanet, Human, HumanId}
import com.sangria_clean_architecture.shared.ddd_base.Repository

import scala.concurrent.{ExecutionContext, Future}

trait HumanRepository extends Repository[HumanId, Human] {
  def findAll(implicit ec: ExecutionContext): Future[List[Human]]
  def findById(humanId: HumanId)(implicit ec: ExecutionContext): Future[Option[Human]]
  def create(name: String, homePlanet: Option[HomePlanet], episodeIds: List[EpisodeId])(implicit ec: ExecutionContext): Future[HumanId]
}
