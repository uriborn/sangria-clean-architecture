package com.sangria_clean_architecture.usecases.human

import com.google.inject.Inject
import com.sangria_clean_architecture.entities.episode.Episode
import com.sangria_clean_architecture.gateway.repositories.HumanRepository
import com.sangria_clean_architecture.usecases.UseCase

import scala.concurrent.{ExecutionContext, Future}

class GetAllHumansUseCase @Inject()(
  humanRepository: HumanRepository
) extends UseCase[GetAllHumansInput, List[GetAllHumansOutput]] {

  override def execute(inputData: GetAllHumansInput)(implicit ec: ExecutionContext): Future[List[GetAllHumansOutput]] = {
    for {
      humans <- humanRepository.findAll
    } yield humans.map { human =>
      GetAllHumansOutput(
        id = human.id.value,
        name = human.name.value,
        episodes = human.episodes.map(convertEpisodeOutput),
        homePlanet = human.homePlanet.map(_.entryName)
      )
    }.slice(inputData.offset, inputData.offset + inputData.limit)
  }

  private def convertEpisodeOutput(episode: Episode): GetAllHumansEpisodeOutput = {
    GetAllHumansEpisodeOutput(
      id = episode.id.value,
      name = episode.name.value
    )
  }

}

case class GetAllHumansInput(
  limit: Int,
  offset: Int
)

case class GetAllHumansOutput(
  id: Long,
  name: String,
  episodes: List[GetAllHumansEpisodeOutput],
  homePlanet: Option[String]
)

case class GetAllHumansEpisodeOutput(
  id: Long,
  name: String
)
