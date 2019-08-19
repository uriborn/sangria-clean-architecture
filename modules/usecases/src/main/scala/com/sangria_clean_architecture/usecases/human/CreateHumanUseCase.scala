package com.sangria_clean_architecture.usecases.human

import com.google.inject.Inject
import com.sangria_clean_architecture.entities.episode.EpisodeId
import com.sangria_clean_architecture.entities.human.HomePlanet
import com.sangria_clean_architecture.gateway.repositories.HumanRepository
import com.sangria_clean_architecture.usecases.UseCase

import scala.concurrent.{ExecutionContext, Future}

class CreateHumanUseCase @Inject()(
  humanRepository: HumanRepository
) extends UseCase[CreateHumanInput, CreateHumanOutput] {

  override def execute(inputData: CreateHumanInput)(implicit ec: ExecutionContext): Future[CreateHumanOutput] = {
    for {
      humanId <- humanRepository.create(inputData.name, inputData.homePlanetDomainModel, inputData.episodeIds)
    } yield CreateHumanOutput(
      id = humanId.value,
      name = inputData.name,
      episodes = inputData.episodes.map(convertEpisodeOutput),
      homePlanet = inputData.homePlanet
    )
  }

  private def convertEpisodeOutput(episode: CreateHumanEpisodeInput): CreateHumanEpisodeOutput = {
    CreateHumanEpisodeOutput(
      id = episode.id,
      name = episode.name
    )
  }

}

case class CreateHumanInput(
  name: String,
  homePlanet: Option[String],
  episodes: List[CreateHumanEpisodeInput]
) {

  val homePlanetDomainModel = homePlanet.map(HomePlanet.withName)
  val episodeIds = episodes.map(e => EpisodeId(e.id))
  val episodeNames = episodes.map(_.name)

}

case class CreateHumanEpisodeInput(
  id: Long,
  name: String
)

case class CreateHumanOutput(
  id: Long,
  name: String,
  episodes: List[CreateHumanEpisodeOutput],
  homePlanet: Option[String]
)

case class CreateHumanEpisodeOutput(
  id: Long,
  name: String
)
