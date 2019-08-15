package com.sangria_clean_architecture.gateway.repositories

import com.sangria_clean_architecture.entities.human.{Human, HumanId}
import com.sangria_clean_architecture.shared.ddd_base.Repository

import scala.concurrent.Future

trait HumanRepository extends Repository[HumanId, Human] {
  def findById(humanId: HumanId): Future[Human]
}
