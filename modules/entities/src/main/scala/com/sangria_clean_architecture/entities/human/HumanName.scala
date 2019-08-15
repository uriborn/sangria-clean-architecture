package com.sangria_clean_architecture.entities.human

case class HumanName(value: String) {
  assert(value.length <= 50, "human name fields maximum length from 50 characters")
}
