package com.sangria_clean_architecture.entities.episode

import com.sangria_clean_architecture.entities.EnumWithValidation
import enumeratum.EnumEntry

import scala.collection.immutable

sealed abstract class Episode(override val entryName: String) extends EnumEntry

object Episode extends enumeratum.Enum[Episode] with EnumWithValidation[Episode] {
  def values: immutable.IndexedSeq[Episode] = findValues

  case object NewHope extends Episode("NewHope")
  case object Empire  extends Episode("Empire")
  case object Jedi    extends Episode("Jedi")
}
