package com.sangria_clean_architecture.entities.human

import com.sangria_clean_architecture.entities.episode.Episode
import com.sangria_clean_architecture.shared.ddd_base.Entity

case class Human(
  id: HumanId,
  name: HumanName,
  episodes: List[Episode],
  homePlanet: Option[HomePlanet]
) extends Entity[HumanId] {

  def update(name: HumanName, homePlanet: Option[HomePlanet]): Human = copy(
    name = name,
    homePlanet = homePlanet
  )

}
