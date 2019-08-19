package com.sangria_clean_architecture.repositories.droid

import com.sangria_clean_architecture.entities.droid.{Droid, DroidId, DroidName, PrimaryFunction}
import com.sangria_clean_architecture.entities.episode.{Episode, EpisodeId, EpisodeName}
import com.sangria_clean_architecture.repositories.episode.EpisodeTable

class DroidConverter {

  def convertToEntity(droidTable: DroidTable): Droid = {
    Droid(
      id = DroidId(droidTable.id),
      name = DroidName(droidTable.name),
      episodes = droidTable.episodes.map(convertEpisode).toList,
      primaryFunction = droidTable.primaryFunction.map(h => PrimaryFunction.withName(h))
    )
  }

  private def convertEpisode(episodeTable: EpisodeTable): Episode = {
    Episode(
      id = EpisodeId(episodeTable.id),
      name = EpisodeName(episodeTable.name)
    )
  }

}
