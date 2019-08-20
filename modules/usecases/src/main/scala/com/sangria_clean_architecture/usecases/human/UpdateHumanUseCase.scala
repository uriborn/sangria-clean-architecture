package com.sangria_clean_architecture.usecases.human

import com.google.inject.Inject
import com.sangria_clean_architecture.entities.episode.EpisodeId
import com.sangria_clean_architecture.entities.human.{HomePlanet, HumanId, HumanName}
import com.sangria_clean_architecture.gateway.repositories.HumanRepository
import com.sangria_clean_architecture.usecases.UseCase

import scala.concurrent.{ExecutionContext, Future}

class UpdateHumanUseCase @Inject()(
  humanRepository: HumanRepository
) extends UseCase[UpdateHumanInput, UpdateHumanOutput] {

  override def execute(inputData: UpdateHumanInput)(implicit ec: ExecutionContext): Future[UpdateHumanOutput] = {
    for {
      _ <- humanRepository.update(HumanId(inputData.id), HumanName(inputData.name), inputData.homePlanetDomainModel, inputData.episodeIds)
    } yield UpdateHumanOutput(
      id = inputData.id,
      name = inputData.name,
      episodes = inputData.episodes.map(convertEpisodeOutput),
      homePlanet = inputData.homePlanet
    )
  }

  private def convertEpisodeOutput(episode: UpdateHumanEpisodeInput): UpdateHumanEpisodeOutput = {
    UpdateHumanEpisodeOutput(
      id = episode.id,
      name = episode.name
    )
  }

}

case class UpdateHumanInput(
  id: Long,
  name: String,
  homePlanet: Option[String],
  episodes: List[UpdateHumanEpisodeInput]
) {

  val homePlanetDomainModel = homePlanet.map(HomePlanet.withName)
  val episodeIds = episodes.map(e => EpisodeId(e.id))

}

case class UpdateHumanEpisodeInput(
  id: Long,
  name: String
)

case class UpdateHumanOutput(
  id: Long,
  name: String,
  episodes: List[UpdateHumanEpisodeOutput],
  homePlanet: Option[String]
)

case class UpdateHumanEpisodeOutput(
  id: Long,
  name: String
)
