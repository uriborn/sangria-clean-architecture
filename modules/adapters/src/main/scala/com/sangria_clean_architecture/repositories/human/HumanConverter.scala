package com.sangria_clean_architecture.repositories.human

import com.sangria_clean_architecture.entities.episode.Episode
import com.sangria_clean_architecture.entities.human.{HomePlanet, Human, HumanId, HumanName}

class HumanConverter {

  def convertToEntity(humanTable: HumanTable): Human = {
    Human(
      id = HumanId(humanTable.id),
      name = HumanName(humanTable.name),
      episodes = humanTable.episodes.map(e => Episode.withName(e.name)).toList,
      homePlanet = humanTable.homePlanet.map(h => HomePlanet.withName(h))
    )
  }

}
