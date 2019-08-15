package com.sangria_clean_architecture.entities.droid

import com.sangria_clean_architecture.shared.ddd_base.Entity

case class Droid(
  id: DroidId,
  name: DroidName,
  primaryFunction: Option[PrimaryFunction]
) extends Entity[DroidId] {

  def update(name: DroidName, primaryFunction: Option[PrimaryFunction]): Droid = copy(
    name = name,
    primaryFunction = primaryFunction
  )

}
