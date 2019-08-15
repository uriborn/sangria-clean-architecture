package com.sangria_clean_architecture.shared.ddd_base

trait Repository[ID <: Identifier[_], E <: Entity[ID]] {

  type This <: Repository[ID, E]

}
