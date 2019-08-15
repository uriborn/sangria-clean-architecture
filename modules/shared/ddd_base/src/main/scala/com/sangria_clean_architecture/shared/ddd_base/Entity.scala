package com.sangria_clean_architecture.shared.ddd_base

trait Entity[ID <: Identifier[_]] {
  type ID

  def hashCode: Int
  def equals(that: Any): Boolean
}
