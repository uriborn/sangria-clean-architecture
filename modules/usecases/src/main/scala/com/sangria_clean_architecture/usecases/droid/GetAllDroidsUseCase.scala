package com.sangria_clean_architecture.usecases.droid

import com.google.inject.Inject
import com.sangria_clean_architecture.gateway.repositories.DroidRepository
import com.sangria_clean_architecture.usecases.UseCase

import scala.concurrent.{ExecutionContext, Future}

class GetAllDroidsUseCase @Inject()(
  droidRepository: DroidRepository
) extends UseCase[GetAllDroidsInput, List[GetAllDroidsOutput]] {

  override def execute(inputData: GetAllDroidsInput)(implicit ec: ExecutionContext): Future[List[GetAllDroidsOutput]] = {
    for {
      droids <- droidRepository.findAll
    } yield droids.map { droid =>
      GetAllDroidsOutput(
        id = droid.id.value,
        name = droid.name.value,
        episodes = droid.episodes.map(_.entryName),
        primaryFunction = droid.primaryFunction.map(_.entryName)
      )
    }.slice(inputData.offset, inputData.offset + inputData.limit)
  }

}


case class GetAllDroidsInput(
  limit: Int,
  offset: Int
)

case class GetAllDroidsOutput(
  id: Long,
  name: String,
  episodes: List[String],
  primaryFunction: Option[String]
)
