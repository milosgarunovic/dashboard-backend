package com.milosgarunovic.dashboard.domain

import com.milosgarunovic.dashboard.domain.`typealias`.Email
import com.milosgarunovic.dashboard.domain.`typealias`.Password
import org.hibernate.annotations.GenericGenerator
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(generator = "tsid")
    @GenericGenerator(name = "tsid", strategy = "io.hypersistence.utils.hibernate.id.TsidGenerator")
    var id: Long,
    var email: Email,
    var password: Password,
) /*: BaseEntity()*/