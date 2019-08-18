package com.sangria_clean_architecture.repositories.droid

import com.sangria_clean_architecture.entities.droid.{Droid, DroidId, DroidName, PrimaryFunction}
import com.sangria_clean_architecture.entities.episode.Episode

class DroidConverter {

  def convertToEntity(droidTable: DroidTable): Droid = {
    Droid(
      id = DroidId(droidTable.id),
      name = DroidName(droidTable.name),
      episodes = droidTable.episodes.map(e => Episode.withName(e.name)).toList,
      primaryFunction = droidTable.primaryFunction.map(h => PrimaryFunction.withName(h))
    )
  }

}
