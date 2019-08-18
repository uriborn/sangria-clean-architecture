package com.sangria_clean_architecture.di

import com.google.inject.AbstractModule
import com.sangria_clean_architecture.gateway.repositories.{DroidRepository, HumanRepository}
import com.sangria_clean_architecture.repositories.droid.DroidRDBRepository
import com.sangria_clean_architecture.repositories.human.HumanRDBRepository

class RepositoryModule extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[HumanRepository]).to(classOf[HumanRDBRepository])
    bind(classOf[DroidRepository]).to(classOf[DroidRDBRepository])
  }

}
