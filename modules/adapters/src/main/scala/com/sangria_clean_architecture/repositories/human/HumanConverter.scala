package com.sangria_clean_architecture.repositories.human

import com.sangria_clean_architecture.entities.episode.{Episode, EpisodeId, EpisodeName}
import com.sangria_clean_architecture.entities.human.{HomePlanet, Human, HumanId, HumanName}
import com.sangria_clean_architecture.repositories.episode.EpisodeTable

class HumanConverter {

  def convertToEntity(humanTable: HumanTable): Human = {
    Human(
      id = HumanId(humanTable.id),
      name = HumanName(humanTable.name),
      episodes = humanTable.episodes.map(convertEpisode).toList,
      homePlanet = humanTable.homePlanet.map(h => HomePlanet.withName(h))
    )
  }

  private def convertEpisode(episodeTable: EpisodeTable): Episode = {
    Episode(
      id = EpisodeId(episodeTable.id),
      name = EpisodeName(episodeTable.name)
    )
  }

}
