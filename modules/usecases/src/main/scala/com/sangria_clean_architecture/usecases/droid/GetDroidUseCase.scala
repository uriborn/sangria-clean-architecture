package com.sangria_clean_architecture.usecases.droid

import com.google.inject.Inject
import com.sangria_clean_architecture.entities.droid.DroidId
import com.sangria_clean_architecture.gateway.repositories.DroidRepository
import com.sangria_clean_architecture.usecases.UseCase

import scala.concurrent.{ExecutionContext, Future}

class GetDroidUseCase @Inject()(
  droidRepository: DroidRepository
) extends UseCase[GetDroidInput, Option[GetDroidOutput]] {

  override def execute(inputData: GetDroidInput)(implicit ec: ExecutionContext): Future[Option[GetDroidOutput]] = {
    for {
      droid <- droidRepository.findById(DroidId(inputData.id))
    } yield droid.map(d =>
      GetDroidOutput(
        id = d.id.value,
        name = d.name.value,
        episodes = d.episodes.map(_.entryName),
        primaryFunction = d.primaryFunction.map(_.entryName)
      )
    )
  }

}

case class GetDroidInput(
  id: Long
)

case class GetDroidOutput(
  id: Long,
  name: String,
  episodes: List[String],
  primaryFunction: Option[String]
)
