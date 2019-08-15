package com.sangria_clean_architecture.usecases.human

import com.google.inject.Inject
import com.sangria_clean_architecture.entities.human.HumanId
import com.sangria_clean_architecture.gateway.repositories.HumanRepository
import com.sangria_clean_architecture.usecases.{OutputBoundary, UseCaseInteractor}

import scala.concurrent.{ExecutionContext, Future}

class GetHumanUseCase @Inject()(
  outputBoundary: OutputBoundary[GetHumanOutput],
  humanRepository: HumanRepository
)(implicit ec: ExecutionContext) extends UseCaseInteractor[GetHumanInput, GetHumanOutput](outputBoundary) {

  override protected def call(inputData: GetHumanInput): Future[GetHumanOutput] = {
    for {
      human <- humanRepository.findById(HumanId(inputData.id))
    } yield GetHumanOutput(
      id = human.id.value,
      name = human.name.value,
      episodes = human.episodes.map(_.entryName),
      homePlanet = human.homePlanet.map(_.entryName)
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
