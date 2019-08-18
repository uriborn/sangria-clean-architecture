package com.sangria_clean_architecture.gateway.repositories

import com.sangria_clean_architecture.entities.droid.{Droid, DroidId}
import com.sangria_clean_architecture.shared.ddd_base.Repository

import scala.concurrent.{ExecutionContext, Future}

trait DroidRepository extends Repository[DroidId, Droid] {
  def findAll(implicit ec: ExecutionContext): Future[List[Droid]]
  def findById(droidId: DroidId)(implicit ec: ExecutionContext): Future[Option[Droid]]
}
