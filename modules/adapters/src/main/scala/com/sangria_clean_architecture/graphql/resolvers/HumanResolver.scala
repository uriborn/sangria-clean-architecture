package com.sangria_clean_architecture.graphql.resolvers

import com.google.inject.Inject
import com.sangria_clean_architecture.graphql.schemas.HumanSchemaValue
import com.sangria_clean_architecture.usecases.human._

import scala.concurrent.{ExecutionContext, Future}

class HumanResolver @Inject()(
  getHumanUseCase: GetHumanUseCase,
  getAllHumansUseCase: GetAllHumansUseCase
) {

  def findAllHumans(limit: Int, offset: Int)(implicit ec: ExecutionContext): Future[List[HumanSchemaValue]] = {
    for {
      output <- getAllHumansUseCase.execute(GetAllHumansInput(limit, offset))
    } yield convertGetAllHumansOutput(output)
  }

  def findHuman(id: Long)(implicit ec: ExecutionContext): Future[Option[HumanSchemaValue]] = {
    getHumanUseCase.execute(GetHumanInput(id)).map(convertGetHumanOutput)
  }

  private def convertGetHumanOutput(output: Option[GetHumanOutput]): Option[HumanSchemaValue] = {
    output.map { o =>
      HumanSchemaValue(
        id = o.id,
        name = o.name,
        episodes = o.episodes,
        homePlanet = o.homePlanet
      )
    }
  }

  private def convertGetAllHumansOutput(output: List[GetAllHumansOutput]): List[HumanSchemaValue] = {
    output.map { o =>
      HumanSchemaValue(
        id = o.id,
        name = o.name,
        episodes = o.episodes,
        homePlanet = o.homePlanet
      )
    }
  }

}
