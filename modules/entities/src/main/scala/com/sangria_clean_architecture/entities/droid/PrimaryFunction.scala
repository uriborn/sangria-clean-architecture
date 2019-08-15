package com.sangria_clean_architecture.entities.droid

import com.sangria_clean_architecture.entities.EnumWithValidation
import enumeratum.EnumEntry

import scala.collection.immutable

sealed abstract class PrimaryFunction(override val entryName: String) extends EnumEntry

object PrimaryFunction extends enumeratum.Enum[PrimaryFunction] with EnumWithValidation[PrimaryFunction] {
  def values: immutable.IndexedSeq[PrimaryFunction] = findValues

  case object Protocol  extends PrimaryFunction("Protocol")
  case object Astromech extends PrimaryFunction("Astromech")
}
