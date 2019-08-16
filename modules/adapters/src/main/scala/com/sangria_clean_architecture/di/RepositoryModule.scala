package com.sangria_clean_architecture.di

import com.google.inject.AbstractModule
import com.sangria_clean_architecture.gateway.repositories.HumanRepository
import com.sangria_clean_architecture.repositories.human.HumanRDBRepository

class RepositoryModule extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[HumanRepository]).to(classOf[HumanRDBRepository])
  }

}
