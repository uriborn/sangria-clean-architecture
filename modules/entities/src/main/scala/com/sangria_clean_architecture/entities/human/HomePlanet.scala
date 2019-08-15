package com.sangria_clean_architecture.entities.human

import com.sangria_clean_architecture.entities.EnumWithValidation
import enumeratum.EnumEntry

import scala.collection.immutable

sealed abstract class HomePlanet(override val entryName: String) extends EnumEntry

object HomePlanet extends enumeratum.Enum[HomePlanet] with EnumWithValidation[HomePlanet] {
  def values: immutable.IndexedSeq[HomePlanet] = findValues

  case object Tatooine extends HomePlanet("Tatooine")
  case object Alderaan extends HomePlanet("Alderaan")
}
