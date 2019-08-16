package com.sangria_clean_architecture.graphql.resolvers.human

import com.google.inject.Inject
import com.sangria_clean_architecture.graphql.resolvers.HumanSchemaValue
import com.sangria_clean_architecture.usecases.human.{GetHumanInput, GetHumanOutput, GetHumanUseCase}

import scala.concurrent.{ExecutionContext, Future}

class HumanResolver @Inject()(getHumanUseCase: GetHumanUseCase) {

  def findHuman(id: Long)(implicit ec: ExecutionContext): Future[Option[HumanSchemaValue]] = {
    for {
      output <- getHumanUseCase.execute(GetHumanInput(id))
    } yield convertGetHumanOutput(output)
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

}
