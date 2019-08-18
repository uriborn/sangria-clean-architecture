package com.sangria_clean_architecture.gateway.repositories

import com.sangria_clean_architecture.entities.human.{Human, HumanId}
import com.sangria_clean_architecture.shared.ddd_base.Repository

import scala.concurrent.{ExecutionContext, Future}

trait HumanRepository extends Repository[HumanId, Human] {
  def findAll(implicit ec: ExecutionContext): Future[List[Human]]
  def findById(humanId: HumanId)(implicit ec: ExecutionContext): Future[Option[Human]]
}
