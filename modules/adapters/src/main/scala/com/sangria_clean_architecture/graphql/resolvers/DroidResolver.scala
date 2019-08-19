package com.sangria_clean_architecture.graphql.resolvers

import com.google.inject.Inject
import com.sangria_clean_architecture.graphql.schemas.{DroidSchemaValue, EpisodeSchemaValue}
import com.sangria_clean_architecture.usecases.droid._

import scala.concurrent.{ExecutionContext, Future}

class DroidResolver @Inject()(
  getDroidUseCase: GetDroidUseCase,
  getAllDroidsUseCase: GetAllDroidsUseCase
) {

  def findAllDroids(limit: Int, offset: Int)(implicit ec: ExecutionContext): Future[List[DroidSchemaValue]] = {
    for {
      output <- getAllDroidsUseCase.execute(GetAllDroidsInput(limit, offset))
    } yield convertGetAllDroidsOutput(output)
  }

  def findDroid(id: Long)(implicit ec: ExecutionContext): Future[Option[DroidSchemaValue]] = {
    getDroidUseCase.execute(GetDroidInput(id)).map(convertGetDroidOutput)
  }

  private def convertGetDroidOutput(output: Option[GetDroidOutput]): Option[DroidSchemaValue] = {
    output.map { o =>
      DroidSchemaValue(
        id = o.id,
        name = o.name,
        episodes = o.episodes.map(e => convertEpisode(e.id, e.name)),
        primaryFunction = o.primaryFunction
      )
    }
  }

  private def convertGetAllDroidsOutput(output: List[GetAllDroidsOutput]): List[DroidSchemaValue] = {
    output.map { o =>
      DroidSchemaValue(
        id = o.id,
        name = o.name,
        episodes = o.episodes.map(e => convertEpisode(e.id, e.name)),
        primaryFunction = o.primaryFunction
      )
    }
  }

  private def convertEpisode(id: Long, name: String): EpisodeSchemaValue = {
    EpisodeSchemaValue(
      id = id,
      name = name
    )
  }

}
