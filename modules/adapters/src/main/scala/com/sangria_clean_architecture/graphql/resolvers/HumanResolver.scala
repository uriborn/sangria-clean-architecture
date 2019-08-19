package com.sangria_clean_architecture.graphql.resolvers

import com.google.inject.Inject
import com.sangria_clean_architecture.graphql.schemas.{EpisodeSchemaValue, HumanSchemaValue}
import com.sangria_clean_architecture.usecases.human._

import scala.concurrent.{ExecutionContext, Future}

class HumanResolver @Inject()(
  getHumanUseCase: GetHumanUseCase,
  getAllHumansUseCase: GetAllHumansUseCase,
  createHumanUseCase: CreateHumanUseCase
) {

  def findAllHumans(limit: Int, offset: Int)(implicit ec: ExecutionContext): Future[List[HumanSchemaValue]] = {
    for {
      output <- getAllHumansUseCase.execute(GetAllHumansInput(limit, offset))
    } yield output.map(convertGetAllHumansOutput)
  }

  def findHuman(id: Long)(implicit ec: ExecutionContext): Future[Option[HumanSchemaValue]] = {
    for {
      output <- getHumanUseCase.execute(GetHumanInput(id))
    } yield output.map(convertGetHumanOutput)
  }

  def createHuman(name: String, homePlanet: Option[String], episodes: Seq[Map[String, Any]])(implicit ec: ExecutionContext): Future[HumanSchemaValue] = {
    for {
      output <- createHumanUseCase.execute(convertCreateHumanInput(name, homePlanet, episodes))
    } yield convertCreateHumanOutput(output)
  }

  private def convertCreateHumanInput(name: String, homePlanet: Option[String], episodes: Seq[Map[String, Any]]): CreateHumanInput = {
    val episodeInput = episodes.map { map =>
      val id = map("id").asInstanceOf[Long]
      val name = map("name").asInstanceOf[String]

      CreateHumanEpisodeInput(
        id = id,
        name = name
      )
    }

    CreateHumanInput(
      name = name,
      homePlanet = homePlanet,
      episodes = episodeInput.toList
    )
  }

  private def convertGetAllHumansOutput(output: GetAllHumansOutput): HumanSchemaValue = {
    HumanSchemaValue(
      id = output.id,
      name = output.name,
      episodes = output.episodes.map(e => convertEpisodeSchemaValue(e.id, e.name)),
      homePlanet = output.homePlanet
    )
  }

  private def convertGetHumanOutput(output: GetHumanOutput): HumanSchemaValue = {
    HumanSchemaValue(
      id = output.id,
      name = output.name,
      episodes = output.episodes.map(e => convertEpisodeSchemaValue(e.id, e.name)),
      homePlanet = output.homePlanet
    )
  }

  private def convertCreateHumanOutput(output: CreateHumanOutput): HumanSchemaValue = {
    HumanSchemaValue(
      id = output.id,
      name = output.name,
      episodes = output.episodes.map(e => convertEpisodeSchemaValue(e.id, e.name)),
      homePlanet = output.homePlanet
    )
  }

  private def convertEpisodeSchemaValue(id: Long, name: String): EpisodeSchemaValue = {
    EpisodeSchemaValue(
      id = id,
      name = name
    )
  }

}
