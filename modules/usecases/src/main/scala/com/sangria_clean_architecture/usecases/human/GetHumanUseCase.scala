package com.sangria_clean_architecture.usecases.human

import com.google.inject.Inject
import com.sangria_clean_architecture.entities.human.HumanId
import com.sangria_clean_architecture.gateway.repositories.HumanRepository
import com.sangria_clean_architecture.usecases.UseCase

import scala.concurrent.{ExecutionContext, Future}

class GetHumanUseCase @Inject()(
  humanRepository: HumanRepository
) extends UseCase[GetHumanInput, Option[GetHumanOutput]] {

  override def execute(inputData: GetHumanInput)(implicit ec: ExecutionContext): Future[Option[GetHumanOutput]] = {
    for {
      human <- humanRepository.findById(HumanId(inputData.id))
    } yield human.map(h =>
      GetHumanOutput(
        id = h.id.value,
        name = h.name.value,
        episodes = h.episodes.map(_.entryName),
        homePlanet = h.homePlanet.map(_.entryName)
      )
    )
  }

}

case class GetHumanInput(
  id: Long
)

case class GetHumanOutput(
  id: Long,
  name: String,
  episodes: List[String],
  homePlanet: Option[String]
)
